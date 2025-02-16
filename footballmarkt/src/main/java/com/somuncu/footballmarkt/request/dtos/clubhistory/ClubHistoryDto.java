package com.somuncu.footballmarkt.request.dtos.clubhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubHistoryDto {

    private String playerFirstName;
    private String playerLastName;
    private List<ClubDtoForClubHistoryDto> clubs;

}
