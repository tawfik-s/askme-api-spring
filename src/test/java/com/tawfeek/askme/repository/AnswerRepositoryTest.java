package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private User userSend;
    private User userRec;
    private Question question;
    private Answer answer;


    @AfterEach
    void tearDown() {
        answerRepository.deleteAll();
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
        answer = new Answer();
        answer.setQuestion(question);
        answer.setRecipient(userRec);
        answer.setAnswerText("twenty one years old");
        answer.setCreatedAt(LocalDateTime.now());
        answerRepository.save(answer);
    }

    @Test
    void findByQuestion() {
        List<Answer> answers = answerRepository.findByQuestion(question);
        assertThat(answers.size()).isEqualTo(1);
    }

    @Test
    void findByRecipient() {
        List<Answer> answers = answerRepository.findByRecipient(userRec);
        assertThat(answers.size()).isEqualTo(1);
    }
}