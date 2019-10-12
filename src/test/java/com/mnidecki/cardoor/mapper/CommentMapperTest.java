package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.services.DBService.CarBrandModelService;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentMapperTest {

    @InjectMocks
    private CommentMapper commentMapper;
    @Mock
    private CarBrandModelService carBrandModelService;
    @Mock
    private UserService userService;


    @Test
    public void mapToComment() {
        //Given
        CommentDto commentDto = new CommentDto("Very good car!", 10, 1L);


        CarBrandModel sorento = new CarBrandModel(3L, "Sorento", new CarBrand());
        sorento.getBrand().getModels().add(sorento);

        when(carBrandModelService.findByID(commentDto.getModelId())).thenReturn(sorento);
        //When
        Comment comment = commentMapper.mapToComment(commentDto);

        //Then

        assertEquals("Very good car!",comment.getCommentContent());
        assertEquals(Integer.valueOf(10),comment.getRating());
        assertEquals(Long.valueOf(3),comment.getModel().getId());
        assertEquals("Sorento",comment.getModel().getModel());
    }

    @Test
    public void mapToCommentDto() {
        //Given
        User user = new User.UserBuilder()
                .id(2L)
                .firstname("John")
                .lastname("Kovalsky")
                .password("AvDww872nccDAa2131eszx")
                .email("email@wp.pl")
                .build();

        CarBrandModel sorento = new CarBrandModel(3L, "Sorento", new CarBrand());
        sorento.getBrand().getModels().add(sorento);

        Comment comment = new Comment(1L,"Very good car!", 10, Timestamp.valueOf("2019-12-10 21:22:22"), user, sorento);

        //When
        CommentDto commentDto = commentMapper.mapToCommentDto(comment);

        //Then
        assertEquals(Long.valueOf(1),commentDto.getId());
        assertEquals("Very good car!",commentDto.getCommentContent());
        assertEquals(Integer.valueOf(10),commentDto.getRating());
        assertEquals("2019-12-10",commentDto.getCreationDate());
        assertEquals("John",commentDto.getUserFirstName());
        assertEquals(Long.valueOf(2),commentDto.getUserId());
        assertEquals(Long.valueOf(3),commentDto.getModelId());
    }

    @Test
    public void ShouldMapToCommentDtoList() {
        //Given
        User user = new User.UserBuilder()
                .id(2L)
                .firstname("John")
                .lastname("Kovalsky")
                .password("AvDww872nccDAa2131eszx")
                .email("email@wp.pl")
                .build();

        CarBrandModel sorento = new CarBrandModel(3L, "Sorento", new CarBrand());
        sorento.getBrand().getModels().add(sorento);

        Comment comment = new Comment(1L,"Very good car!", 10, Timestamp.valueOf("2019-12-10 21:22:22"), user, sorento);
        List<Comment> commentList = Collections.singletonList(comment);

        //When
        List<CommentDto>  commentDtoList = commentMapper.mapToCommentDtoList(commentList);
        CommentDto commentDto = commentDtoList.get(0);

        //Then
        assertEquals(1,commentDtoList.size());
        assertEquals(Long.valueOf(1),commentDto.getId());
        assertEquals("Very good car!",commentDto.getCommentContent());
        assertEquals(Integer.valueOf(10),commentDto.getRating());
        assertEquals("2019-12-10",commentDto.getCreationDate());
        assertEquals("John",commentDto.getUserFirstName());
        assertEquals(Long.valueOf(2),commentDto.getUserId());
        assertEquals(Long.valueOf(3),commentDto.getModelId());
    }
}