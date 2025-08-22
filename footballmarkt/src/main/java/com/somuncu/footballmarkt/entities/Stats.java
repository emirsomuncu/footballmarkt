package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@Entity
@Table
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long totalPass;
    private Long completePass;
    private Double passingAccuracyRate;
    private Long keyPasses;
    private Long totalShoot;
    private Long shootOnTarget;
    private Double successfulShootingRate;
    private Long duel;
    private Long successfulDuel;
    private Double successfulDuelRate;
    private Long airDuel;
    private Long airDuelWon;
    private Double airDuelSuccessRate;
    private Long totalDribbles;
    private Long successfulDribbles;
    private Double successfulDribblesRate;
    private Double dribblesPerMatch;
    private Long cleanSheets;
    private Double savePercentage;
    private Double goalExpectation;
    private Double assistExpectation;
    private Long yellowCard;
    private Long redCard;

    @ManyToOne
    private Player player ;

    @ManyToOne
    private Club club;

    public Stats(Long id, String season, Long goal, Long assist, Long playedMatch, Long firstEleven, Long playedMinutes, Long yellowCard, Long redCard) {
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

    private double calculateAndRoundRate(Long numerator, Long denominator) {
        if (denominator == null || denominator <= 0) {
            return 0.0;
        }

        double rawRate = ((double) numerator / denominator) * 100;
        BigDecimal bd = new BigDecimal(String.valueOf(rawRate));
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    private double safeDivideAndRound(Long numerator, Long denominator) {
        if (denominator == null || denominator <= 0) {
            return 0.0;
        }

        double rawResult = (double) numerator / denominator;
        BigDecimal bd = new BigDecimal(String.valueOf(rawResult));
        bd = bd.setScale(2, RoundingMode.HALF_UP);


        return bd.doubleValue();
    }

    public void evaluatePassingAccuracyRate() {
        this.passingAccuracyRate = calculateAndRoundRate(this.completePass, this.totalPass);
    }

    public void evaluateSuccessfulShootingRate() {
        this.successfulShootingRate = calculateAndRoundRate(this.shootOnTarget, this.totalShoot);
    }

    public void evaluateSuccessfulDuelRate() {
        this.successfulDuelRate = calculateAndRoundRate(this.successfulDuel, this.duel);
    }

    public void evaluateSuccessfulDribblesRate() {
        this.successfulDribblesRate = calculateAndRoundRate(this.successfulDribbles, this.totalDribbles);
    }

    public void evaluateAirDuelSuccessRate() {
        this.airDuelSuccessRate = calculateAndRoundRate(this.airDuelWon , this.airDuel);
    }
    public void evaluateDribblesPerMatch() {
        this.dribblesPerMatch = safeDivideAndRound(this.totalDribbles, this.playedMatch);
    }

}
