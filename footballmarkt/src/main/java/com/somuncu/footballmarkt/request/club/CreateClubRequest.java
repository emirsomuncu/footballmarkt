package com.somuncu.footballmarkt.request.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClubRequest {

    private Long id;
    private String name;
    private int foundationYear;
    private Long leagueId;
    private List<Long> imagesIds;
}
