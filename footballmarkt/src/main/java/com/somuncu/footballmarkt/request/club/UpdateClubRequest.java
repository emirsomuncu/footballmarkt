package com.somuncu.footballmarkt.request.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClubRequest {

    private Long id;
    private String name;
    private int foundationYear;
    private Long leagueId;

}
