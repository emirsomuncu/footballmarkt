package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.request.transferrumor.CreateTransferRumorRequest;
import com.somuncu.footballmarkt.request.transferrumor.UpdateTransferRumorRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.transferrumor.TransferRumorDto;
import com.somuncu.footballmarkt.service.transferrumor.TransferRumorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/transfer-rumors")
@RequiredArgsConstructor
public class TransferRumorsController {

    private final TransferRumorService transferRumorService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getAllTransferRumors(@RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getAllTransferRumors(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/descending-probability")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getAllTransferRumorsByDescendingProbability(@RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getAllTransferRumorsByDescendingProbability(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/rumor/{id}")
    public ResponseEntity<ApiResponse<TransferRumorDto>> getTransferRumorById(@PathVariable Long id) {
        TransferRumorDto transferRumorDto = this.transferRumorService.getTransferRumorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , transferRumorDto));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getTransferRumorsByPlayerId(@PathVariable Long playerId , @RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getTransferRumorsByPlayerId(playerId,pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getAllTransferRumorsOfClub(@PathVariable Long clubId ,@RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getAllTransferRumorsOfClub(clubId,pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/club/{currentClubId}/leaving-rumors")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getAllTransferRumorsAboutPlayersLeavingTheClub(@PathVariable Long currentClubId , @RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getAllTransferRumorsAboutPlayersLeavingTheClub(currentClubId , pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @GetMapping("/club/{nextClubId}/arrival-rumors")
    public ResponseEntity<ApiResponse<PageResponse<TransferRumorDto>>> getAllTransferRumorsAboutFuturePlayersAtTheClub(@PathVariable Long nextClubId , @RequestParam(defaultValue = "0") int pagingOffset){
        PageResponse<TransferRumorDto> pageResponse = this.transferRumorService.getAllTransferRumorsAboutFuturePlayersAtTheClub(nextClubId ,pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , pageResponse));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addTransferRumor(@RequestBody CreateTransferRumorRequest createTransferRumorRequest) {
        this.transferRumorService.addTransferRumor(createTransferRumorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully" , null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateTransferRumor(@RequestBody UpdateTransferRumorRequest updateTransferRumorRequest) {
        this.transferRumorService.updateTransferRumor(updateTransferRumorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteTransferRumorById(@RequestParam Long id) {
        this.transferRumorService.deleteTransferRumorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , null));
    }

}

