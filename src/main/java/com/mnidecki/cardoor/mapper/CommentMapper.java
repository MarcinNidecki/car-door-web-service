package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.services.DBService.CarBrandModelService;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommentMapper {

    @Autowired
    UserService userService;
    @Autowired
    CarBrandModelService modelService;

    public Comment mapToComment(CommentDto commentDto) {
        Comment comment = new Comment(
                commentDto.getCommentContent(),
                commentDto.getRating());

        comment.setModel(modelService.findByID(commentDto.getModelId()));
        if(commentDto.getId()!=null && commentDto.getId()>0) commentDto.setId(commentDto.getId());
        return comment;
    }

    public CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getCommentContent(),
                comment.getRating(),
                comment.getCreationDate().toLocalDateTime().toLocalDate().toString(),
                comment.getUser().getFirstname(),
                comment.getUser().getId(),
                comment.getModel().getId());
    }

    public List<CommentDto> mapToCommentDtoList(List<Comment> commentList) {
        return commentList.stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }
}
