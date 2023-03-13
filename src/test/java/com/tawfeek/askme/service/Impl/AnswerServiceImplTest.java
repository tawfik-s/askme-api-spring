package com.tawfeek.askme.service.Impl;


import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.exception.RecordNotFoundException;
import com.tawfeek.askme.mapper.AnswerMapper;
import com.tawfeek.askme.mapper.QuestionAnswerMapper;
import com.tawfeek.askme.model.Role;
import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;
import com.tawfeek.askme.repository.AnswerRepository;
import com.tawfeek.askme.repository.QuestionRepository;
import com.tawfeek.askme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerMapper answerMapper;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionAnswerMapper questionAnswerMapper;

    @InjectMocks
    private AnswerServiceImpl answerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        var user = new User(1L, "tawfeek", "123456", Role.USER, "test@test.com");
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldReturnAnsweredQuestionByUser() {
        Long userId = 1L;
        User currentUser = new User();
        currentUser.setId(userId);
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answers.add(answer);
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(answerRepository.getUserAnsweredQuestions(userId)).thenReturn(Optional.of(answers));
        when(questionAnswerMapper.toDTO(answer)).thenReturn(new QuestionAndAnswerDTO());

        // when
        List<QuestionAndAnswerDTO> result = answerService.getUserAnsweredQuestions(userId);
        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void shouldReturnUserAnsweredQuestions() {
        User currentUser = new User(1L, "tawfeek", "123456", Role.USER, "test@test.com");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(currentUser, "password", currentUser.getAuthorities()));
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answers.add(answer);
        when(userRepository.findByEmail(currentUser.getUsername())).thenReturn(Optional.of(currentUser));
        when(answerRepository.getUserAnsweredQuestions(currentUser.getId())).thenReturn(Optional.of(answers));
        when(questionAnswerMapper.toDTO(answer)).thenReturn(new QuestionAndAnswerDTO());

        // when
        List<QuestionAndAnswerDTO> result = answerService.getMyAnsweredQuestions();

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void shouldAddAnswerToQuestion() {
        AnswerRequestDTO answerRequest = new AnswerRequestDTO();
        answerRequest.setQuestionId(1L);
        answerRequest.setAnswerText("Test answer");

        Question question = new Question();
        question.setId(answerRequest.getQuestionId());

        User user = new User();
        user.setId(1L);

        Answer answer = new Answer();
        answer.setId(1L);
        answer.setAnswerText(answerRequest.getAnswerText());
        answer.setQuestion(question);
        answer.setAnswerOwner(user);

        when(questionRepository.findById(answerRequest.getQuestionId())).thenReturn(Optional.of(question));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(answerMapper.toEntity(answerRequest, question, user)).thenReturn(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerMapper.toDTO(answer)).thenReturn(new AnswerResponseDTO());

        // Act
        AnswerResponseDTO result = answerService.AnswerQuestion(answerRequest);

        // Assert
        assertThat(result).isNotNull();
        verify(questionRepository).findById(answerRequest.getQuestionId());
        verify(userRepository).findByEmail(anyString());
        verify(answerMapper).toEntity(answerRequest, question, user);
        verify(answerRepository).save(answer);
        verify(answerMapper).toDTO(answer);
    }

    @Test
    void shouldDeleteQuestionAnswerSuccessfully() {
        Long answerId = 1L;
        Answer answer = new Answer();
        answer.setId(answerId);
        User user = new User();
        user.setId(1L);
        answer.setAnswerOwner(user);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));
        when(answerMapper.toDTO(answer)).thenReturn(new AnswerResponseDTO());

        // Act
        AnswerResponseDTO result = answerService.DeleteQuestionAnswer(answerId);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository).findByEmail(anyString());
        verify(answerRepository).findById(answerId);
        verify(answerRepository).delete(answer);
        verify(answerMapper).toDTO(answer);
    }

    @Test
     void shouldThrowRecordNotFoundExceptionWhenDeletingAnswer() throws RecordNotFoundException {
        // Arrange
        Long answerId = 5L;
        Answer answer = new Answer();
        answer.setId(answerId);
        User user = new User();
        user.setId(6L);
        answer.setAnswerOwner(user);
        var currentUser =
                new User(1L, "tawfeek", "123456"
                        , Role.USER, "test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(currentUser));
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

        // Assert
        // Expects RecordNotFoundException to be thrown
        assertThrows(RecordNotFoundException.class, () -> {
            answerService.DeleteQuestionAnswer(answerId);
        });
    }
}