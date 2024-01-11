package jdktomcat.demo.skywalking.gateway.route.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：traceId拦截器
 * @date 2024/1/8
 */
@Component
@Slf4j
public class ResetTraceIdFilter implements GlobalFilter, Ordered {

    @Value("${resetTraceIdUris:/reset}")
    private String resetTraceIdUris;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        log.info("request uri:{}", uri);
        if (matchContext(uri)) {
            Map<String, List<String>> headers = request.getHeaders();
            headers.forEach((key, dataList) -> {
                if (key.contains("sw")) {
                    request.getHeaders().remove(key);
                    log.info("request reset head param key:{} value:{}",key, Arrays.toString(dataList.toArray()));
                }
            });
            HttpHeaders httpHeaders = request.getHeaders();
            ServerHttpRequest.Builder requestBuilder = request.mutate();
            requestBuilder.headers(k -> k.remove("要修改的header的key"));
            ServerHttpRequest newRequest = requestBuilder.build();
            exchange.mutate().request(newRequest).build();
            return chain.filter(exchange.mutate().request(newRequest).build());
        }
        return chain.filter(exchange);
    }

    private boolean matchContext(String uri) {
        if (StringUtils.isEmpty(resetTraceIdUris)) {
            return false;
        }
        return Arrays.stream(resetTraceIdUris.split(",")).anyMatch(uri::contains);
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
