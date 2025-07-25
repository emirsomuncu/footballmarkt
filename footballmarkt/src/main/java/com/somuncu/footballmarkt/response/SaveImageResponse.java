package com.somuncu.footballmarkt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveImageResponse {

    private Long id;
    private String fileName;
    private String downloadUrl;
}
