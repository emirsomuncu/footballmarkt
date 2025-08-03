package com.somuncu.footballmarkt.request.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuizRequest {

    private String name;
    private int numOfQuestions;
    private List<String> questionCategories;
}
