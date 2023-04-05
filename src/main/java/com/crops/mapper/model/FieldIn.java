package com.crops.mapper.model;

import javax.validation.constraints.NotNull;

import static com.crops.mapper.model.Field.FieldBuilder.aField;

public class FieldIn {

    private double latitude;
    private double longtitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public Field toField() {
        return aField().latitude(latitude).longtitude(longtitude).build();
    }

    public void updateField(Field field) {
        field.setLatitude(latitude);
        field.setLongtitude(longtitude);
    }
}
