package com.somuncu.footballmarkt.service.transferrumor;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.transferrumor.NoTransferRumorFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.dao.TransferRumorRepository;
import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.TransferRumor;
import com.somuncu.footballmarkt.request.transferrumor.CreateTransferRumorRequest;
import com.somuncu.footballmarkt.request.transferrumor.UpdateTransferRumorRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.transferrumor.TransferRumorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferRumorServiceImpl implements TransferRumorService{

    private final TransferRumorRepository transferRumorRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public PageResponse<TransferRumorDto> getAllTransferRumors(int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAll(pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    public PageResponse<TransferRumorDto> getAllTransferRumorsByDescendingProbability(int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAllByOrderByProbabilityDesc(pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    public TransferRumorDto getTransferRumorById(Long id) {

        TransferRumor transferRumor = this.transferRumorRepository.findById(id).orElseThrow(()-> new NoTransferRumorFoundException("No transfer rumor found"));
        return this.convertTransferRumorToTransferRumorDto(transferRumor);
    }

    @Override
    public PageResponse<TransferRumorDto> getTransferRumorsByPlayerId(Long playerId , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAllByPlayerId(playerId , pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    public PageResponse<TransferRumorDto> getAllTransferRumorsOfClub(Long clubId , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAllTransferRumorsForOneClub(clubId ,pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    public PageResponse<TransferRumorDto> getAllTransferRumorsAboutPlayersLeavingTheClub(Long currentClubId , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAllByCurrentClubId(currentClubId , pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    public PageResponse<TransferRumorDto> getAllTransferRumorsAboutFuturePlayersAtTheClub(Long nextClubId , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = this.determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo , pageSize);
        Page<TransferRumor> transferRumors = this.transferRumorRepository.findAllByNextClubId(nextClubId , pageable);

        List<TransferRumor> transferRumorList = transferRumors.getContent();
        List<TransferRumorDto> transferRumorDtos = this.convertTransferRumorListToTransferRumorDtoList(transferRumorList);

        PageResponse<TransferRumorDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(transferRumorDtos);
        pageResponse.setPageNo(transferRumors.getNumber());
        pageResponse.setPageSize(transferRumors.getSize());
        pageResponse.setTotalPages(transferRumors.getTotalPages());
        pageResponse.setTotalElements(transferRumors.getTotalElements());
        pageResponse.setLast(transferRumors.isLast());

        return pageResponse;
    }

    @Override
    @Transactional
    public void addTransferRumor(CreateTransferRumorRequest createTransferRumorRequest) {

        TransferRumor transferRumor = new TransferRumor();

        transferRumor.setProbability(createTransferRumorRequest.getProbability());

        Player player = this.playerRepository.findById(createTransferRumorRequest.getPlayerId()).orElseThrow(()-> new NoPlayerFoundException("No player found to add transfer rumor"));
        transferRumor.setPlayer(player);

        Club currentClub = this.clubRepository.findById(createTransferRumorRequest.getCurrentClubId()).orElseThrow(()-> new NoClubFoundException("No club found"));
        transferRumor.setCurrentClub(currentClub);

        Club nextClub = this.clubRepository.findById(createTransferRumorRequest.getNextClubId()).orElseThrow(()-> new NoClubFoundException("No club found"));
        transferRumor.setNextClub(nextClub);

        this.transferRumorRepository.save(transferRumor);

    }


    @Override
    @Transactional
    public void updateTransferRumor(UpdateTransferRumorRequest updateTransferRumorRequest) {

        Long transferRumorId = updateTransferRumorRequest.getId();
        TransferRumor transferRumorToUpdate = this.transferRumorRepository.findById(transferRumorId).orElseThrow(()-> new NoTransferRumorFoundException("No transfer found rumor to delete"));

        transferRumorToUpdate.setProbability(updateTransferRumorRequest.getProbability());

        Player player = this.playerRepository.findById(updateTransferRumorRequest.getPlayerId()).orElseThrow(()-> new NoPlayerFoundException("No player found to add transfer rumor"));
        transferRumorToUpdate.setPlayer(player);

        Club currentClub = this.clubRepository.findById(updateTransferRumorRequest.getCurrentClubId()).orElseThrow(()-> new NoClubFoundException("No club found"));
        transferRumorToUpdate.setCurrentClub(currentClub);

        Club nextClub = this.clubRepository.findById(updateTransferRumorRequest.getNextClubId()).orElseThrow(()-> new NoClubFoundException("No club found"));
        transferRumorToUpdate.setNextClub(nextClub);

        this.transferRumorRepository.save(transferRumorToUpdate);

    }

    @Override
    public void deleteTransferRumorById(Long id) {

        this.transferRumorRepository.findById(id).orElseThrow(()-> new NoTransferRumorFoundException("No transfer found rumor to delete"));
        this.transferRumorRepository.deleteById(id);
    }

    @Override
    public TransferRumorDto convertTransferRumorToTransferRumorDto(TransferRumor transferRumor) {
        TransferRumorDto transferRumorDto = this.modelMapperService.forResponse().map(transferRumor , TransferRumorDto.class);
        return transferRumorDto;
    }

    @Override
    public List<TransferRumorDto> convertTransferRumorListToTransferRumorDtoList(List<TransferRumor> transferRumors) {

        List<TransferRumorDto> transferRumorDtos = transferRumors.stream().map(transferRumor -> this.convertTransferRumorToTransferRumorDto(transferRumor)).collect(Collectors.toList());
        return transferRumorDtos;
    }

    @Override
    public DetermineNumbersForPagingResponse determineNumbersForPagingResponse(int pagingOffset) {

        int pageSize = 2 ;
        int pageNo = pagingOffset/pageSize;

        return new DetermineNumbersForPagingResponse(pageNo,pageSize);
    }
}
