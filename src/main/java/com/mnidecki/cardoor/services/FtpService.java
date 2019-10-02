package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.config.FtpConfig;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
@Service
public class FtpService {

    @Autowired
    private FtpConfig ftpConfig;

    public RedirectAttributes uploadPicture(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes) throws IOException {
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
        carPictureDto.setFileName(FilenameUtils.getBaseName(carPictureDto.getFile().getOriginalFilename()));
        carPictureDto.setThumbnails(FilenameUtils.getBaseName(carPictureDto.getFile().getOriginalFilename()) + "-small");

        try {
            con = new FTPClient();
            con.connect(FTP_ADDRESS);

            if (con.login(LOGIN, PSW)) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);

                boolean result = con.storeFile(carPictureDto.getFile().getOriginalFilename(), carPictureDto.getFile().getInputStream());
                boolean result2 = con.storeFile(carPictureDto.getThumbnails() + "." + carPictureDto.getFileExtension(), is);
                con.logout();
                con.disconnect();
                redirectAttributes.addFlashAttribute("successmessage2",
                        "You successfully uploaded " + carPictureDto.getFile().getOriginalFilename() + "!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errormessage",
                    "Could not upload " + carPictureDto.getFile().getOriginalFilename() + "!");
        }

        carPictureDto.setFileNamePath("http://debowyzakatek.pl/images/" + carPictureDto.getFile().getOriginalFilename());
        carPictureDto.setThumbnailsPath("http://debowyzakatek.pl/images/" + carPictureDto.getThumbnails() + "." + carPictureDto.getFileExtension());
        return redirectAttributes;
    }
}