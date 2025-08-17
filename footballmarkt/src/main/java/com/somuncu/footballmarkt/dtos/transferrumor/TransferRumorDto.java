package com.somuncu.footballmarkt.dtos.transferrumor;

import com.somuncu.footballmarkt.dtos.club.ClubDtoForTransferRumorDto;
import com.somuncu.footballmarkt.dtos.player.PlayerDtoForTransferRumorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRumorDto {

    private double probability;
    private PlayerDtoForTransferRumorDto player;
    private ClubDtoForTransferRumorDto currentClub;
    private ClubDtoForTransferRumorDto nextClub;

}
