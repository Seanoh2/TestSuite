package com.example.demo.controller;

import com.example.demo.dao.BrandingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SuiteController {

    @RequestMapping(method= RequestMethod.GET)
    public String index(Model theModel)
    {
        return "index";
    }

    @GetMapping("/branding")
    public String brandingTesting(Model theModel) {
        String path = "C:\\Users\\sohora\\Projects\\TestSuite\\src\\main\\resources\\145e0001.zip";
        BrandingRepository validation = new BrandingRepository();
        List<Map<String, Boolean>> results =  validation.checkFile(path);
        theModel.addAttribute("fileResults", results);

        return "testPages/branding";
    }
}
