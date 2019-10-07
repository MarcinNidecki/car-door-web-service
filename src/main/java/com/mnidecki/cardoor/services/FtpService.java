package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.config.FtpConfig;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
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
public class FtpService {

    private static Logger LOGGER = LoggerFactory.getLogger(FtpService.class);

    @Autowired
    private FtpConfig ftpConfig;


    public CarPictureDto uploadPicture(CarPictureDto carPictureDto) throws IOException {
        if (carPictureDto != null) {
            carPictureDto.setFileExtension(FilenameUtils.getExtension(carPictureDto.getFile().getOriginalFilename()));
            String FTP_ADDRESS = ftpConfig.getFtpHost();
            String LOGIN = ftpConfig.getLogin();
            String PSW = ftpConfig.getPassword();

            BufferedImage thumbnails = Thumbnails.of(carPictureDto.getFile().getInputStream())
                    .size(397, 300)
                    .outputFormat(carPictureDto.getFileExtension())
                    .crop(Positions.CENTER)
                    .asBufferedImage();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumbnails, carPictureDto.getFileExtension(), os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            FTPClient con = null;


            try {
                con = new FTPClient();
                con.connect(FTP_ADDRESS);

                if (con.login(LOGIN, PSW)) {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);

                    con.logout();
                    con.disconnect();

                    carPictureDto.setFileName(FilenameUtils.getBaseName(carPictureDto.getFile().getOriginalFilename()));
                    carPictureDto.setThumbnails(FilenameUtils.getBaseName(carPictureDto.getFile().getOriginalFilename()) + "-small");
                    carPictureDto.setFileNamePath(ftpConfig.getDomainFullImagePath() + carPictureDto.getFile().getOriginalFilename());
                    carPictureDto.setThumbnailsPath(ftpConfig.getDomainFullImagePath() + carPictureDto.getThumbnails() + "." + carPictureDto.getFileExtension());

                    LOGGER.info("Successfull uploaded " + carPictureDto.getFile().getOriginalFilename() + "!");

                }
            } catch (Exception e) {
                 LOGGER.info("Could not upload" + carPictureDto.getFile().getOriginalFilename() +"!");
                 return null;

            }



        }
        return carPictureDto;
    }
}