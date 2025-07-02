package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community , Long> {

    public Optional<Community> findCommunityByCommunityName(String communityName);
}
