package com.crops.mapper.model;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import javax.persistence.*;
import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

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

    public String toGeoJson() {
        String points = polygon.getPoints().toString().replace("Point ", "").replace("x=", "").replace("y=", "").replace(" ", "");
        return "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[" + points + "]}}]}";
    }

    public String toEncodedUrlQuery() {
        String baseUrl = "http://geojson.io/#data=data:application/json,";
        String query = this.toGeoJson();
        String encodedQuery = encodeValue(query);
        return baseUrl + encodedQuery;
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
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
