package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long> {
}
