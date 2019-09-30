package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.services.DBService.DBCarBrandModelService;
import com.mnidecki.cardoor.services.DBService.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommentMapper {

    @Autowired
    DBUserService userService;
    @Autowired
    DBCarBrandModelService modelService;

    public Comment mapToComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getCommentContent(),
                commentDto.getRating(),
                Timestamp.valueOf(commentDto.getCreationDate()),
                userService.findUserById(commentDto.getUserId()),
                modelService.findByID(commentDto.getModelId()));
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
