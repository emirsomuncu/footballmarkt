package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image , Long> {

}
