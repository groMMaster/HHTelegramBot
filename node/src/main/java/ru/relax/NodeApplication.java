package ru.relax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class NodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class);
    }
}
