package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.request.quiz.CreateQuizRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.quiz.QuizDto;
import com.somuncu.footballmarkt.request.quiz.SolveQuizRequest;
import com.somuncu.footballmarkt.service.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/quizzes")
public class QuizzesController {

    private final QuizService quizService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PageResponse<QuizDto>>> getAllQuizzes(@RequestParam int pagingOffset) {
        PageResponse<QuizDto> pageResponse = this.quizService.getAllQuizzes(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfuly listed" , pageResponse));
    }

    @GetMapping("/{id}/quiz")
    public ResponseEntity<ApiResponse<QuizDto>> getQuizById(@PathVariable Long id) {
        QuizDto quizDto = this.quizService.getQuizById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfuly" , quizDto));
    }

    @GetMapping("/by/{userName}")
    public ResponseEntity<ApiResponse<List<QuizDto>>> getQuizzesByUserName(@PathVariable String userName  , @AuthenticationPrincipal UserDetails userDetails ) {
        List<QuizDto> quizzesByUserName = this.quizService.getQuizzesByUserName(userName , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfuly listed" , quizzesByUserName));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(@RequestBody CreateQuizRequest createQuizRequest ,@AuthenticationPrincipal UserDetails userDetails) {
        QuizDto quizDto = this.quizService.createQuiz(createQuizRequest , userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfuly created" , quizDto));
    }

    @PutMapping("/solve")
    public ResponseEntity<ApiResponse<QuizDto>> solveQuiz(@RequestParam Long quizId , @RequestBody SolveQuizRequest solveQuizRequest, @AuthenticationPrincipal UserDetails userDetails) {
        QuizDto quizDto = this.quizService.solveQuiz(quizId , solveQuizRequest , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfuly solved" , quizDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@RequestParam Long id , @AuthenticationPrincipal UserDetails userDetails) {
        this.quizService.deleteQuiz(id , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfuly deleted" , null));
    }
}
