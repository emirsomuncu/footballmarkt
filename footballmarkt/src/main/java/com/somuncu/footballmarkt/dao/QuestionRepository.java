package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question , Long> {

    public List<Question> findAllByCategory(String category);
}
