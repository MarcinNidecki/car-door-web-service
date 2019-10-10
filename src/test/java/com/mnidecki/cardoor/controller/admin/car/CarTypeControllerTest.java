package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.controller.GlobalControllerAdvice;
import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.domain.dto.CarTypeDto;
import com.mnidecki.cardoor.mapper.CarTypeMapper;
import com.mnidecki.cardoor.services.DBService.CarParametersService;
import com.mnidecki.cardoor.services.DBService.CarTypeService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CarTypeController.class)
public class CarTypeControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @MockBean
    private DataSource dataSource;
    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;
    @MockBean
    private CarTypeService carTypeService;
    @MockBean
    private CarTypeMapper carTypeMapper;
    @MockBean
    private CarParametersService carParametersService;


    @Before
    public void setUp()  {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(username = "testUser", password = "pw", roles = "USER")
    public void shouldBeAccessForbidden() throws Exception {
        //When & Then
        mockMvc.perform(get("/admin/car/type").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetAllCarType() throws Exception {
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
        MvcResult result = mockMvc.perform(get("/admin/car/type").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carTypes", Matchers.hasSize(1)))
                .andExpect(model().attribute("carTypes", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("carTypes", Matchers.hasItem(hasProperty("type", is("Suv")))))
                .andExpect(model().attributeExists("carTypeDto"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attribute("isAdd", true))
                .andExpect(view().name("carType"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetACarType() throws Exception {
        //Given
        CarType carType = new CarType(1L, "Suv");
        CarTypeDto carTypeDto = new CarTypeDto(1L, "Suv");
        List<CarType> carTypeList = new ArrayList<>();
        List<CarTypeDto> carTypeDtoList = new ArrayList<>();
        carTypeList.add(carType);
        carTypeDtoList.add(carTypeDto);

        when(carTypeService.getById(carTypeDto.getId())).thenReturn(carType);
        when(carTypeMapper.mapToCarTypeDto(carType)).thenReturn(carTypeDto);
        when(carTypeService.findAll()).thenReturn(carTypeList);
        when(carTypeMapper.mapToCarTypeDtoList(carTypeList)).thenReturn(carTypeDtoList);

        //When & Then
        MvcResult result = mockMvc.perform(get("/admin/car/type/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attribute("carTypes", Matchers.hasSize(1)))
                .andExpect(model().attribute("carTypes", Matchers.hasItem(hasProperty("id", is(1L)))))
                .andExpect(model().attribute("carTypes", Matchers.hasItem(hasProperty("type", is("Suv")))))
                .andExpect(model().attribute("carTypeDto", (hasProperty("id", is(1L)))))
                .andExpect(model().attribute("carTypeDto", (hasProperty("type", is("Suv")))))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attribute("isAdd", true))
                .andExpect(view().name("carType"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldSaveACarType() throws Exception {
        //Given
        CarTypeDto carTypeDto = new CarTypeDto(1L,"Suv");
        CarType carType = new CarType(1L,"Suv");

        when(carTypeMapper.mapToCarType(carTypeDto)).thenReturn(carType);
        when(carTypeService.save(carType)).thenReturn(carType);

        //When & Then
        mockMvc.perform(post("/admin/car/type" ).with(MockMvcRequestBuilderUtils.form(carTypeDto)))
                .andExpect(view().name("redirect:/admin/car/type"))
                .andExpect(flash().attribute("successmessage","Car type is saved successfully"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldHasValidationErrorWhenSavingACar() throws Exception {
        //Given
        CarTypeDto carTypeDto = new CarTypeDto(1L,"");

        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        //When & Then
        MvcResult result = mockMvc.perform(post("/admin/car/type").with(MockMvcRequestBuilderUtils.form(carTypeDto)))
                .andExpect(status().isOk())
                .andExpect(view().name("carType"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Car type name must be minimum 3 characters long"));

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldUpdateCar() throws Exception {
        //Given
        CarType carType = new CarType(1L, "Suv");
        CarTypeDto carTypeDto = new CarTypeDto(1L, "Suv");

        when(carTypeMapper.mapToCarType(carTypeDto)).thenReturn(carType);
        when(carTypeService.save(carType)).thenReturn(carType);

        //When & Then
        mockMvc.perform(put("/admin/car/type").with(MockMvcRequestBuilderUtils.form(carTypeDto)))
                .andExpect(flash().attribute("successmessage", "Car type is updated successfully"))
                .andExpect(view().name("redirect:/admin/car/type"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldRejectValueAndNotUpdate() throws Exception {
        //Given
        CarTypeDto carTypeDto = new CarTypeDto(1L, "");

        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        //When & Then
        MvcResult result = mockMvc.perform(put("/admin/car/type").with(MockMvcRequestBuilderUtils.form(carTypeDto)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("carTypeDto"))
                .andExpect(view().name("carType"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
       // assertTrue(content.contains("Car type name must be minimum 3 characters long"));
        System.out.println(content);
    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldNotUpdate() throws Exception {
        //Given
        CarType carType = new CarType(1L, "Suv");
        CarTypeDto carTypeDto = new CarTypeDto(1L, "Suv");

        when(carTypeMapper.mapToCarType(carTypeDto)).thenReturn(carType);
        when(carTypeService.save(carType)).thenReturn(null);

        //When & Then
        MvcResult result = mockMvc.perform(put("/admin/car/type").with(MockMvcRequestBuilderUtils.form(carTypeDto)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errormessage","Car type is not update, Please try again"))
                .andExpect(model().attributeExists("carTypeDto"))
                .andExpect(view().name("carType"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Car type is not update, Please try again"));

    }

    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldDelete() throws Exception {
        //When & Then
        MvcResult result =  mockMvc.perform(delete("/admin/car/type/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(flash().attribute("successmessage", "Car type is deleted successfully"))
                .andExpect(view().name("redirect:/admin/car/type"))
                .andReturn();
        System.out.print(result);

    }


}