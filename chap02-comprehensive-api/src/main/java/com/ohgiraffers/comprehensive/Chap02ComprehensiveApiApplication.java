package com.ohgiraffers.comprehensive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //전체적은 프로세스에 설정이 되어있어야 한다.
@SpringBootApplication
public class Chap02ComprehensiveApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap02ComprehensiveApiApplication.class, args);
    }

}
