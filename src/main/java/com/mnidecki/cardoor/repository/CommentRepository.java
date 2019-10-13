package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Override
    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    @Override
    Comment save(Comment comment);

    void deleteById(Long id);

    List<Comment> findAllByModel_Id(Long id);

    @Query
    Integer countCommentWithRankingLessThen2();

    @Query
    List<Comment> retriveLast4Comment();

}
