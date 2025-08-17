package com.somuncu.footballmarkt.dtos.clubhistory;

import com.somuncu.footballmarkt.dtos.club.ClubDtoForPlayer;
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
