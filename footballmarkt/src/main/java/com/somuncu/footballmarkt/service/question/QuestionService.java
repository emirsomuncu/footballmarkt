package com.somuncu.footballmarkt.service.question;

import com.somuncu.footballmarkt.entities.Question;
import com.somuncu.footballmarkt.request.question.CreateQuestionRequest;
import com.somuncu.footballmarkt.request.question.UpdateQuestionRequest;

import java.util.List;

public interface QuestionService {

    public List<Question> getAllQuestions();
    public List<Question> getAllQuestionsByCategory(String category);
    public String getCategoryOfQuestionById(Long id);
    public Question createQuestion(CreateQuestionRequest createQuestionRequest);
    public Question updateQuestion(UpdateQuestionRequest updateQuestionRequest);
    public void deleteQuestion(Long id);
}
