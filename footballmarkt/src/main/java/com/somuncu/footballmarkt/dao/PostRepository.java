package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post , Long> {

    public List<Post> findPostByUserName(String userName);
    public List<Post> findPostByCommunityCommunityName(String communityName);
}
