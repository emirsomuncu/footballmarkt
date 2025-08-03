package com.somuncu.footballmarkt.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@SoftDelete
public class Quiz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int numOfQuestions;
    private boolean solved;
    private int numOfCorrectAnswers;
    private int numOfWrongAnswers;
    private Double score;

    @ManyToMany
    @OrderBy("id asc")
    private List<Question> questionList ;

    @ManyToOne
    private User user;
}
