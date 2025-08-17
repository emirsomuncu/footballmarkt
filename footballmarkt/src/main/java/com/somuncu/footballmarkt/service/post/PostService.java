package com.somuncu.footballmarkt.service.post;

import com.somuncu.footballmarkt.entities.Post;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.post.CreatePostRequest;
import com.somuncu.footballmarkt.dtos.post.PostDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PostService {

    public List<Post> getAllPosts();
    public List<Post> getAllPostsByUserName(String userName);
    public List<Post> getAllPostsByCommunityName(String communityName);
    public Post getPostById(Long postId);
    public void createPost(CreatePostRequest createPostRequest , UserDetails userDetails);
    public void deletePost(Long postId , UserDetails userDetails);
    public PostDto convertPostToPostDto(Post post);
    public List<PostDto> convertPostListToPostDtoList(List<Post> posts);
    public User getCurrentUser(UserDetails userDetails);

}
