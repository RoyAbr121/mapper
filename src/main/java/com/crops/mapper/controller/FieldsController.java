package com.crops.mapper.controller;

import com.crops.mapper.model.Field;
import com.crops.mapper.model.FieldIn;
import com.crops.mapper.repo.FieldService;
import com.crops.mapper.service.OpenStreetMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/fields")
public class FieldsController {

    @Autowired
    OpenStreetMapService openStreetMapService;

    @Autowired
    FieldService fieldService;

    @RequestMapping(value = "/scrape", method = RequestMethod.GET)
    public ResponseEntity<?> scrapeWheatFields() throws IOException {
        ArrayList<Field> fields = openStreetMapService.scrapeWheatFields();
        if (fields.isEmpty()) throw new RuntimeException("Not Fields were found during the scraping");
        fieldService.saveAll(fields);
        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllFields() {
        return new ResponseEntity<>(fieldService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getField(@PathVariable Long id) {
        return new ResponseEntity<>(fieldService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertField(@RequestBody FieldIn fieldIn) {
        Field field = fieldIn.toField();
        field = fieldService.save(field);
        return new ResponseEntity<>(field, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateField(@PathVariable Long id, @RequestBody FieldIn fieldIn) {
        Optional<Field> dbField = fieldService.findById(id);
        if (dbField.isEmpty()) throw new RuntimeException("Field with the ID: " + id + " was not found");
        fieldIn.updateField(dbField.get());
        Field updatedField = fieldService.save(dbField.get());
        return new ResponseEntity<>(updatedField, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        Optional<Field> dbField = fieldService.findById(id);
        if (dbField.isEmpty()) throw new RuntimeException("Field with the ID: " + id + " was not found");
        fieldService.delete(dbField.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}
