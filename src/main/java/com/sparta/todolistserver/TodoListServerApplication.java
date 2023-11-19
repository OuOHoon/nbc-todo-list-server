package com.sparta.todolistserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoListServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListServerApplication.class, args);
    }

}
