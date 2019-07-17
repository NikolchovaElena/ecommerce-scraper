package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.CompetitorBindingModel;
import com.demo.scraper.domain.models.CompetitorDetailsViewModel;
import com.demo.scraper.domain.models.ProductProjection;
import com.demo.scraper.service.api.CompetitorService;
import com.demo.scraper.service.api.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class CompetitorController extends BaseController {

    private final CompetitorService competitorService;
    private final ProductService productService;

    public CompetitorController(CompetitorService competitorService, ProductService productService) {
        this.competitorService = competitorService;
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView competitors(ModelAndView modelAndView) {
        modelAndView.addObject("model", competitorService.findAll());

        return view("index", modelAndView);
    }

    @GetMapping("/vendor/form")
    public ModelAndView getVendorForm(ModelAndView modelAndView) {

        modelAndView.addObject("model", new CompetitorBindingModel());
        modelAndView.addObject("products", fetchProductNames());
        return view("add-vendor-form", modelAndView);
    }

    @PostMapping("/vendor/form")
    public ModelAndView addVendor(@Valid @ModelAttribute("model") CompetitorBindingModel model,
                                  BindingResult bindingResult,
                                  ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            modelAndView.addObject("products", fetchProductNames());
            modelAndView.addObject("selected", model.getProductId());
            return view("add-vendor-form", modelAndView);
        }

        competitorService.add(model);
        return redirect("/");
    }

    private List<ProductProjection> fetchProductNames() {
        return productService.findAllProductNames();
    }

    @GetMapping("/competitors/{id}")
    public ModelAndView competitors(@PathVariable("id") Long id,
                                    ModelAndView modelAndView) {
        CompetitorDetailsViewModel model = competitorService.findBy(id);
        modelAndView.addObject("competitor", model);

        return view("competitor-details", modelAndView);
    }

    @ResponseBody
    @PostMapping("/edit/competitor")
    public void editCompetitor(@RequestParam Map<String, String> body) {
        competitorService.edit(body);
    }

    @ResponseBody
    @PostMapping("/delete/competitors/{id}")
    public void deleteTableEntry(@PathVariable("id") Long id) {
        competitorService.delete(id);
    }

}
