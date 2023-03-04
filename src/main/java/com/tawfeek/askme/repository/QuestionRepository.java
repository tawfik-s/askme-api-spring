package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    Optional<List<Question>> findByRecipient(User recipient);

    Optional<List<Question>> findBySender(User sender);

    @Query("SELECT q FROM Question q " +
            "WHERE q.recipient.id = :userId " +
            "AND" +
            " q.id NOT IN " +
            "(SELECT a.question.id FROM Answer a " +
            "WHERE a.recipient.id = :userId)")
    Optional<List<Question>> findUnAnsweredQuestions(@Param("userId") Long userId);
}
