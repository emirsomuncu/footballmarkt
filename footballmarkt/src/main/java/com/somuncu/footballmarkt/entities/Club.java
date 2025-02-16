package com.somuncu.footballmarkt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int foundationYear;
    private Double clubValue;

    @JsonIgnore
    @OneToMany(mappedBy = "club" , cascade = CascadeType.ALL )
    private List<Player> players = new ArrayList<>();

    @ManyToOne
    private League league ;

    public Club(Long id, String name, int foundationYear, Double clubValue) {
        this.id = id;
        this.name = name;
        this.foundationYear = foundationYear;
        this.clubValue = clubValue;
    }

    public void updateClubValue() {

       List<Double> playersValuesList = players.stream().map(player -> {
           Double playerMarketValue = player.getMarketValue();
           return playerMarketValue;
       }).collect(Collectors.toList());

       double totalValue = 0.0 ;
       for(Double runner : playersValuesList) {
           totalValue = totalValue + runner;
       }

       this.clubValue = totalValue;
    }


}
