package com.soro.esop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : Joshua Park
 * @version: 1.0.0
 * @license: sorosoft, LLC (<a href="https://soromiso.kr">soromiso.kr</a>)
 * @since : 9/8/24
 */
@Configuration
public class WebClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
