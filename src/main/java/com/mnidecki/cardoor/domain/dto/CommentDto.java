package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    private Long id;
    @Length(min = 15, message = "Message must be at least 20 characters long")
    private String commentContent;
    @NotNull(message = "Please select the star rating point!")
    private Integer rating;
    private String creationDate;
    private String userFirstName;
    private Long userId;
    private Long modelId;

    public CommentDto(@Length(min = 15, message = "Message must be at least 20 characters long") String commentContent, @NotNull(message = "Please select the star rating point!") Integer rating) {
        this.commentContent = commentContent;
        this.rating = rating;
    }

    public CommentDto(@Length(min = 15, message = "Message must be at least 20 characters long") String commentContent, @NotNull(message = "Please select the star rating point!") Integer rating, Long modelId) {
        this.commentContent = commentContent;
        this.rating = rating;
        this.modelId = modelId;
    }
}
