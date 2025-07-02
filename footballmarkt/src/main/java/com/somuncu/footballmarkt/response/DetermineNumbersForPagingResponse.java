package com.somuncu.footballmarkt.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetermineNumbersForPagingResponse {

    private int pageNo;
    private int pageSize;

}
