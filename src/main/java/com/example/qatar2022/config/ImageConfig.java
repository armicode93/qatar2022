package com.example.qatar2022.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/images/equipe/**")
        .addResourceLocations("file:./src/main/resources/static/images/equipe/");
  }

  private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get(dirName);
    String uploadPath = uploadDir.toFile().getAbsolutePath();

    if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

    registry
        .addResourceHandler("/" + dirName + "/**")
        .addResourceLocations("file:/" + uploadPath + "/");
  }
}
