package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private StarService starService;


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(final Long id) {
        return commentRepository.findById(id).orElse(new Comment());
    }

    public Comment save(final Comment comment) {
        List<Comment> commentList = findAllByModel_Id(comment.getModel().getId());
        commentList.add(comment);

        int sum = commentList.stream().mapToInt(Comment::getRating).sum();
        float rating = sum/ (float) commentList.size();

        Star star = starService.findById(comment.getModel().getId());
        star.setRatingAverage(rating);
        starService.save(star);

        comment.setUser(userService.getUserFromAuthentication());
        comment.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        return commentRepository.save(comment);
    }

    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findAllByModel_Id(Long id) {
        return commentRepository.findAllByModel_Id(id);
    }

    public List<Comment> findTop4ByCreationDate() {
        return commentRepository.retriveLast4Comment();
    }

    public Integer countAllByRatingLessThan2() {
        return commentRepository.countCommentWithRankingLessThen2();
    }
}
