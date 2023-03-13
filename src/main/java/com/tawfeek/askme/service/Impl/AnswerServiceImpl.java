package com.tawfeek.askme.service.Impl;

import com.tawfeek.askme.entity.Answer;
import com.tawfeek.askme.entity.Question;
import com.tawfeek.askme.entity.User;
import com.tawfeek.askme.exception.RecordNotFoundException;
import com.tawfeek.askme.mapper.AnswerMapper;
import com.tawfeek.askme.mapper.QuestionAnswerMapper;
import com.tawfeek.askme.model.answer.AnswerRequestDTO;
import com.tawfeek.askme.model.answer.AnswerResponseDTO;
import com.tawfeek.askme.model.questionAndAnswer.QuestionAndAnswerDTO;
import com.tawfeek.askme.repository.AnswerRepository;
import com.tawfeek.askme.repository.QuestionRepository;
import com.tawfeek.askme.repository.UserRepository;
import com.tawfeek.askme.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Override
    public List<QuestionAndAnswerDTO> getUserAnsweredQuestions(Long userId) {
        User currentUser=userRepository.findById(userId)
                .orElseThrow();
        return answerRepository.getUserAnsweredQuestions(currentUser.getId())
                .orElse(new ArrayList<>()).stream()
                .map(answer->questionAnswerMapper.toDTO(answer)).toList();
    }

    @Override
    public List<QuestionAndAnswerDTO> getMyAnsweredQuestions() {
        var principal =(UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email=principal.getUsername();
        User currentUser=userRepository.findByEmail(email)
                .orElseThrow();
        return answerRepository.getUserAnsweredQuestions(currentUser.getId())
                .orElse(new ArrayList<>()).stream()
                .map(answer->questionAnswerMapper.toDTO(answer)).toList();
    }

    @Override
    public AnswerResponseDTO AnswerQuestion(AnswerRequestDTO answerRequest) {
        Question question = questionRepository.findById(answerRequest.getQuestionId()).
                orElseThrow();
        var principal =(UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email=principal.getUsername();
        User currentUser=userRepository.findByEmail(email)
                .orElseThrow();
        Answer answer = answerMapper.toEntity(answerRequest,question,currentUser);
        answerRepository.save(answer);
        return answerMapper.toDTO(answer);
    }

    @Override
    public void DeleteQuestionAnswer(Long answerId) throws RecordNotFoundException {
        var principal =(UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email=principal.getUsername();
        User currentUser=userRepository.findByEmail(email)
                .orElseThrow();
        Answer answer=answerRepository.findById(answerId).orElseThrow();
        if(answer.getAnswerOwner().getId()!= currentUser.getId())
            throw new RecordNotFoundException("you don't own this message");


        answerRepository.delete(answer);
    }
}
