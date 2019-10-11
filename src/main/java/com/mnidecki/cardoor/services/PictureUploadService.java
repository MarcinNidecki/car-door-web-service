package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.config.FtpConfig;
import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.services.DBService.CarPictureService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
@Service
public class PictureUploadService {

    private static Logger LOGGER = LoggerFactory.getLogger(PictureUploadService.class);

    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private CarPictureService pictureService;


    public CarPicture uploadPicture(CarPicture picture) {
        if (picture != null && picture.getFile()!=null) {
            if (picture.getFileName() != null && picture.getFileNamePath() != null) {
                if (pictureService.isFileNameTheSameLikeFileNamePath(picture)) {
                    return picture;
                } else {
                    return sendPicturesToFtp(picture, prepareThumbnailsAsImputStream(picture));
                }
            } else {
               return sendPicturesToFtp(picture, prepareThumbnailsAsImputStream(picture));
            }
        }
        return picture;
    }

    private CarPicture setFilesNamesAndPaths(CarPicture picture) {
        picture.setFileName(FilenameUtils.getBaseName(picture.getFile().getOriginalFilename()));
        picture.setThumbnails(FilenameUtils.getBaseName(picture.getFile().getOriginalFilename()) + "-small");
        picture.setFileNamePath(ftpConfig.getDomainFullImagePath() + picture.getFile().getOriginalFilename());
        picture.setThumbnailsPath(ftpConfig.getDomainFullImagePath() + picture.getThumbnails() + "." + picture.getFileExtension());
        return picture;
    }

    private InputStream prepareThumbnailsAsImputStream(CarPicture picture)  {
        picture.setFileExtension(FilenameUtils.getExtension(picture.getFile().getOriginalFilename()));
        try {
            BufferedImage thumbnails = Thumbnails.of(picture.getFile().getInputStream())
                    .size(397, 300)
                    .outputFormat(picture.getFileExtension())
                    .crop(Positions.CENTER)
                    .asBufferedImage();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumbnails, picture.getFileExtension(), os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    private CarPicture sendPicturesToFtp(CarPicture picture, InputStream thumbnails) {
        FTPClient con = null;

        try {

            con = new FTPClient();
            con.connect(ftpConfig.getFtpHost());
            if (con.login(ftpConfig.getLogin(), ftpConfig.getPassword())) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                con.storeFile(picture.getFile().getOriginalFilename(),
                        picture.getFile().getInputStream());
                con.storeFile(FilenameUtils.getBaseName(picture.getFile().getOriginalFilename()) + "-small." + picture.getFileExtension(), thumbnails);

                con.logout();
                con.disconnect();

                picture = setFilesNamesAndPaths(picture);
                LOGGER.info("Picture was successfull uploaded! ");
            }
            return picture;
        } catch (Exception e) {
            LOGGER.info("Could not upload" + picture.getFile().getOriginalFilename() + "!");
            return new CarPicture();
        }
    }
}





