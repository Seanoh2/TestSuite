package com.example.demo.dao;

import ch.qos.logback.core.util.FileUtil;
import com.example.demo.model.IconMetadata;
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

    public List<IconMetadata> validateIcon(File icon) {
        List<IconMetadata> results = new ArrayList<>();
        System.out.println("Checking file: " + icon.getName());

        try {
            List<BufferedImage> images = Imaging.getAllBufferedImages(icon);
            images.forEach(ico -> {
                IconMetadata iconInfo = new IconMetadata(
                        icon.getName(),
                        ico.getWidth(),
                        ico.getHeight()
                );

                if (validEntries.contains(ico.getHeight())) {
                    iconInfo.setMatching(true);
                } else {
                    iconInfo.setMatching(false);
                }

                results.add(iconInfo);
            });
        } catch (IOException e) {
            throw new RuntimeException("Error changing ico to file" + e);
        }

        return results;
    }
    public List<List<IconMetadata>> checkFile(String path) {

        Path filePath = Paths.get(path);
        List<File> files;
        List<List<IconMetadata>> validationResults = new ArrayList<>();


        try {
            files = this.unzip(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error opening zip file to list" + e);
        }

        files.forEach(file -> {
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
                        File targetFile = new File("src/main/resources/temp/" + entry.getName());

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

    private void cleanUp() {

    }
}
