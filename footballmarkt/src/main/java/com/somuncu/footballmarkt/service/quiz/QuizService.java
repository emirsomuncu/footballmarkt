package com.somuncu.footballmarkt.service.quiz;

import com.somuncu.footballmarkt.request.quiz.CreateQuizRequest;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.quiz.QuizDto;
import com.somuncu.footballmarkt.request.quiz.SolveQuizRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface QuizService {

    public PageResponse<QuizDto> getAllQuizzes(int pagingOffset);
    public QuizDto getQuizById(Long id);
    public List<QuizDto> getQuizzesByUserName(String userName , UserDetails userDetails);
    public QuizDto createQuiz(CreateQuizRequest createQuizRequest , UserDetails userDetails);
    public QuizDto solveQuiz(Long quizId , SolveQuizRequest solveQuizRequest, UserDetails userDetails);
    public String getQuizOwnerByQuizId(Long quizId);
    public void deleteQuiz(Long id , UserDetails userDetails);
}
