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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Long noOfCommunityOwnership = 0L ;
    private Long noOfCommunityRegistered = 0L ;

    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "creatorOfCommunity" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Community> communityOwnership;

    @ManyToMany(mappedBy = "users")
    private List<Community> communities = new ArrayList<>();


    public void updateNoOfCommunityOwnership() {
        Long count = 0L;
        for (Community runner : communityOwnership) {
            count++ ;
        }
        this.noOfCommunityOwnership = count;
    }

    public void updateNoOfCommunityRegistered() {
        Long count = 0L ;
        for(Community communityRunner : communities) {
            count++;
        }
        count = count + 1 ;
        this.noOfCommunityRegistered = count;
    }

}
