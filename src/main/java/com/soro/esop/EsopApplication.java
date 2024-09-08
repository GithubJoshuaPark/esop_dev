package com.soro.esop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class EsopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsopApplication.class, args);
	}

	/**
     * 패스워드 인코더
     * Usage: passwordEncoder().encode("password")
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
