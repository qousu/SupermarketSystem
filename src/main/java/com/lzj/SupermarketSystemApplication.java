package com.lzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lzj")
@MapperScan("com.lzj.mapper")
public class SupermarketSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupermarketSystemApplication.class, args);
    }

}
