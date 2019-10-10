package com.mnidecki.cardoor.domain.car;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Car_pictures")
public class CarPicture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "descriptions", unique = true)
    private String descriptions;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "thumbnails_name")
    private String thumbnails;

    @Column(name = "file_name_path")
    private String fileNamePath;

    @Column(name = "thumbnails_path")
    private String thumbnailsPath;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(targetEntity = CarParameters.class,
            mappedBy = "carPicture",
            fetch = FetchType.EAGER)
    private List<CarParameters> carList = new ArrayList<>();

    @Transient
    private MultipartFile file;


    public CarPicture(String descriptions, String fileName, String thumbnails, String fileNamePath,
                             String thumbnailsPath, String fileExtension, LocalDate createdDate) {
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;



    }
    public CarPicture(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
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

    public CarPicture(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
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

    public CarPicture(String descriptions, String fileName, String thumbnails, String fileNamePath,
                      String thumbnailsPath, String fileExtension, LocalDate createdDate, MultipartFile file) {
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
        this.file = file;
    }
    public CarPicture(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath,
                             String thumbnailsPath, String fileExtension) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
    }

    public CarPicture(String descriptions, MultipartFile file) {
        this.descriptions = descriptions;
        this.file = file;
    }

    public LocalDate getCreatedDate() {
        if (createdDate == null) {
            return LocalDate.now();
        }
        return createdDate;
    }

    @Override
    public String toString() {
        return "CarPicture{" +
                "id=" + id +
                ", descriptions='" + descriptions + '\'' +
                ", fileName='" + fileName + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", fileNamePath='" + fileNamePath + '\'' +
                ", thumbnailsPath='" + thumbnailsPath + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", createdDate=" + createdDate +
                ", carList=" + carList +
                ", file=" + file +
                '}';
    }
}
