package com.example.demo.dao;

import org.apache.commons.imaging.Imaging;

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
        InputStream in = Files.newInputStream(filePath);

        try {
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry entry = null;
            while((entry = zin.getNextEntry()) != null) {
                File file = new File(entry.getName());
                FileOutputStream os = new FileOutputStream(file);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    os.write(c);
                }
                os.close();

                if(file.getName().endsWith(".ico")) {
                    files.add(file);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while extract the zip: " + e);
        }
        return files;
    }
}
