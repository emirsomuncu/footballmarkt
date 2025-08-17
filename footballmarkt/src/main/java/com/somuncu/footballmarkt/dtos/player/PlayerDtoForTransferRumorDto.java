package com.somuncu.footballmarkt.dtos.player;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForTransferRumorDto {

    private String fullName;
    private String position;
    private Double marketValue;

}
