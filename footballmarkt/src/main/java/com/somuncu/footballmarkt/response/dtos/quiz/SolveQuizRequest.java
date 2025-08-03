package com.somuncu.footballmarkt.response.dtos.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolveQuizRequest {

    private List<String> answers;

}
