package com.somuncu.footballmarkt.request.dtos.stats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForStatsDto {

    private String firstName;
    private String lastName;
    private String position;

}
