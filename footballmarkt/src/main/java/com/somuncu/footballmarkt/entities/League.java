package com.somuncu.footballmarkt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double leagueValue;

    @JsonIgnore
    @OneToMany(mappedBy = "league" , cascade = {CascadeType.PERSIST, CascadeType.REFRESH ,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Club> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "league" , cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "league" , cascade = CascadeType.ALL)
    private List<Trophy> trophies;


    public Double updateLeagueValue() {

        Double totalClubValue = 0.0;

        for(Club runner : clubs) {
            Double clubValue = runner.getClubValue();
            totalClubValue = totalClubValue + clubValue;
        }

        this.leagueValue = totalClubValue;
        return totalClubValue;
    }

    public League(Long id, String name, Double leagueValue) {
        this.id = id;
        this.name = name;
        this.leagueValue = leagueValue;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leagueValue=" + leagueValue +
                '}';
    }
}
