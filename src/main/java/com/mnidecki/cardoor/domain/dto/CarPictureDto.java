package com.mnidecki.cardoor.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class CarPictureDto {

    private Long id;
    private String descriptions;
    private String fileName;
    private String thumbnails;
    private String fileNamePath;
    private String thumbnailsPath;
    private String fileExtension;
    private LocalDate createdDate;
    private MultipartFile file;

    public CarPictureDto(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
                         String thumbnailsPath, String fileExtension, LocalDate createdDate, MultipartFile file) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
        this.file = file;
    }

    public CarPictureDto(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
                         String thumbnailsPath, String fileExtension, MultipartFile file) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.file = file;
    }

    public CarPictureDto(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
                         String thumbnailsPath, String fileExtension, LocalDate createdDate) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
    }

    public CarPictureDto(String descriptions, MultipartFile file) {
        this.descriptions = descriptions;
        this.file = file;
    }
}
