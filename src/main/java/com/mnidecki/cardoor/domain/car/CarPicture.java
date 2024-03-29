package com.mnidecki.cardoor.domain.car;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarPicture that = (CarPicture) o;

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
        if (carList != null ? !carList.equals(that.carList) : that.carList != null) return false;
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
        result = 31 * result + (carList != null ? carList.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
