package com.somuncu.footballmarkt.request.player;

import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.ClubHistory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPlayerRequest {

    private Long id ;
    private String firstName;
    private String lastName;
    private String nation;
    private int age;
    private String position;
    private String foot;
    private Double marketValue;
    private Long clubId;
    private Long clubHistoryId;
}

