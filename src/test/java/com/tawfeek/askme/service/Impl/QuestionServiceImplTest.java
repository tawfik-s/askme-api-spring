package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.mapper.QuestionMapper;
import com.tawfeek.askme.model.Role;
import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;
import com.tawfeek.askme.model.user.UserResponseDTO;
import com.tawfeek.askme.repository.QuestionRepository;
import com.tawfeek.askme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        var user = new User(1L, "tawfeek", "123456", Role.USER, "test@test.com");
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("Should return list of un-answered questions")
    void shouldReturnListOfUnansweredQuestions() {
        // given
        var user = new User(1L, "test", "password", Role.USER, "test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        var question1 = new Question(1L, user, user, "Question 1", false, LocalDateTime.now());
        var question2 = new Question(2L, user, user, "Question 2", false, LocalDateTime.now());
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        when(questionRepository.findUnAnsweredQuestions(user.getId())).thenReturn(Optional.of(questions));
        when(questionMapper.toDTO(question1)).thenReturn(new QuestionResponseDTO(question1.getId(),question1.getQuestionText(),
                new UserResponseDTO(question1.getSender().getId(),question1.getSender().getUsername(),question1.getSender().getEmail())));
        when(questionMapper.toDTO(question2)).thenReturn(new QuestionResponseDTO(question2.getId(), question2.getQuestionText()
                ,new UserResponseDTO(question1.getSender().getId(),question1.getSender().getUsername(),question1.getSender().getEmail())));

        // when
        List<QuestionResponseDTO> result = questionService.getMyUnAnsweredQuestions();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(question1.getId());
        assertThat(result.get(1).getId()).isEqualTo(question2.getId());
    }

    @Test
    @DisplayName("should add question and return question response")
    void shouldAddQuestion(){
        // given
        var user = new User(1L, "test", "password", Role.USER, "test@test.com");
        var recipient =new User(2L,"tawfeek","123456", Role.USER,"t.shalash1@gmail.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));
        var question1 = new Question(1L, user, user, "Question 1", false, LocalDateTime.now());
        List<Question> questions = new ArrayList<>();
        questions.add(question1);

        when(questionMapper.toDTO(question1)).thenReturn(new QuestionResponseDTO(question1.getId(),question1.getQuestionText(),
                new UserResponseDTO(question1.getSender().getId(),question1.getSender().getUsername(),question1.getSender().getEmail())));
        QuestionRequestDTO questionRequestDTO=new QuestionRequestDTO(2L,"hello my friend",false);
        when(questionMapper.toEntity(questionRequestDTO,recipient,user)).thenReturn(question1);
        // when
        QuestionResponseDTO result = questionService.AskUserQuestion(questionRequestDTO);

        // then
        verify(questionMapper,times(1)).toDTO(question1);
        verify(questionMapper,times(1)).toEntity(questionRequestDTO,recipient,user);
        verify(questionRepository,times(1)).save(question1);
        assertThat(result.getId()).isEqualTo(question1.getId());
    }

}