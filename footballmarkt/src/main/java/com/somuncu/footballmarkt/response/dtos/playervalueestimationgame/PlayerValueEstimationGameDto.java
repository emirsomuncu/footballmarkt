package com.somuncu.footballmarkt.response.dtos.playervalueestimationgame;

import com.somuncu.footballmarkt.response.dtos.player.PlayerDtoForPlayerValueEstimationGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerValueEstimationGameDto {

    private Long id;
    private PlayerDtoForPlayerValueEstimationGameDto playerOne;
    private PlayerDtoForPlayerValueEstimationGameDto playerTwo;


}
