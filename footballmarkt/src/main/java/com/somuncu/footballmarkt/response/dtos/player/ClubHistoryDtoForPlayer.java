package com.somuncu.footballmarkt.response.dtos.player;

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
