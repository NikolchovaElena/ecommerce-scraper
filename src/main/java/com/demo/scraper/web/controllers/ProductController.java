package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductDetailsViewModel;
import com.demo.scraper.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ProductController extends BaseController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add/form")
    public ModelAndView addProductForm(ModelAndView modelAndView) {

        modelAndView.addObject(new ProductBindingModel());
        return view("product-form", modelAndView);
    }

    @PostMapping("/add/form")
    public ModelAndView addProduct(@ModelAttribute("model") ProductBindingModel model) {
        productService.add(model);
        return redirect("/products");
    }

    @GetMapping("/")
    public ModelAndView products(ModelAndView modelAndView) {
        modelAndView.addObject("model", productService.findAll());

        return view("products", modelAndView);
    }

    @GetMapping("/product/{id}")
    public ModelAndView products(@PathVariable("id") Long id,
                                 ModelAndView modelAndView) {
        ProductDetailsViewModel productDetailsViewModel = productService.findBy(id);
        modelAndView.addObject("product", productDetailsViewModel);

        return view("product-details", modelAndView);
    }

    @ResponseBody
    @PostMapping("/delete/product/{id}")
    public void deleteTableEntry(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @ResponseBody
    @PostMapping("/edit/product")
    public void editProduct(@RequestParam Map<String, String> body) {
        productService.edit(body);
    }
}
