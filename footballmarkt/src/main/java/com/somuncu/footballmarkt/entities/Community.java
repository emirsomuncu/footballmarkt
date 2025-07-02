package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@SoftDelete
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String communityName;

    private Long numOfMembers = 1L ;
    private Long numOfPosts = 0L ;

    @CreationTimestamp
    private Date creationTime;

    @OneToMany(mappedBy = "community" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @ManyToOne
    private User creatorOfCommunity;

    @ManyToMany
    private List<User> users = new ArrayList<>();


    public void updateNumOfPosts() {
        Long postCount = 0L;
        for(Post eachPost : posts) {
            postCount++;
        }
        this.numOfPosts=postCount;
    }

    public void updateNumOfMembers() {
        Long memberCount = 0L;
        for(User eachUser : users ) {
            memberCount++;
        }
        this.numOfMembers=memberCount;
    }


}
