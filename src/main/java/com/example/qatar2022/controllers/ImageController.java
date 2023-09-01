/*package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.TicketService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller

public class ImageController {

  private final ImageService imageService;
  private final TicketService ticketService;
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Value("${uploadDir}")
  private String uploadFolder;

  public ImageController(ImageService imageService, TicketService ticketService) {
    this.imageService = imageService;
    this.ticketService = ticketService;
  }


  @PostMapping("/saveImageDetails")
  public String createImage(
      Model model,
      HttpServletRequest request,
      @RequestParam("image") MultipartFile file,
      BindingResult result) {
    try {

      String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
      log.info("uploadDirectory:: " + uploadDirectory);
      String fileName = file.getOriginalFilename();
      String filePath = Paths.get(uploadDirectory, fileName).toString();
      log.info("FileName: " + file.getOriginalFilename());
      if (fileName == null || fileName.contains("..")) {
        model.addAttribute(
            "invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
        // return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName,
        // HttpStatus.BAD_REQUEST);
      }

      try {
        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
          log.info("Folder Created");
          dir.mkdirs();
        }
        // Save the file locally
        BufferedOutputStream stream =
            new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(file.getBytes());
        stream.close();
      } catch (Exception e) {
        log.info("in catch");
        e.printStackTrace();
      }
      byte[] imageData = file.getBytes();
      Image image = new Image();
      //
      image.setNom(file.getOriginalFilename());
      image.setImageByte(imageData);

      imageService.saveImage(image);
      /*log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
      return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;} catch (Exception e) {
      e.printStackTrace();
      log.info("Exception: " + e);
      // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/";
  }

  @GetMapping("/display/{id}")
  void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Image> image)
      throws ServletException, IOException {
    log.info("Id :: " + id);
    image = imageService.getImageById(id);
    response.setContentType("image/jpg");
    response.getOutputStream().write(image.get().getImageByte());
  }

  @GetMapping("/image/show")
  String show(Model map) {
    List<Image> images = imageService.getAllImage();
    map.addAttribute("images", images);
    return "images";
  }
}
*/