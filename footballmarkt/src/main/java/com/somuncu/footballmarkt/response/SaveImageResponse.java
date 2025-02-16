package com.somuncu.footballmarkt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveImageResponse {

    private String fileName;
    private String downloadUrl;
    private String playerFirstName;
    private String playerLastName;

}
