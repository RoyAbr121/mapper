package com.crops.mapper.model;

import static com.crops.mapper.model.Field.FieldBuilder.aField;

public class FieldIn {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Field toField() {
        return aField().latitude(latitude).longitude(longitude).build();
    }

    public void updateField(Field field) {
        field.setLatitude(latitude);
        field.setLongitude(longitude);
    }
}
