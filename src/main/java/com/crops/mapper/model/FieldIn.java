package com.crops.mapper.model;

import org.springframework.data.geo.Polygon;

import static com.crops.mapper.model.Field.FieldBuilder.aField;

public class FieldIn {

    private Polygon polygon;

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Field toField() {
        return aField().polygon(polygon).build();
    }

    public void updateField(Field field) {
        field.setPolygon(polygon);
    }
}
