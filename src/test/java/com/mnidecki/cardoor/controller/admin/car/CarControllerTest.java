package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.controller.GlobalControllerAdvice;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.*;
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
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @MockBean
    DataSource dataSource;
    @MockBean
    GlobalControllerAdvice globalControllerAdvice;
    @MockBean
    private DBLocationService locationService;
    @MockBean
    private DBCarService carService;
    @MockBean
    private DBCarTypeService carTypeService;
    @MockBean
    private DBCarPicture carPictureService;
    @MockBean
    private DBCarParameters carParametersService;
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
    private DBCarBrandService carBrandService;
    @MockBean
    private CarBrandModelMapper carBrandModelMapper;
    @MockBean
    private DBCarBrandModelService carBrandModelService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    public void allCity() {
    }

    @Test
    public void allType() {
    }

    @Test
    public void allCar() {
    }

    @Test
    public void allCarPicture() {
    }

    @Test
    public void allBrand() {
    }

    @Test
    @WithMockUser(username = "testUser", password = "pw", roles = "USER")
    public void shouldBeAccessForbidden() throws Exception {
        //When & Then
        mockMvc.perform(get("/admin/car" ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "testAdmin", password = "pw", roles = "ADMIN")
    public void shouldGetAllCars() throws Exception {
        //When & Then
        MvcResult result = mockMvc.perform(get("/admin/car" ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("allCar"))
                .andExpect(model().attributeExists("allType"))
                .andExpect(model().attributeExists("allCity"))
                .andExpect(model().attributeExists("allCarPicture"))
                .andExpect(model().attributeExists("allBrdand"))
                .andExpect(model().attributeExists("carDto"))
                .andExpect(model().attributeExists("afterPrice"))
                .andExpect(model().attributeExists("beforePrice"))
                .andExpect(model().attribute("isAdd",false))
                .andExpect(view().name("cars"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Hell3eerwdfwsefsdfsdfwerwefsdfsdfsdfsdfsdo"));
    }


    @Test
    public void save() {
    }

    @Test
    public void getCar() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAllModels() {
    }
}