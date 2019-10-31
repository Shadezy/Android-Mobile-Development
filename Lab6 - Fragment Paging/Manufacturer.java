package com.cartmell.travis.tcartmelllab6;

import java.io.Serializable;
import java.util.ArrayList;

public class Manufacturer implements Serializable {
    String name = "";
    ArrayList<Model> muscle_car_models = new ArrayList<>();

    public Manufacturer(String name, ArrayList<Model> models) {
        this.name = name;
        this.muscle_car_models = models;
    }

    public String getManufacturerName() {
        return name;
    }

    public String getModelName(int position) {
        return muscle_car_models.get(position).getName();
    }

    public void deleteModel(int position) {
                muscle_car_models.remove(position);
    }

    public int getModelCount() {
        return muscle_car_models.size();
    }

    public void addModel(String name, String year, String engine_size, int id) {
        boolean isExist = false;
        for (int i = 0; i < muscle_car_models.size(); i++) {
            if (muscle_car_models.get(i).getName().compareTo(name) == 0) {
                isExist = true;
            }
        }

        if (!isExist)
            muscle_car_models.add(new Model(name, year, engine_size, id));
    }
}
