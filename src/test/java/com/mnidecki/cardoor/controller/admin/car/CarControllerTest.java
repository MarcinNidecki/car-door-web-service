package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.controller.GlobalControllerAdvice;
import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.car.*;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.*;
import com.mnidecki.cardoor.services.DBService.CarPictureService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @MockBean
    DataSource dataSource;
    @MockBean
    GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;
    @MockBean
    private CarService carService;
    @MockBean
    private CarTypeService carTypeService;
    @MockBean
    private CarPictureService carPictureService;
    @MockBean
    private CarMapper carMapper;
    @MockBean
    private LocationMapper locationMapper;
    @MockBean
    private CarTypeMapper carTypeMapper;
    @MockBean
    private CarPictureMapper carPictureMapper;
    @MockBean
    private CarBrandMapper carBrandMapper;
    @MockBean
    private CarBrandService carBrandService;
    @MockBean
    private CarBrandModelMapper carBrandModelMapper;
    @MockBean
    private CarBrandModelService carBrandModelService;


    public Car getSampleCar() {

        List<CarBrandModel> models = new ArrayList<>();
        CarBrand kia = new CarBrand(1L, "Kia", models);
        CarBrandModel model = new CarBrandModel(1L, "Sorento",kia);
        models.add(model);

        Locationn location = new Locationn(1L, "Katowice");
        CarPicture carPicture = new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath", "thumbNailsPath",
                "jpg", LocalDate.now());

        CarType carType = new CarType(1L, "Suv");

        CarParameters carParameters = new CarParameters.CarParametersBuilder()
                .id(1L)
                .carPicture(carPicture)
                .airConditioning(true)
                .bigBags(2)
                .seatsNumber(5)
                .allWheelDrive(true)
                .doorsNumber(2)
                .smallBags(2)
                .fuelType("ON")
                .year(2015)
                .transmissionIsAutomatic(true)
                .type(carType)
                .color("Black")
                .build();

        Car car = new Car.CarBuilder()
                .id(234L)
                .model(model)
                .price(new BigDecimal(123))
                .vehicleStatus("Good")
                .location(location)
                .registration("SZO121212")
                .carParameters(carParameters)
                .build();

        carParameters.setCar(car);

        return car;
    }

    public CarDto getSampleCarDto() {

        return new CarDto.CarDtoBuilder()
                .id(234L)
                .bigBags(2)
                .seatsNumber(5)
                .doorsNumber(2)
                .allWheelDrive(true)
                .year(2011)
                .smallBags(2)
                .fuelType("ON")
                .transmissionIsAutomatic(true)
                .airConditioning(true)
                .color("Black")
                .model("Sorento")
                .modelId(1L)
                .brand("Kia")
                .brandId(1L)
                .price(new BigDecimal(123))
                .registration("SZO121212")
                .carTypeName("Suv")
                .vehicleStatus("Good")
                .carTypeId(1L)
                .cityId(1L)
                .carPictureId(1L)
                .fileNamePath("fileNamePath")
                .thumbnailsPath("thumbnailsPath")
                .carParametersId(1L)
                .build();

    }


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

   /* @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void allCity() throws Exception {
        //Given
        List<Locationn> locationsList = new ArrayList<>();
        Locationn location = new Locationn(1L, "Poland", "Katowice", "Airport Katowice",
                "ul. Odlotowa", "23a/b", "pick up instructions",
                "44 -444", "email@katowice.email.pl", "515125144",
                "11:00 - 19:00");

        locationsList.add(location);

        List<LocationnDto> locationsDtoList = new ArrayList<>();
        LocationnDto locationDto = new LocationnDto(1L, "Poland", "Katowice", "Airport Katowice", "ul. Odlotowa","23a/b", "pick up instructions", "44-444", "email@katowice.email.pl", "515125144", "11:00 - 19:00");
        locationsDtoList.add(locationDto);

        when(locationService.findAll()).thenReturn(locationsList);
        when(locationMapper.mapToLocationDtoList(locationsList)).thenReturn(locationsDtoList);

        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attribute("allCity", Matchers.hasSize(1)))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("city", is("Katowice")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("country", is("Poland")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("locationName", is("Airport Katowice")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("addressLine", is("ul. Odlotowa")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("addressLine2", is("23a/b")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("pickUpInstructions", is("pick " +
                        "up instructions")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("postalCode", is("44-444")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("email", is("email@katowice.email.pl")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("phone", is("515125144")))))
                .andExpect(model().attribute("allCity", Matchers.hasItem(hasProperty("openingHours",
                        is("11:00 - 19:00")))))
                .andExpect(view().name("cars"))
                .andReturn();
    }*/
   

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void allType() throws Exception {
        //Given
        CarType carType = new CarType(1L, "Suv");
        CarTypeDto carTypeDto = new CarTypeDto(1L, "Suv");
        List<CarType> carTypeList = new ArrayList<>();
        List<CarTypeDto> carTypeDtoList = new ArrayList<>();
        carTypeList.add(carType);
        carTypeDtoList.add(carTypeDto);

        when(carTypeService.findAll()).thenReturn(carTypeList);
        when(carTypeMapper.mapToCarTypeDtoList(carTypeList)).thenReturn(carTypeDtoList);

        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attribute("allType", Matchers.hasSize(1)))
                .andExpect(model().attribute("allType", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("allType", Matchers.hasItem(hasProperty("type", is("Suv")))))
                .andExpect(view().name("cars"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void allCar() throws Exception {
        //Given
        Car car = getSampleCar();
        CarDto carDto = getSampleCarDto();
        List<Car> carList = new ArrayList<>();
        List<CarDto> carDtoList = new ArrayList<>();
        carList.add(car);
        carDtoList.add(carDto);

        when(carService.findAll()).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attribute("allCar", Matchers.hasSize(1)))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("id", is(234L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("bigBags", is(2)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("seatsNumber", is(5)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("doorsNumber", is(2)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("allWheelDrive", is(true)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("year", is(2011)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("smallBags", is(2)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("fuelType", is("ON")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("transmissionIsAutomatic",
                        is(true)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("airConditioning", is(true)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("color", is("Black")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("model", is("Sorento")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("modelId", is(1L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("brand", is("Kia")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("brandId", is(1L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("price",
                        is(BigDecimal.valueOf(123))))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("registration", is("SZO121212")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("carTypeName", is("Suv")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("vehicleStatus", is("Good")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("carTypeId", is(1L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("cityId", is(1L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("carPictureId", is(1L)))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("fileNamePath", is("fileNamePath")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("thumbnailsPath", is("thumbnailsPath")))))
                .andExpect(model().attribute("allCar", Matchers.hasItem(hasProperty("carParametersId", is(1L)))))
                .andExpect(view().name("cars"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void allCarPicture() throws Exception {
        //Given
        CarPicture carPicture = new CarPicture(1L, "Kia Sorento II", "kia", "small-kia", "/images/", "/images/small/", "jpg", LocalDate.of(2000, 11, 23));

        CarPictureDto carPictureDto = new CarPictureDto(1L, "Kia Sorento II", "kia", "small-kia", "/images/",
                "/images/small/", "jpg", LocalDate.of(2000, 11, 23));

        List<CarPicture> carPictureList = new ArrayList<>();
        carPictureList.add(carPicture);
        List<CarPictureDto> carPictureDtoList = new ArrayList<>();
        carPictureDtoList.add(carPictureDto);
        when(carPictureService.findAll()).thenReturn(carPictureList);
        when(carPictureMapper.mapToCarPictureDtoList(carPictureList)).thenReturn(carPictureDtoList);

        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attribute("allCarPicture", Matchers.hasSize(1)))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("descriptions",
                        is("Kia Sorento II")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("fileName", is("kia")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("thumbnails", is("small-kia")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("fileNamePath", is("/images/")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("thumbnailsPath",
                        is("/images/small/")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("fileExtension", is("jpg")))))
                .andExpect(model().attribute("allCarPicture", Matchers.hasItem(hasProperty("createdDate", is(LocalDate.of(2000, 11, 23))))))
                .andExpect(view().name("cars"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void allBrand() throws Exception {
        //Given
        List<CarBrandModel> models = new ArrayList<>();
        models.add(new CarBrandModel(1L, "Sorento"));
        CarBrand carBrand = new CarBrand(1L, "Kia", models);

        CarBrandDto carBrandDto =  new CarBrandDto(1L,"Kia", new ArrayList<>());
        CarBrandModelDto carBrandModelDto = new CarBrandModelDto(1L,"Sorento", 1L);
        carBrandDto.getModels().add(carBrandModelDto);

        List<CarBrand> carBrandList = new ArrayList<>();
        List<CarBrandDto> carBrandDtoList = new ArrayList<>();
        carBrandList.add(carBrand);
        carBrandDtoList.add(carBrandDto);

        when(carBrandService.findAll()).thenReturn(carBrandList);
        when(carBrandMapper.mapToCarBrandDtoList(carBrandList)).thenReturn(carBrandDtoList);

        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(model().attribute("allBrand", Matchers.hasSize(1)))
                .andExpect(model().attribute("allBrand", hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("allBrand", hasItem(hasProperty("brand", is("Kia")))))
                .andExpect(model().attribute("allBrand", hasItem(hasProperty("models", hasItem(hasProperty("id",
                        is(1L)))))))
                .andExpect(model().attribute("allBrand", hasItem(hasProperty("models", hasItem(hasProperty("model",
                        is("Sorento")))))))
                .andExpect(view().name("cars"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testUser", password = "pw", roles = "USER")
    public void shouldBeAccessForbidden() throws Exception {
        //When & Then
        mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetAllCars() throws Exception {
        //Given
        List<Car> carList = new ArrayList<>();
        carList.add(getSampleCar());

        List<CarDto> carDtoList = new ArrayList<>();
        carDtoList.add(getSampleCarDto());

        when(carService.findAll()).thenReturn(carList);
        when(carMapper.mapToCarDtoList(carList)).thenReturn(carDtoList);

        //When & Then
        MvcResult result = mockMvc.perform(get("/admin/car").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(model().attributeExists("carDto"))
                .andExpect(model().attributeExists("afterPrice"))
                .andExpect(model().attributeExists("beforePrice"))
                .andExpect(model().attribute("isAdd", false))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Sorento"));
        assertTrue(content.contains("Kia"));
        assertTrue(content.contains("123"));
        assertTrue(content.contains("234"));
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldSaveACar() throws Exception {
        //Given
        Car car = getSampleCar();
        CarDto carDto = getSampleCarDto();

        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carService.save(car)).thenReturn(car);

        //When & Then
        mockMvc.perform(post("/admin/car").with(MockMvcRequestBuilderUtils.form(carDto)))
                .andExpect(flash().attribute("successmessage", "Car is saved successfully"))
                .andExpect(view().name("redirect:/admin/car"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldHasErrorWhenSavingACar() throws Exception {
        //Given
        CarDto carDto = getSampleCarDto();
        carDto.setYear(1600);
        carDto.setModelId(null);
        carDto.setColor("");
        carDto.setRegistration("");
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        //When & Then
        MvcResult result = mockMvc.perform(post("/admin/car").with(MockMvcRequestBuilderUtils.form(carDto)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("The license plate must be between 2 and 10 characters long"));
        assertTrue(content.contains("must be greater than or equal to 2000"));
        assertTrue(content.contains("Color must be not empty"));
        assertTrue(content.contains("The model name must not be empty"));
        System.out.println(content);
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetACar() throws Exception {
        //Given
        CarDto carDto = getSampleCarDto();
        Car car = getSampleCar();
        List<CarDto> carsDto = new ArrayList<>();
        carsDto.add(carDto);
        List<Car> cars = new ArrayList<>();
        cars.add(car);

        when(carService.findById(anyLong())).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);
        when(carService.findAll()).thenReturn(cars);
        when(carMapper.mapToCarDtoList(cars)).thenReturn(carsDto);

        //When & Then
        MvcResult result = mockMvc.perform(get("/admin/car/{carId}", 1))
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(model().attributeExists("carDto"))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Sorento"));
        assertTrue(content.contains("Kia"));
        assertTrue(content.contains("123"));
        assertTrue(content.contains("234"));
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldUpdateCar() throws Exception {
        //Given
        CarDto carDto = getSampleCarDto();
        Car car = getSampleCar();

        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carService.save(car)).thenReturn(car);

        //When & Then
        mockMvc.perform(put("/admin/car").with(MockMvcRequestBuilderUtils.form(carDto)))
                .andExpect(flash().attribute("successmessage", "Car is updated successfully"))
                .andExpect(view().name("redirect:/admin/car"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldRejectValueAndNotUpdate() throws Exception {
        //Given
        CarDto carDto = getSampleCarDto();
        carDto.setYear(1600);
        carDto.setModelId(null);
        carDto.setColor("");
        carDto.setRegistration("");
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        //When & Then
        MvcResult result = mockMvc.perform(put("/admin/car").with(MockMvcRequestBuilderUtils.form(carDto)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("The license plate must be between 2 and 10 characters long"));
        assertTrue(content.contains("must be greater than or equal to 2000"));
        assertTrue(content.contains("Color must be not empty"));
        assertTrue(content.contains("The model name must not be empty"));
        System.out.println(content);
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldNotUpdate() throws Exception {
        //Given
        CarDto carDto = getSampleCarDto();
        Car car = getSampleCar();

        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carService.save(car)).thenReturn(null);

        //When & Then
        MvcResult result = mockMvc.perform(put("/admin/car").with(MockMvcRequestBuilderUtils.form(carDto)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrand"))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Car is not updated, Please try again"));
        System.out.println(content);
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldDelete() throws Exception {
        //When & Then
        MvcResult result = mockMvc.perform(delete("/admin/car/{carId}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(flash().attribute("successmessage", "Car is deleted successfully"))
                .andExpect(view().name("redirect:/admin/car"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldFindAllModels() throws Exception {
        List<CarBrandModel> modelList = new ArrayList<>();
        Car car = getSampleCar();
        modelList.add(car.getModel());

        List<CarBrandModelDto> modelsDto = new ArrayList<>();
        modelsDto.add(new CarBrandModelDto(1L, "Sorento", 1L));

        //Given
        when(carBrandModelService.getAllModelByBrand_Id(modelList.get(0).getBrand().getId())).thenReturn(modelList);
        when(carBrandModelMapper.mapToCarBrandModelDtoList(modelList)).thenReturn(modelsDto);

        //When & Then
        mockMvc.perform(get("/admin/car/model")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].model", is("Sorento")))
                .andExpect(jsonPath("$[0].brandId", is(1)));

    }
}