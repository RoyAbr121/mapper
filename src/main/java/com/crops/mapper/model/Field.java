package com.crops.mapper.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    public Field() {
    }

    public Field(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public static final class FieldBuilder {
        private long id;
        private @NotNull double latitude;
        private @NotNull double longitude;

        private FieldBuilder() {
        }

        public static FieldBuilder aField() {
            return new FieldBuilder();
        }

        public FieldBuilder id(long id) {
            this.id = id;
            return this;
        }

        public FieldBuilder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public FieldBuilder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.setId(id);
            field.setLatitude(latitude);
            field.setLongitude(longitude);
            return field;
        }
    }
}
