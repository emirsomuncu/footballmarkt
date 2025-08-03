package com.somuncu.footballmarkt.response.dtos.quiz;

import com.somuncu.footballmarkt.response.dtos.question.QuestionDtoForQuizDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto implements Serializable {

    private Long id;
    private int numOfQuestions;
    private boolean solved;
    private Double score;
    private List<QuestionDtoForQuizDto> questionList;
    private String userName;

}
