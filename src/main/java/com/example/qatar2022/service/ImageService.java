package com.example.qatar2022.service;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private  ImageRepository imageRepository;



    public List<Image> getAllImage()
    {
        return imageRepository.findAll();
    }

    public Optional getImageById(Long id)
    {
        return imageRepository.findById(id);
    }

    public void saveImage(Image image)
    {
        imageRepository.save(image);
    }





}
