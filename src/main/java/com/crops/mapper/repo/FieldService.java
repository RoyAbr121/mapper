package com.crops.mapper.repo;

import com.crops.mapper.model.Field;
import com.crops.mapper.model.FieldIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FieldService {

    @Autowired
    FieldRepository fieldRepository;

    public Iterable<Field> findAll() {
        return fieldRepository.findAll();
    }

    public Optional<Field> findById(long id) {
        return fieldRepository.findById(id);
    }

    public Field save(Field field) {
        return fieldRepository.save(field);
    }

    public Iterable<Field> saveAll(Iterable<Field> Fields) {
        return fieldRepository.saveAll(Fields);
    }

    public void delete(Field field) {
        fieldRepository.delete(field);
    }
}
