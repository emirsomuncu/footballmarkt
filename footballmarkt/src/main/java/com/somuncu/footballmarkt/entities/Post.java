package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@SoftDelete
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @CreationTimestamp()
    private Date creationTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Community community;


}
