package com.cartmell.travis.tcartmelllab6;

import java.io.Serializable;

public class Model implements Serializable {
    private String name;
    private String year;
    private String engine_size;
    private int id;

    public Model(String name, String year, String engine_size, int id) {
        this.name = name;
        this.year = year;
        this.engine_size = engine_size;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngine_size() {
        return engine_size;
    }

    public void setEngine_size(String engine_size) {
        this.engine_size = engine_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
