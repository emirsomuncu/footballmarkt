package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table
public class Trophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String season;

    @OneToMany(mappedBy = "trophy" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    private League league;

    @ManyToOne
    private Club club;

    public Trophy(Long id, String name, String season) {
        this.id = id;
        this.name = name;
        this.season = season;
    }

    @Override
    public String toString() {
        return "Trophy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", season=" + season +
                '}';
    }
}
