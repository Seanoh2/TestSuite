package com.example.demo.service;

import com.example.demo.model.IconMetadata;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class BrandingService {

    private final Set<Integer> validEntries = new HashSet<>(Arrays.asList(16,32,48,256));
    private final byte[] icoFormat = {00,00,01,00};

    public List<IconMetadata> validateIcon(File icon) {
        List<IconMetadata> results = new ArrayList<>();
        boolean iconFileFormat;

        System.out.println("Checking file: " + icon.getName());

        try {
            iconFileFormat = validateIcoFormat(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            List<BufferedImage> images = Imaging.getAllBufferedImages(icon);
            images.forEach(ico -> {
                IconMetadata iconInfo = new IconMetadata(
                        icon.getName(),
                        ico.getWidth(),
                        ico.getHeight()
                );

                iconInfo.setMatching(validEntries.contains(ico.getHeight()));
                iconInfo.setIcoFormated(iconFileFormat);

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

    public boolean validateIcoFormat(File iconFile) throws IOException {
        byte[] buffer = new byte[4];
        InputStream is = new FileInputStream(iconFile);
        if (is.read(buffer) != buffer.length) {
        }
        is.close();

        System.out.println(Arrays.toString(buffer));
        return Arrays.equals(icoFormat, buffer);
    }
;
    public void CleanUp(File file) {
        try {
            FileUtils.cleanDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
