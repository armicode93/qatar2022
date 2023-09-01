/*package com.example.qatar2022.service;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.repository.ImageRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
  @Autowired private ImageRepository imageRepository;
  @Value("${uploadDir}")
  private String uploadFolder;

  public List<Image> getAllImage() {
    return imageRepository.findAll();
  }

  public Optional getImageById(Long id) {
    return imageRepository.findById(id);
  }

  public Image saveImage(Image image) {
    return imageRepository.save(image);
  }

  public Image saveImage(MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    String filePath = Paths.get(uploadFolder, fileName).toString();
    if (fileName == null || fileName.contains("..")) {
      throw new IllegalArgumentException("Invalid filename");
    }

    try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
      stream.write(file.getBytes());
    }

    byte[] imageData = file.getBytes();
    Image image = new Image();
    image.setNom(file.getOriginalFilename());
    image.setImageByte(imageData);
    return imageRepository.save(image);
  }
}

 */
