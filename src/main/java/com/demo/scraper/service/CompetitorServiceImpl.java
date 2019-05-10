package com.demo.scraper.service;

import com.demo.scraper.domain.entities.Competitor;
import com.demo.scraper.domain.entities.Log;
import com.demo.scraper.domain.models.CompetitorBindingModel;
import com.demo.scraper.domain.models.CompetitorDetailsViewModel;
import com.demo.scraper.domain.models.CompetitorViewModel;
import com.demo.scraper.repository.CompetitorRepository;
import com.demo.scraper.service.api.CompetitorService;
import com.demo.scraper.util.EditDataService;
import com.demo.scraper.service.api.LogService;
import com.demo.scraper.service.api.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompetitorServiceImpl implements CompetitorService {
    private final static int MAX_PARAM_LENGTH = 30;

    private final CompetitorRepository competitorRepository;
    private final ProductService productService;
    private final EditDataService editDataService;
    private final LogService logService;
    private final ModelMapper mapper;

    @Autowired
    public CompetitorServiceImpl(CompetitorRepository competitorRepository,
                                 ProductService productService, EditDataService editDataService, LogService logService,
                                 ModelMapper mapper) {
        this.competitorRepository = competitorRepository;
        this.productService = productService;
        this.editDataService = editDataService;
        this.logService = logService;
        this.mapper = mapper;
    }

    @Override
    public void add(CompetitorBindingModel model) {
        if (!this.productService.doesProductExists(model.getProductId())) {
            throw new NullPointerException("No product by that id!");
        }
        Competitor competitor = mapper.map(model, Competitor.class);
        this.competitorRepository.save(competitor);
    }

    @Override
    public List<CompetitorViewModel> findAll() {

        List<CompetitorViewModel> competitors = this.competitorRepository.findAll().stream()
                .map(c -> {
                    CompetitorViewModel competitor = new CompetitorViewModel();
                    competitor.setId(c.getId());
                    competitor.setProductName(c.getProduct().getName());
                    competitor.setUrl(trimData(c.getUrl(), MAX_PARAM_LENGTH));
                    competitor.setCurrentPriceWithCurrency(getCurrentPrice(getCurrentLog(c)));
                    competitor.setCurrentTitle(getCurrentTitle(getCurrentLog(c)));
                    return competitor;
                }).collect(Collectors.toList());

        return competitors;
    }

    @Override
    public CompetitorDetailsViewModel findBy(Long id) {
        Competitor c = this.competitorRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No competitor by that id!"));

        CompetitorDetailsViewModel model = mapper.map(c, CompetitorDetailsViewModel.class);
        model.setProductName(c.getProduct().getName());
        model.setCurrentPrice(getCurrentPrice(getCurrentLog(c)));
        return model;
    }

    @Override
    public void edit(Map<String, String> body) {
        String name = body.get("name");
        String value = body.get("value");
        Long id = Long.parseLong(body.get("pk"));

        Competitor competitor = competitorRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No competitor by that id!"));

        editDataService.edit(competitor, name, value);
        competitorRepository.save(competitor);
    }

    @Override
    public void delete(Long id) {
        Competitor competitor = competitorRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No competitor by that id!"));

        this.productService.deleteCompetitor(competitor.getProduct(), competitor);
    }

    private String trimData(String s, int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength);
        }
        return s;
    }

    private Log getCurrentLog(Competitor competitor) {
        return this.logService.getCurrentLog(competitor);
    }

    private String getCurrentPrice(Log log) {

        return log == null ? "" : log.getPrice() + " " + log.getCurrency();
    }

    private String getCurrentTitle(Log log) {
        return log == null ? "" : log.getTitle();
    }


}
