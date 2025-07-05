package com.somuncu.footballmarkt.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetermineNumbersForPagingResponse {

    private int pageNo;
    private int pageSize;

    public DetermineNumbersForPagingResponse(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
