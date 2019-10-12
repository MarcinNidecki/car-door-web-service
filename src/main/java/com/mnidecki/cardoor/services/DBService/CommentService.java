package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.car.Comment;
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
    private CarBrandModelService modelService;


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(final Long id) {
        return commentRepository.findById(id).orElse(new Comment());
    }

    public Comment save(final Comment comment) {

        List<Comment> commentList = findAllByModel_Id(comment.getModel().getId());
        commentList.add(comment);
        comment.setUser(userService.getUserFromAuthentication());
        comment.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        double average= commentList.stream().mapToInt(Comment::getRating).average().orElse(0.0);
        average = Math.round(average * 10) / 10.0;
        CarBrandModel carBrandModel = modelService.findByID(comment.getModel().getId());
        carBrandModel.getStar().setRatingAverage(average);
        carBrandModel.getComments().add(comment);
        modelService.save(carBrandModel);

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
