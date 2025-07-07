package com.somuncu.footballmarkt.response;

import com.somuncu.footballmarkt.response.dtos.player.PlayerResponseForPlayPlayerValueEstimationGameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayPlayerValueEstimationGameResponse {

    private PlayerResponseForPlayPlayerValueEstimationGameResponse correctPLayer;
}
