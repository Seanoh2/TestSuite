package com.example.demo.model;

import java.util.Objects;

public class IconMetadata {
    private String name;
    private int width;
    private int height;
    private boolean matching;
    private boolean icoFormated;

    public IconMetadata() {
    }

    public IconMetadata(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public IconMetadata(String name, int width, int height, boolean matching, boolean icoFormated) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.matching = matching;
        this.icoFormated = icoFormated;
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

    public boolean isIcoFormated() {
        return icoFormated;
    }

    public void setIcoFormated(boolean icoFormated) {
        this.icoFormated = icoFormated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IconMetadata that = (IconMetadata) o;
        return width == that.width && height == that.height && matching == that.matching && icoFormated == that.icoFormated && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, width, height, matching, icoFormated);
    }
}
