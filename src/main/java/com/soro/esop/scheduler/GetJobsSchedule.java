package com.soro.esop.scheduler;

import com.soro.esop.domain.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author : Joshua Park
 * @version: 1.0.0
 * @license: sorosoft, LLC (<a href="https://soromiso.kr">soromiso.kr</a>)
 * @since : 9/8/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetJobsSchedule {
    private final RestTemplate restTemplate;

    //@Scheduled(cron = "0 * * * * ?")
    public void getJobs() {
        log.info("GetJobsSchedule.getJobs() is called.");
        String url = "http://localhost:8089/api/v1/batch/jobs";

        try{
            HttpResponse response = restTemplate.getForObject(url, HttpResponse.class);
            log.info("response: {}", response.toString());
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }
}
