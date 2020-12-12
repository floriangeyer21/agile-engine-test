package com.agile.engine.demo.service;

import com.agile.engine.demo.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class DataInitializer {
    private final FetchDataFromExternalApiService fetchDataFromExternalApiService;

    @Autowired
    public DataInitializer(FetchDataFromExternalApiService fetchDataFromExternalApiService) {
        this.fetchDataFromExternalApiService = fetchDataFromExternalApiService;
    }

    @Scheduled(fixedRateString = "${renewal.cache.time}")
    public void initialize() throws Exception {
        Response response = new Response();
        int count = 1;
        do {
            response = fetchDataFromExternalApiService.fetch();
            count++;
        } while (count <= response.getPageCount()
        );
    }
}
