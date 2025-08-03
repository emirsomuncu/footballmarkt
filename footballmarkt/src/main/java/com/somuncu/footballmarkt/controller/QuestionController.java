package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Question;
import com.somuncu.footballmarkt.request.question.CreateQuestionRequest;
import com.somuncu.footballmarkt.request.question.UpdateQuestionRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllQuestions() {
        List<Question> questions = this.questionService.getAllQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully listed" , questions )) ;
    }

    @GetMapping("/{category}")
    public ResponseEntity<ApiResponse> getAllQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = this.questionService.getAllQuestionsByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully listed" , questions )) ;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createQuestion(@RequestBody CreateQuestionRequest createQuestionRequest) {
        Question question = this.questionService.createQuestion(createQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully created" , question)) ;
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateQuestion(@RequestBody UpdateQuestionRequest updateQuestionRequest) {
        Question question = this.questionService.updateQuestion(updateQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully updated" , question)) ;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteQuestion(@RequestParam Long id) {
        this.questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully deleted", null));
    }

}
