package com.example.qatar2022.service;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;


    public List<Image> getAllImages()
    {
        List<Image> images = new ArrayList<>();

        imageRepository.findAll().forEach(images::add);

        return images;
    }

    public Image getImage(String id)
    {
        int indice = Integer.parseInt(id);
        return imageRepository.findById(indice);
    }

    public void addImage(Image image)
    {
        imageRepository.save(image);
    }

    public void updateImage(Long id, Image image)
    {
        imageRepository.save(image);

    }

    public void deleteImage(Long id)
    {
        imageRepository.deleteById(id);
    }
}
