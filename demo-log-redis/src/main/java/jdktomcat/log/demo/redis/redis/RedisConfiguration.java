package jdktomcat.log.demo.redis.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 这个类，是代理RedisTemplate和它返回的OpsForXXX对象，只能获取到执行的Java方法名，具体执行了哪个命令，还需要对Redis代码有了解。
 * 因此不使用，改用另一个对 RedisConnectionFactory 的代理实现类
 */
@Configuration
public class RedisConfiguration {
    @Bean
    @Primary
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        initSerializer(template);
        return getProxy(template, invocation -> {
            String methodName = invocation.getMethod().getName();
            System.out.println("=========" + invocation + "=========");
            if (methodName.contains("execute"))
                System.out.println("----------------------");
            if (methodName.equals("delete") || methodName.equals("execute")) {
                return interceptorRedis(invocation);
            }
            if (methodName.startsWith("opsFor")) {
                // 要根据类名 方法名 参数列表进行缓存
                return getProxy(invocation.proceed(), invocation2 -> interceptorRedis(invocation2));
            }
            return invocation.proceed();
        });
    }

    private Object interceptorRedis(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String key = method.getDeclaringClass().getName() + " " + method.getName();
        Object[] args = invocation.getArguments();
        if (args != null) {
            for (Object obj : args) {
                key += " " + obj.getClass().getName();
                System.out.println("参数: " + obj);
            }
        }
        System.out.println("调用方法: " + key);
        Object ret = invocation.proceed();
        System.out.println("返回:" + ret);
        return ret;
    }

    private <T> T getProxy(Object obj, MethodInterceptor interceptor) {
        ProxyFactory proxy = new ProxyFactory(obj);
        proxy.setProxyTargetClass(true);
        proxy.addAdvice(interceptor);
        return (T) proxy.getProxy();
    }


    private static void initSerializer(RedisTemplate<String,Object> redisTemplate) {
        // Key用StringRedisSerializer，避免写入Redis的Key和Value，前缀都会出现 \xAC\xED\x00\x05t\x00\x03
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        RedisSerializer<Object> valSerializer = new JsonRedisSerializer<>(Object.class);
        // template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(valSerializer);
        redisTemplate.setHashValueSerializer(valSerializer);
    }

    static class JsonRedisSerializer<T> implements RedisSerializer<T> {
        private static final ObjectMapper mapper;
        static {
            mapper = new ObjectMapper();
        }
        private final Class<T> clazz;
        public JsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null)
                return new byte[0];
            try {
                return mapper.writeValueAsBytes(t);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null)
                return null;
            try {
                return mapper.readValue(bytes, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
