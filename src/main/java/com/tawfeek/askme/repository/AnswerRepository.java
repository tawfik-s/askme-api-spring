package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestion(Question question);
    List<Answer> findByRecipient(User recipient);

}
