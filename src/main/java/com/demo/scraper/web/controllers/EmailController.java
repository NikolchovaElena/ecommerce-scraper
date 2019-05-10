package com.demo.scraper.web.controllers;

import com.demo.scraper.domain.models.ProductBindingModel;
import com.demo.scraper.repository.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController extends BaseController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/emails")
    public ModelAndView emails(ModelAndView modelAndView) {
        modelAndView.addObject("model", emailService.findAll());

        return view("emails", modelAndView);
    }

    //TODO add validation
    @ResponseBody
    @PostMapping(value = "/add/email")
    public void addEmail(@RequestBody() String email) {
        emailService.add(email);
    }

    @ResponseBody
    @PostMapping("/delete/email/{id}")
    public void deleteEmail(@PathVariable("id") Long id) {
        emailService.delete(id);
    }

}
