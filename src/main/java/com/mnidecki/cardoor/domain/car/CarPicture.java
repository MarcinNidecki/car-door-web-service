package com.mnidecki.cardoor.domain.car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Car_pictures")
public class CarPicture {

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

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @OneToMany(targetEntity = CarParameters.class,
            mappedBy = "carPicture",
            fetch = FetchType.EAGER)
    private List<CarParameters> carList = new ArrayList<>();


    public CarPicture(String descriptions, String fileName, String thumbnails, String fileNamePath, String thumbnailsPath, String fileExtension, LocalDate createdDate, LocalDate updatedDate) {
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;

    }

    public CarPicture(Long id, String descriptions, String fileName, String thumbnails, String fileNamePath, String thumbnailsPath, String fileExtension, LocalDate createdDate, LocalDate updatedDate) {
        this.id = id;
        this.descriptions = descriptions;
        this.fileName = fileName;
        this.thumbnails = thumbnails;
        this.fileNamePath = fileNamePath;
        this.thumbnailsPath = thumbnailsPath;
        this.fileExtension = fileExtension;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;

    }

    public LocalDate getCreatedDate() {
        if (createdDate == null) {
            return LocalDate.now();
        }

        return createdDate;
    }

    public LocalDate getUpdatedDate() {
        if (updatedDate == null) {
            return LocalDate.now();
        }
        return updatedDate;
    }

}
