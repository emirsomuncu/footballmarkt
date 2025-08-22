package com.somuncu.footballmarkt.request.stats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatsRequest {

    private Long id;
    private String season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long totalPass;
    private Long completePass;
    private Long keyPasses;
    private Long totalShoot;
    private Long shootOnTarget;
    private Long duel;
    private Long successfulDuel;
    private Long airDuel;
    private Long airDuelWon;
    private Long totalDribbles;
    private Long successfulDribbles;
    private Long cleanSheets;
    private Double savePercentage;
    private Double goalExpectation;
    private Double assistExpectation;
    private Long yellowCard;
    private Long redCard;

    private Long playerId;
    private Long clubId;
}
