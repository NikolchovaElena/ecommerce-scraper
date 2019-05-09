package com.demo.scraper.service.api;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.models.LogViewModel;

import java.util.List;

public interface LogService {

    void create(Competitor competitor, String price, String currency, String title);

    List<LogViewModel> findAll(Long competitorId);

    Log getCurrentLog(Competitor competitor);

}
