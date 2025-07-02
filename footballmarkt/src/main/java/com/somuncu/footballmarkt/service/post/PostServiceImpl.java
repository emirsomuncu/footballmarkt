package com.somuncu.footballmarkt.service.post;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NoCommunityFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.post.NoPostFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.CommunityRepository;
import com.somuncu.footballmarkt.dao.PostRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.entities.Post;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.post.CreatePostRequest;
import com.somuncu.footballmarkt.response.dtos.post.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ModelMapperService modelMapperService;


    @Override
    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsByUserName(String userName) {
        return this.postRepository.findPostByUserName(userName);
    }

    @Override
    public List<Post> getAllPostsByCommunityName(String communityName) {
        return this.postRepository.findPostByCommunityCommunityName(communityName);
    }

    @Override
    public Post getPostById(Long postId) {
         return this.postRepository.findById(postId).orElseThrow(()-> new NoPostFoundException("No post found"));
    }

    @Transactional
    @Override
    public void createPost(CreatePostRequest createPostRequest, UserDetails userDetails) {

        User curentUser = this.getCurrentUser(userDetails);

        Long communityId = createPostRequest.getCommunityId();
        Community community = this.communityRepository.findById(communityId).orElseThrow(() -> new NoCommunityFoundException("No community found to share post"));
        List<Community> currentUserCommunities = curentUser.getCommunities();
        if (!currentUserCommunities.contains(community)) {
            throw new NotAbleToDoThisOperationException("You can not post without registering to the community");
        }

        Post post = new Post();
        post.setText(createPostRequest.getText());
        post.setUser(curentUser);
        post.setCommunity(community);

        community.getPosts().add(post);
        community.updateNumOfPosts();
        communityRepository.save(community);

    }

    @Transactional
    @Override
    public void deletePost(Long postId, UserDetails userDetails) {

        Post post = this.postRepository.findById(postId).orElseThrow(()-> new NoPostFoundException("No post found to delete"));
        User postOwner = post.getUser();
        User currentUser = this.getCurrentUser(userDetails);
        if( currentUser != postOwner) {
            throw new NotAbleToDoThisOperationException("You can only delete your posts");
        }

        Community community = post.getCommunity();
        community.getPosts().remove(post);
        community.updateNumOfPosts();
        communityRepository.save(community);

    }

    @Override
    public PostDto convertPostToPostDto(Post post) {
        PostDto postDto = this.modelMapperService.forResponse().map(post , PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> convertPostListToPostDtoList(List<Post> posts) {

        return posts.stream().map(this::convertPostToPostDto).collect(Collectors.toList());

    }


    @Override
    public User getCurrentUser(UserDetails userDetails) {

        String userMail = userDetails.getUsername();
        return this.userRepository.findUserByEmail(userMail).orElseThrow();
    }
}
