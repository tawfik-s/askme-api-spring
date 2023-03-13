package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.model.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        userSend = new User("mohammed","pass2",Role.USER,"m.shalash1@gmail.com");
        userRepository.save(userSend);
        userRec = new User("tawfeek","pass1",Role.USER,"t.shalash1@gmail.com");
        userRepository.save(userRec);
        question = new Question(null,userSend,userRec,"how are you ",false,LocalDateTime.now());
        questionRepository.save(question);
        answer = new Answer(null,question,userRec,"find ",LocalDateTime.now());
        answerRepository.save(answer);
    }

    @Test
    void findByQuestionShouldReturnQuestionAnswers() {
        List<Answer> answers = answerRepository.findByQuestion(question)
                .orElseThrow();
        assertThat(answers.size()).isEqualTo(1);
    }

    @Test
    void findByAnswerOwnerShouldReturnAnswersByOwner() {
        List<Answer> answers = answerRepository.findByAnswerOwner(userRec)
                .orElseThrow();
        assertThat(answers.size()).isEqualTo(1);
    }

    @Test
    void getUserAnsweredQuestionsShouldReturnUserAnsweredQuestions(){
        List<Answer> answers = answerRepository.getUserAnsweredQuestions(userRec.getId())
                .orElseThrow();
        assertThat(answers.size()).isEqualTo(1);
    }
}