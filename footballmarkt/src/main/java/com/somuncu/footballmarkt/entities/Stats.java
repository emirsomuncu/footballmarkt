package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Long season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long yellowCard;
    private Long redCard;

    @ManyToOne
    private Player player ;

    @ManyToOne
    private Club club;

    public Stats(Long id, Long season, Long goal, Long assist, Long playedMatch, Long firstEleven, Long playedMinutes, Long yellowCard, Long redCard) {
        this.id = id;
        this.season = season;
        this.goal = goal;
        this.assist = assist;
        this.playedMatch = playedMatch;
        this.firstEleven = firstEleven;
        this.playedMinutes = playedMinutes;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }
}
