package com.somuncu.footballmarkt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
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
    @OneToMany(mappedBy = "league" , cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Club> clubs;


    public Double updateLeagueValue() {

        Double totalClubValue = 0.0;

        for(Club runner : clubs) {
            Double clubValue = runner.getClubValue();
            totalClubValue = totalClubValue + clubValue;
        }

        this.leagueValue = totalClubValue;
        return totalClubValue;
    }

}
