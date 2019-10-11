package com.mnidecki.cardoor.domain.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnidecki.cardoor.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@NamedNativeQuery(
        name = "Comment.retriveLast4Comment",
        query = "SELECT * FROM comment ORDER BY creation_date DESC LIMIT 4",
        resultClass = Comment.class
)
@NamedNativeQueries(value = {
        @NamedNativeQuery(
                name = "Comment.countCommentWithRankingLessThen2",
                query = "SELECT count(rating<=2) FROM comment"
        ) })

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "commentContent")
    private String commentContent;

    @Column(name = "rating")
    private Integer rating;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    @Column(name = "creationDate")
    private Timestamp creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private CarBrandModel model;

    public Comment(Long id, String commentContent, Integer rating, Timestamp creationDate, User user,
                   CarBrandModel model) {
        this.id = id;
        this.commentContent = commentContent;
        this.rating = rating;
        this.creationDate = creationDate;
        this.user = user;
        this.model = model;
    }

    public Comment(String commentContent, Integer rating) {
        this.commentContent = commentContent;
        this.rating = rating;
    }
}
