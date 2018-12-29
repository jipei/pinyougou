package cn.itcast.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如果是spring boot的引导类则必须要添加该注解
 * 该注解是一个组合注解；可以扫描到当前包及其子包的那些spring 注解
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
