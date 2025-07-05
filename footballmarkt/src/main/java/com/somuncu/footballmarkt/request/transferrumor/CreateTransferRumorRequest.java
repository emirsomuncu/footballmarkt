package com.somuncu.footballmarkt.request.transferrumor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferRumorRequest {

    private double probability;
    private Long playerId;
    private Long currentClubId;
    private Long nextClubId;
}
