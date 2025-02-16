package com.somuncu.footballmarkt.request.dtos.player;

import com.somuncu.footballmarkt.request.dtos.player.ClubDtoForPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubHistoryDtoForPlayer {

    private List<ClubDtoForPlayer> clubs;

}
