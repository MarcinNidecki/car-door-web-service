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
    @Transient
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

    public CarPictureDto(String fileName, MultipartFile file) {
        this.fileName = fileName;
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarPictureDto that = (CarPictureDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (descriptions != null ? !descriptions.equals(that.descriptions) : that.descriptions != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (thumbnails != null ? !thumbnails.equals(that.thumbnails) : that.thumbnails != null) return false;
        if (fileNamePath != null ? !fileNamePath.equals(that.fileNamePath) : that.fileNamePath != null) return false;
        if (thumbnailsPath != null ? !thumbnailsPath.equals(that.thumbnailsPath) : that.thumbnailsPath != null)
            return false;
        if (fileExtension != null ? !fileExtension.equals(that.fileExtension) : that.fileExtension != null)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return file != null ? file.equals(that.file) : that.file == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descriptions != null ? descriptions.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (thumbnails != null ? thumbnails.hashCode() : 0);
        result = 31 * result + (fileNamePath != null ? fileNamePath.hashCode() : 0);
        result = 31 * result + (thumbnailsPath != null ? thumbnailsPath.hashCode() : 0);
        result = 31 * result + (fileExtension != null ? fileExtension.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
