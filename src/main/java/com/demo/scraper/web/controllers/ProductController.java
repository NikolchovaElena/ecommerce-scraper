package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.domain.models.ProductViewModel;
import com.demo.scraper.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/products")
    public ModelAndView products(ModelAndView modelAndView) {
        modelAndView.addObject("model", productService.findAllWithPrices());

        return view("products", modelAndView);
    }

    @GetMapping("/product/{id}")
    public ModelAndView products(@PathVariable("id") Long id,
                                 ModelAndView modelAndView) {
        ProductViewModel productViewModel = productService.findBy(id);
        modelAndView.addObject("product", productViewModel);

        return view("product-details", modelAndView);
    }

}
