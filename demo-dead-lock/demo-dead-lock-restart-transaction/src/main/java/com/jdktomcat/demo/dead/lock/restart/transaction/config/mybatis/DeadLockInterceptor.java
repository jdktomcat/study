package com.jdktomcat.demo.dead.lock.restart.transaction.config.mybatis;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.InvocationTargetException;

/**
 * mybatis死锁拦截器
 */
@Intercepts({
        //type指定代理的是那个对象，method指定代理Executor中的那个方法,args指定Executor中的query方法都有哪些参数对象
        //由于Executor中有两个query，因此需要两个@Signature
        //需要代理的对象和方法
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
})
@Slf4j
public class DeadLockInterceptor extends MybatisPlusInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        try{
            return invocation.proceed();
        }catch (Exception exception){
            log.error("数据库更新异常！", exception);
            throw exception;
        }
    }
}
