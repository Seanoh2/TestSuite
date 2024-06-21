package com.example.demo.model;

import java.util.Objects;

public class ImageMetadata implements Metadata{
    private String name;
    private int width;
    private int height;
    private boolean matching;
    private boolean pngFormated;

    public ImageMetadata() {
    }

    public ImageMetadata(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public ImageMetadata(String name, int width, int height, boolean matching, boolean pngFormated) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.matching = matching;
        this.pngFormated = pngFormated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isMatching() {
        return matching;
    }

    public void setMatching(boolean matching) {
        this.matching = matching;
    }

    public boolean correctFormat() {
        return pngFormated;
    }

    public void setCorrectFormat(boolean pngFormated) {
        this.pngFormated = pngFormated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMetadata that = (ImageMetadata) o;
        return width == that.width && height == that.height && matching == that.matching && pngFormated == that.pngFormated && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, width, height, matching, pngFormated);
    }
}
