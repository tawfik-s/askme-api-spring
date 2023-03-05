package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.exception.RecordNotFoundException;
import com.tawfeek.askme.mapper.QuestionMapper;
import com.tawfeek.askme.model.question.QuestionRequestDTO;
import com.tawfeek.askme.model.question.QuestionResponseDTO;
import com.tawfeek.askme.repository.QuestionRepository;
import com.tawfeek.askme.repository.UserRepository;
import com.tawfeek.askme.service.QuestionService;
import com.tawfeek.askme.service.UserService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionResponseDTO> getMyUnAnsweredQuestions() {
        var principal =(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var email=principal.getUsername();
        User user=userRepository.findByEmail(email)
                .orElseThrow();
        List<Question> unAnsweredQuestions = questionRepository.findUnAnsweredQuestions(user.getId())
                .orElse(new ArrayList<Question>());

        return unAnsweredQuestions.stream()
                .map(q->questionMapper.toDTO(q))
                .toList();
    }

    @Override
    public QuestionResponseDTO AskUserQuestion(QuestionRequestDTO questionRequest) {
        var principal =(UserDetails)SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email=principal.getUsername();
        User sender=userRepository.findByEmail(email)
                .orElseThrow();

        User receiver = userRepository.findById(questionRequest.getRecipientId())
                .orElseThrow();
        Question question = questionMapper.toEntity(questionRequest,receiver,sender);

        questionRepository.save(question);

        return questionMapper.toDTO(question);
    }
}
