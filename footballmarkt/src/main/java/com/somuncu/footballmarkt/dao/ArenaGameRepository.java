package com.somuncu.footballmarkt.dao;


import com.somuncu.footballmarkt.entities.ArenaGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArenaGameRepository extends JpaRepository<ArenaGame, Long> {
}
