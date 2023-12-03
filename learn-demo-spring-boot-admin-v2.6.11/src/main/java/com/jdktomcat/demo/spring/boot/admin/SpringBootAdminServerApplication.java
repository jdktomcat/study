package com.jdktomcat.demo.spring.boot.admin;

import de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZF-Timmy
 * @version V1.0<dependency>
 *             <groupId>de.codecentric</groupId>
 *             <artifactId>spring-boot-admin-starter-server</artifactId>
 *             <version>2.6.11</version>
 *         </dependency>
 * @description 类描述：服务器
 * @date 2023/12/3
 */
@EnableAdminServer
@SpringBootApplication(exclude =AdminServerHazelcastAutoConfiguration.class)
public class SpringBootAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminServerApplication.class, args);
    }
}