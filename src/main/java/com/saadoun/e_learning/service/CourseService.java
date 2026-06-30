package com.saadoun.e_learning.service;

import com.saadoun.e_learning.dto.CourseDto;
import com.saadoun.e_learning.dto.req.CourseReqDto;
import com.saadoun.e_learning.model.Course;
import com.saadoun.e_learning.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Value("${upload.path}")
    private String uploadPath;



    public CourseDto save(CourseReqDto dto){

        MultipartFile image = dto.getImage();
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(uploadPath);
        try{
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Files.copy(image.getInputStream(),
                    path.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        Course course = Course.builder()
                .title(dto.getTitle())
                .img("/uploads/" + fileName)
                .build();


        String imageUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(course.getImg())
                .toUriString();

          courseRepository.save(course);
          return CourseDto.builder()
                  .title(course.getTitle())
                  .img(imageUrl)
                  .build();
    }


}
