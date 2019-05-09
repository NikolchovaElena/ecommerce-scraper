package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.LogViewModel;
import com.demo.scraper.service.api.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ResponseBody
    @GetMapping("/logs/competitors/{id}")
    public List<LogViewModel> competitorLogChart(@PathVariable("id") Long competitorId) {

        return logService.findAll(competitorId);
    }
}
