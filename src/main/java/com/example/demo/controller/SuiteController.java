package com.example.demo.controller;

import com.example.demo.service.BrandingService;
import com.example.demo.model.IconMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/")
public class SuiteController {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\upload";


    @RequestMapping(method= RequestMethod.GET)
    public String index(Model theModel)
    {
        return "index";
    }

    @GetMapping("/uploadBrandingFile")
    public String displayUploadForm() {
        return "testPages/uploadBrandingFile";
    }

    @GetMapping("/branding")
    public String brandingTesting(Model theModel) {
        return "testPages/branding";
    }

    @PostMapping("/upload")
    public String uploadImage(Model theModel, @RequestParam("zip") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        System.out.println(fileNameAndPath.toAbsolutePath().toString());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        BrandingService validation = new BrandingService();
        List<List<IconMetadata>> results =  validation.checkFile(fileNameAndPath.toAbsolutePath().toString());
        theModel.addAttribute("fileResults", results);
        theModel.addAttribute("msg", "Uploaded file: " + fileNames.toString());

        return "testPages/branding";
    }
}
