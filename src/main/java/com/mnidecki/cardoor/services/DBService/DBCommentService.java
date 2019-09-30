package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBCommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(final Long id) {
        return commentRepository.findById(id).orElseGet(null);
    }

    public Comment save(final Comment comment) {
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
