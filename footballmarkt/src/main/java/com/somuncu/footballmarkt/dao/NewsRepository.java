package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News , Long> {
}
