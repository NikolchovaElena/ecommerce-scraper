package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.ScrapeModel;
import com.demo.scraper.service.api.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class ScraperController {

    private final ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }


    @PostMapping(value = "/scrape/input")
    public Map<String, String> scrape(@RequestBody ScrapeModel model) {
        String output = scraperService.scrapeProductInfo(model.getUrl(), model.getxPath());
        return Collections.singletonMap("response", output);
    }


}
