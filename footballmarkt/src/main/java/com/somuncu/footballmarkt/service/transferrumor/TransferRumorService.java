package com.somuncu.footballmarkt.service.transferrumor;


import com.somuncu.footballmarkt.entities.TransferRumor;
import com.somuncu.footballmarkt.request.transferrumor.CreateTransferRumorRequest;
import com.somuncu.footballmarkt.request.transferrumor.UpdateTransferRumorRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.transferrumor.TransferRumorDto;

import java.util.List;

public interface TransferRumorService {

    public PageResponse<TransferRumorDto> getAllTransferRumors(int pagingOffset);
    public PageResponse<TransferRumorDto> getAllTransferRumorsByDescendingProbability(int pagingOffset);
    public TransferRumorDto getTransferRumorById(Long id);
    public PageResponse<TransferRumorDto> getTransferRumorsByPlayerId(Long playerId , int pagingOffset);
    public PageResponse<TransferRumorDto> getAllTransferRumorsOfClub(Long clubId , int pagingOffset);
    public PageResponse<TransferRumorDto> getAllTransferRumorsAboutPlayersLeavingTheClub(Long currentClubId , int pagingOffset);
    public PageResponse<TransferRumorDto> getAllTransferRumorsAboutFuturePlayersAtTheClub(Long nextClubId , int pagingOffset);
    public void addTransferRumor(CreateTransferRumorRequest createTransferRumorRequest);
    public void updateTransferRumor(UpdateTransferRumorRequest updateTransferRumorRequest);
    public void deleteTransferRumorById(Long id);
    public TransferRumorDto convertTransferRumorToTransferRumorDto(TransferRumor transferRumor);
    public List<TransferRumorDto> convertTransferRumorListToTransferRumorDtoList(List<TransferRumor> transferRumors);
    public DetermineNumbersForPagingResponse determineNumbersForPagingResponse(int pagingOffset);
}
