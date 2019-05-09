package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.ProductBindingModel;
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

    @ResponseBody
    @PostMapping(value = "/add/product/form")
    public void addProduct(@RequestBody() ProductBindingModel model) {
        productService.add(model);
    }

    @ResponseBody
    @PostMapping("/edit/product-name")
    public void editProductName(@RequestParam Map<String, String> body) {
        String oldName = body.get("name");
        String newName = body.get("value");

        productService.edit(oldName, newName);
    }

    @ResponseBody
    @PostMapping("/edit/product")
    public void editProduct(@RequestParam Map<String, String> body) {
        productService.edit(body);
    }

    @GetMapping("/products")
    public ModelAndView products(ModelAndView modelAndView) {
        modelAndView.addObject("model", productService.findAll());

        return view("products", modelAndView);
    }

}
