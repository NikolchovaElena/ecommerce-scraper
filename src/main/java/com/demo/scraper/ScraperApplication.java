package com.demo.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// TODO Интеграция с API-a на shopify за смяна на цените автоматично

@EnableScheduling
@SpringBootApplication
public class ScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }
}
