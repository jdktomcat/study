package com.jdktomcat.demo.dead.lock.restart.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Plus Config
 */
@Configuration
@MapperScan("com.jdktomcat.demo.dead.lock.restart.transaction.mapper")
public class MybatisPlusConfig {

}
