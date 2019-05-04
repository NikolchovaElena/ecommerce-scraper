package com.demo.scraper.web.controllers;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);
        return modelAndView;
    }

    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String url) {
        return this.view("redirect:" + url);
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
