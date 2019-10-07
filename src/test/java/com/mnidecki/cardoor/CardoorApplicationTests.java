package com.mnidecki.cardoor;

import com.mnidecki.cardoor.controller.GlobalControllerAdvice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class CardoorApplicationTests {
    @MockBean
    DataSource dataSource;
    @MockBean
    GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private WebApplicationContext wac;

    @Test
    public void contextLoads() {
    }

}
