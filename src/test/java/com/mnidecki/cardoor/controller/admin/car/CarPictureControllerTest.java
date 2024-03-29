package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.controller.GlobalControllerAdvice;
import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import com.mnidecki.cardoor.mapper.CarPictureMapper;
import com.mnidecki.cardoor.services.DBService.CarParametersService;
import com.mnidecki.cardoor.services.DBService.CarPictureService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CarPictureController.class)

public class CarPictureControllerTest {

    @Autowired private WebApplicationContext wac;
    @Autowired private MockMvc mockMvc;
    @MockBean private DataSource dataSource;
    @MockBean private GlobalControllerAdvice globalControllerAdvice;
    @MockBean private CarParametersService carParametersService;
    @MockBean private CarPictureMapper carPictureMapper;
    @MockBean private CarPictureService pictureService;

    @Before
    public void setUp()  {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

    }


    @Test
    @WithMockUser(username = "testUser", password = "pw", roles = "USER")
    public void shouldBeAccessForbidden() throws Exception {
        //When & Then
        mockMvc.perform(get("/admin/car/picture").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetAllCarPicture() throws Exception {
        //Given
        CarPicture carPicture = new CarPicture(1L, "Kia Sorento II", "kia", "small-kia", "/images/", "/images/small/", "jpg", LocalDate.of(2000, 11, 23));
        CarPictureDto carPictureDto = new CarPictureDto(1L, "Kia Sorento II", "kia", "small-kia", "/images/",
                "/images/small/", "jpg", LocalDate.of(2000, 11, 23));

        List<CarPicture> carPictureList = new ArrayList<>();
        carPictureList.add(carPicture);
        List<CarPictureDto> carPictureDtoList = new ArrayList<>();
        carPictureDtoList.add(carPictureDto);

        when(carPictureMapper.mapToCarPictureDtoList(carPictureList)).thenReturn(carPictureDtoList);
        when(pictureService.findAll()).thenReturn(carPictureList);

        //When & Then
        mockMvc.perform(get("/admin/car/picture").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carsPictures", Matchers.hasSize(1)))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("descriptions",
                        is("Kia Sorento II")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileName", is("kia")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("thumbnails",
                        is("small-kia")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileNamePath",
                        is("/images/")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("thumbnailsPath",
                        is("/images/small/")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileExtension", is("jpg")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("createdDate",
                        is(LocalDate.of(2000, 11, 23))))))
                .andExpect(model().attribute("isAdd", true))
                .andExpect(view().name("carsPicture"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetACarPicture() throws Exception {
        //Given
        CarPicture carPicture = new CarPicture(1L, "Kia Sorento II", "kia", "small-kia", "/images/", "/images/small/", "jpg", LocalDate.of(2000, 11, 23));
        CarPictureDto carPictureDto = new CarPictureDto(1L, "Kia Sorento II", "kia", "small-kia", "/images/",
                "/images/small/", "jpg", LocalDate.of(2000, 11, 23));

        List<CarPicture> carPictureList = new ArrayList<>();
        carPictureList.add(carPicture);
        List<CarPictureDto> carPictureDtoList = new ArrayList<>();
        carPictureDtoList.add(carPictureDto);

        when(carPictureMapper.mapToCarPictureDtoList(carPictureList)).thenReturn(carPictureDtoList);
        when(pictureService.findAll()).thenReturn(carPictureList);

        when(carPictureMapper.mapToCarPictureDto(carPicture)).thenReturn(carPictureDto);
        when(pictureService.findById(carPictureDto.getId())).thenReturn(carPicture);


        //When & Then
        MvcResult result = mockMvc.perform(get("/admin/car/picture/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carsPictures", Matchers.hasSize(1)))
                .andExpect(model().attribute("carsPictures", Matchers.hasSize(1)))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("descriptions",
                        is("Kia Sorento II")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileName", is("kia")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("thumbnails",
                        is("small-kia")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileNamePath",
                        is("/images/")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("thumbnailsPath",
                        is("/images/small/")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("fileExtension", is("jpg")))))
                .andExpect(model().attribute("carsPictures", Matchers.hasItem(hasProperty("createdDate",
                        is(LocalDate.of(2000, 11, 23))))))

                .andExpect(model().attribute("carPictureDto",hasProperty("id", is(1L))))
                .andExpect(model().attribute("carPictureDto", hasProperty("descriptions",
                        is("Kia Sorento II"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("fileName", is("kia"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("thumbnails",
                        is("small-kia"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("fileNamePath",
                        is("/images/"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("thumbnailsPath",
                        is("/images/small/"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("fileExtension", is("jpg"))))
                .andExpect(model().attribute("carPictureDto", hasProperty("createdDate",
                        is(LocalDate.of(2000, 11, 23)))))
                .andExpect(model().attributeExists("carParametersService"))
                .andExpect(model().attribute("isAdd", false))
                .andExpect(view().name("carsPicture"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldSaveACarPicture() throws Exception {
        //Given
        MultipartFile image =  new MockMultipartFile("file","car-1.jpg",
                "img",new byte[]{1,2,3,4,5,66,7,7,8,9,77,8,9, 0});

        CarPicture carPicture = new CarPicture(1L, "Kia Sorento II", "car-1", "small-kia", "/images/", "/images/small/", "jpg",  LocalDate.of(2000, 11, 23), image);
        CarPictureDto carPictureDto = new CarPictureDto( "Kia Sorento II",  image);

        when(carPictureMapper.mapToCarPicture(carPictureDto)).thenReturn(carPicture);
        when(pictureService.save(carPicture)).thenReturn(carPicture);
        when(pictureService.isFileNameTheSameLikeFileNamePath(carPicture)).thenReturn(true);

        //When & Then
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/car/picture")
                .file( (MockMultipartFile) image)
                .param("descriptions","Kia Sorento II")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/car/picture"))
                .andExpect(flash().attribute("successmessage","Picture car-1.jpg saved successfully"))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }



    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldNotSaveACarPictureWhenFileIsNull() throws Exception {
        //Given

        CarPicture carPicture = new CarPicture(1L, "Kia Sorento II", "car-1", "small-kia", "/images/", "/images/small/", "jpg",  LocalDate.of(2000, 11, 23));
        CarPictureDto carPictureDto = new CarPictureDto( "Kia Sorento II",  null);

        when(carPictureMapper.mapToCarPicture(carPictureDto)).thenReturn(carPicture);
        when(pictureService.save(carPicture)).thenReturn(carPicture);
        when(pictureService.isFileNameTheSameLikeFileNamePath(carPicture)).thenReturn(false);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/car/picture")
                .param("descriptions","Kia Sorento II"))
                .andExpect(status().isOk())
                .andExpect(view().name("carsPicture"))
                .andExpect(model().attribute("errormessage","We could not save your picture. Fill all the field"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldDeletePicture() throws Exception {
        //When & Then
        mockMvc.perform(delete("/admin/car/picture/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("successmessage", "Car picture is deleted successfully"))
                .andExpect(view().name("carsPicture"))
                .andReturn();
    }

@Test
public void save() {
}


}