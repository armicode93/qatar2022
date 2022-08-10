package com.example.qatar2022.service;


import com.example.qatar2022.entities.Type;
import com.example.qatar2022.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllType()
    {
        List<Type> types = new ArrayList<>();

        typeRepository.findAll().forEach(types::add);

        return types;
    }

    public Type getTypeById(Long idType)
    {
       return  typeRepository.findById(idType).orElse(null);
    }

    public void addType(Type type)
    {
        typeRepository.save(type);
    }
    public void deleteType(Long idType)
    {
        boolean exists = typeRepository.existsById(idType);
        if (!exists) {
            throw new IllegalStateException("Not exists");
        }
        typeRepository.deleteById(idType);
    }

    public void updateType(Long idType, Type type)
    {
        typeRepository.save(type);
    }
}
