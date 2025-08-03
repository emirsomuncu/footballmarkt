package com.somuncu.footballmarkt.service.question;

import com.somuncu.footballmarkt.core.utiliites.exceptions.question.NoQuestionFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.QuestionRepository;
import com.somuncu.footballmarkt.entities.Question;
import com.somuncu.footballmarkt.request.question.CreateQuestionRequest;
import com.somuncu.footballmarkt.request.question.UpdateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final ModelMapperService modelMapperService;


    @Override
    @Cacheable(value = "getAllQuestions")
    public List<Question> getAllQuestions() {
        return this.questionRepository.findAll();
    }

    @Override
    @Cacheable(value = "getAllQuestionsByCategory" , key = "#category")
    public List<Question> getAllQuestionsByCategory(String category) {

        List<Question> questions = this.questionRepository.findAllByCategory(category);
        if(questions.isEmpty()) {
            throw new NoQuestionFoundException("No question found for category '" + category + "'" );
        }

        return questions;
    }

    @Override
    public String getCategoryOfQuestionById(Long id) {
        Question question= this.questionRepository.findById(id).orElseThrow(()-> new NoQuestionFoundException("No question found to get category"));
        return question.getCategory();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "getAllQuestions" , allEntries = true) ,
                    @CacheEvict(value = "getAllQuestionsByCategory" , key = "#createQuestionRequest.category")
            }
    )
    public Question createQuestion(CreateQuestionRequest createQuestionRequest) {

        Question question = this.modelMapperService.forRequest().map(createQuestionRequest , Question.class);
        questionRepository.save(question);
        return question;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "getAllQuestions" , allEntries = true) ,
                    @CacheEvict(value = "getAllQuestionsByCategory" , key = "#updateQuestionRequest.category")
            }
    )
    public Question updateQuestion(UpdateQuestionRequest updateQuestionRequest) {

        Question question = this.questionRepository.findById(updateQuestionRequest.getId()).orElseThrow(()-> new NoQuestionFoundException("No question found exception"));
        this.modelMapperService.forRequest().map(updateQuestionRequest , question);
        questionRepository.save(question);
        return question;
    }



    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "getAllQuestions" , allEntries = true) ,
                    @CacheEvict(value = "getAllQuestionsByCategory", key = "#root.target.getCategoryOfQuestionById(#id)", beforeInvocation = true)
            }
    )
    public void deleteQuestion(Long id) {

        Question question = this.questionRepository.findById(id).orElseThrow(()-> new NoQuestionFoundException("No question found to delete"));
        this.questionRepository.delete(question);
    }
}
