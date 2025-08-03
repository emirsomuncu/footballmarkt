package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz , Long> {

    public List<Quiz> findQuizByUserName(String userName);

}
