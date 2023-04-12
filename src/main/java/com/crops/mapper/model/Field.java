package com.crops.mapper.model;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Polygon polygon;

    public Field() {
    }

    public Field(Polygon polygon) {
        this.polygon = polygon;
    }

    public Field(List<Point> points) {
        this.polygon = new Polygon(points);
    }

    public Field(long id, List<Point> points) {
        this.id = id;
        this.polygon = new Polygon(points);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public static final class FieldBuilder {
        private long id;
        private Polygon polygon;

        private FieldBuilder() {
        }

        public static FieldBuilder aField() {
            return new FieldBuilder();
        }

        public FieldBuilder id(long id) {
            this.id = id;
            return this;
        }

        public FieldBuilder polygon(Polygon polygon) {
            this.polygon = polygon;
            return this;
        }

        public Field build() {
            Field field = new Field();
            field.setId(id);
            field.setPolygon(polygon);
            return field;
        }
    }
}
