package com.mnidecki.cardoor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller("/")
public class StaticPageController {

    @GetMapping(value = "about")
    public ModelAndView aboutUs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about");
        return modelAndView;
    }

    @GetMapping(value = "contact")
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");
        return modelAndView;
    }

    @GetMapping(value = "services")
    public ModelAndView service() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("service");
        return modelAndView;
    }
}
