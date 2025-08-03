package com.somuncu.footballmarkt.response.dtos.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDtoForQuizDto implements Serializable {

    private String question;
    private String firstOption;
    private String secondOption;
    private String thirdOption;
    private String fourthOption;

}
