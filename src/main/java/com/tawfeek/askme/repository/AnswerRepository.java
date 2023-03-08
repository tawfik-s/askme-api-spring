package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<List<Answer>> findByQuestion(Question question);
    Optional<List<Answer>> findByAnswerOwner(User answerOwner);

    @Query("SELECT a FROM Answer a WHERE a.answerOwner.id = :userId")
    Optional<List<Answer>> getUserAnsweredQuestions(@Param("userId") long userId);


}
