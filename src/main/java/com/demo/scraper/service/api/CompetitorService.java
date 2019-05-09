package com.demo.scraper.service.api;

import com.demo.scraper.domain.models.CompetitorBindingModel;
import com.demo.scraper.domain.models.CompetitorDetailsViewModel;
import com.demo.scraper.domain.models.CompetitorViewModel;

import java.util.List;
import java.util.Map;

public interface CompetitorService {

    void add(CompetitorBindingModel model);

    List<CompetitorViewModel> findAll();

    CompetitorDetailsViewModel findBy(Long id);

    void delete(Long id);

    void edit(Map<String, String> body);

}
