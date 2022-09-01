package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Image;
import com.example.qatar2022.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v1")
public class ImageController {


    @Value("${uploadDir}")
    private String uploadFolder;

    private final ImageService imageService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


   /* @GetMapping(value = {"/", "/home"})
    public String addImagePage() {
        return "index";
    }

    */

    @PostMapping("/image/saveImageDetails")
    public @ResponseBody
    ResponseEntity<?> createImage(@RequestParam("name") String name,
                                  Model model, HttpServletRequest request
            , final @RequestParam("image") MultipartFile file) {
        try {

            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }





            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Image image = new Image();
            //
            image.setNom(image.getNom());
            image.setImageByte(imageData);


            imageService.saveImage(image);
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Image> image)
            throws ServletException, IOException {
        log.info("Id :: " + id);
        image = imageService.getImageById(id);
        response.setContentType("image/jpg");
        response.getOutputStream().write(image.get().getImageByte());
        response.getOutputStream().close();
    }


    @GetMapping("/image/show")
    String show(Model map) {
        List<Image> images = imageService.getAllImage();
        map.addAttribute("images", images);
        return "images";
    }
}

