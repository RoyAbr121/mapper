package com.crops.mapper.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private double latitude;
    @NotNull
    private double longtitude;

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

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


    public static final class FieldBuilder {
        private long id;
        private @NotNull double latitude;
        private @NotNull double longtitude;

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

        public FieldBuilder longtitude(double longtitude) {
            this.longtitude = longtitude;
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.setId(id);
            field.setLatitude(latitude);
            field.setLongtitude(longtitude);
            return field;
        }
    }
}
