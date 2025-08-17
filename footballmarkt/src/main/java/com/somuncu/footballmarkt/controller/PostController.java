package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Post;
import com.somuncu.footballmarkt.request.post.CreatePostRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.dtos.post.PostDto;
import com.somuncu.footballmarkt.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {

    private final PostService postService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPosts() {
        List<Post> posts = this.postService.getAllPosts();
        List<PostDto> postDtos = this.postService.convertPostListToPostDtoList(posts);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , postDtos));
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPostsByUserName(@PathVariable String userName) {
        List<Post> posts = this.postService.getAllPostsByUserName(userName);
        List<PostDto> postDtos = this.postService.convertPostListToPostDtoList(posts);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , postDtos));
    }

    @GetMapping("/community/{communityName}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPostsByCommunityName(@PathVariable String communityName) {
        List<Post> posts = this.postService.getAllPostsByCommunityName(communityName);
        List<PostDto> postDtos = this.postService.convertPostListToPostDtoList(posts);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , postDtos));
    }

    @GetMapping("/post")
    public ResponseEntity<ApiResponse<PostDto>> getPostById(@RequestParam Long postId) {
        Post post = this.postService.getPostById(postId);
        PostDto postDto = this.postService.convertPostToPostDto(post);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , postDto));
    }

    @PostMapping("/post/create")
    public ResponseEntity<ApiResponse<Void>> createPost(@RequestBody CreatePostRequest createPostRequest , @AuthenticationPrincipal UserDetails userDetails) {
        this.postService.createPost(createPostRequest,userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully" , null));
    }

    @DeleteMapping("/post/delete")
    public ResponseEntity<ApiResponse<Void>> deletePost(@RequestParam Long postId , @AuthenticationPrincipal UserDetails userDetails) {
        this.postService.deletePost(postId,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>( "Successfully" , null));
    }
}
