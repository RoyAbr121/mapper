package com.crops.mapper.controller;

import com.crops.mapper.repo.FieldService;
import com.crops.mapper.service.OpenStreetMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/fields")
public class FieldsController {

    @Autowired
    OpenStreetMapService openStreetMapService;

    @Autowired
    FieldService fieldService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> getWheatLocations() throws IOException {
        return new ResponseEntity<>(openStreetMapService.searchWheat(), HttpStatus.OK);
    }

    //  TODO: add the functions and mapping for PostGIS related (fieldService) operations

}
