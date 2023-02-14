package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByRecipient(User recipient);

    @Query("select question from Question question where question.sender.id =:id")
    List<Question> findBySender(@Param("id") Long userId);
}
