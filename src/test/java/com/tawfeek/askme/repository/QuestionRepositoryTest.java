package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    private User userSend;
    private User userRec;
    private Question question;

    @AfterEach
    void tearDown() {
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        userSend = new User();
        userSend.setPassword("1234");
        userSend.setUserName("tawfeek");
        userSend.setEmail("t.shalash1@gmail.com");
        userRepository.save(userSend);
        userRec = new User();
        userRec.setEmail("m.shalash2@gmail.com");
        userRec.setUserName("mohamed");
        userRec.setPassword("12345");
        userRepository.save(userRec);
        question = new Question();
        question.setSender(userSend);
        question.setRecipient(userRec);
        question.setQuestionText("how old are you");
        question.setAnonymity(true);
        question.setCreatedAt(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Test
    void shouldReturnQuestionsAskedForRecipient() {
        List<Question> res = questionRepository.findByRecipient(userRec).get();
        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0).getRecipient()).isEqualTo(userRec);
    }

    @Test
    void shouldReturnQuestionsAskedBySomeOne() {
        List<Question> res = questionRepository.findBySender(userSend).get();
        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0).getRecipient()).isEqualTo(userRec);
    }

    @Test
    void shouldReturnUnAnsweredQuestions(){
        List<Question> res = questionRepository.findUnAnsweredQuestions(userRec.getId()).get();
        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0).getRecipient()).isEqualTo(userRec);
    }
}