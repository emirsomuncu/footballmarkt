package com.somuncu.footballmarkt.response;

import com.somuncu.footballmarkt.response.dtos.club.ClubDtoForPlayClubValueEstimationGameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayClubValueEstimationGameResponse {

    private ClubDtoForPlayClubValueEstimationGameResponse correctClub;

}
