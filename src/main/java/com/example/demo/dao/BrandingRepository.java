package com.example.demo.dao;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class BrandingRepository {

    private final Set<Integer> validEntries = new HashSet<>(Arrays.asList(16,32,48,256));

    public LinkedHashMap<String, Boolean> validateIcon(File icon) {
        LinkedHashMap<String, Boolean> results = new LinkedHashMap<>();
        System.out.println("Checking file: " + icon.getName());

        try {
            List<BufferedImage> images = Imaging.getAllBufferedImages(icon);
            images.forEach(ico -> {
                if(!validEntries.contains(ico.getHeight())) {
                    System.out.println("Invalid entry found");
                    results.put(icon.getName() + ico.getHeight(), false);
                } else {
                    results.put(icon.getName() + ico.getHeight(), true);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error changing ico to file" + e);
        }

        return results;
    }
    public List<Map<String, Boolean>> checkFile(String path) {

        Path filePath = Paths.get(path);
        List<File> files;
        List<Map<String, Boolean>> validationResults = new ArrayList<>();


        try {
            files = this.unzip(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error opening zip file to list" + e);
        }

        files.forEach(file -> {
            System.out.println("Checking file: " + file.getName());
            validationResults.add(validateIcon(file));
        });

        return validationResults;
    }

    private List<File> unzip(Path filePath) throws IOException {
        List<File> files = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(String.valueOf(filePath))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (!entry.isDirectory()) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        File targetFile = new File("src/main/resources/targetFile.ico");

                        if(entry.getName().endsWith(".ico")) {
                            FileUtils.copyInputStreamToFile(inputStream, targetFile);
                            files.add(targetFile);
                        }
                    }
                }
            }
        }

        return files;
    }
}
