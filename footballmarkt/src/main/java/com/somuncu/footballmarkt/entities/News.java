package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Lob
    private String text;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Club club;

    @OneToMany(mappedBy = "news" , cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

}
