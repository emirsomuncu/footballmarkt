package com.somuncu.footballmarkt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageResponse {

    private String fileName;
    private String downloadUrl;
}
