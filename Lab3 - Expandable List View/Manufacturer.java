package com.cartmell.travis.tcartmelllab3;

import java.io.Serializable;
import java.util.ArrayList;

public class Manufacturer implements Serializable {
    String name = "";
    ArrayList<String> muscle_car_models = new ArrayList<>();

    public Manufacturer(String name, ArrayList<String> models) {
        this.name = name;
        this.muscle_car_models = models;
    }

    public String getManufacturerName() {
        return name;
    }

    public String getModelName(int position) {
        return muscle_car_models.get(position);
    }

    public void deleteModel(int position) {
                muscle_car_models.remove(position);
    }

    public int getModelCount() {
        return muscle_car_models.size();
    }

    public void addModel(String data_to_add) {
        boolean isExist = false;
        for (int i = 0; i < muscle_car_models.size(); i++) {
            if (muscle_car_models.get(i).compareTo(data_to_add) == 0) {
                isExist = true;
            }
        }

        if (!isExist)
            muscle_car_models.add(data_to_add);
    }
}
