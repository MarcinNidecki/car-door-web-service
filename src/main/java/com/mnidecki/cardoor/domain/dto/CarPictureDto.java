package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.time.LocalDate;

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
    private LocalDate updatedDate;
    @Transient
    private MultipartFile file;

    public CarPictureDto(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath, String thumbnailsPath, String fileExtension, LocalDate createdDate, MultipartFile file) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
        this.updatedDate = LocalDate.now();
        this.file = file;
    }
}
