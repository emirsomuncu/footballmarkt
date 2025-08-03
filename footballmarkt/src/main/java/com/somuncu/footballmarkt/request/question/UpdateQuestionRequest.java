package com.somuncu.footballmarkt.request.question;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionRequest {

    private Long id;
    private String category;
    private String question;
    private String firstOption;
    private String secondOption;
    private String thirdOption;
    private String fourthOption;
    private String correctOption;
}
