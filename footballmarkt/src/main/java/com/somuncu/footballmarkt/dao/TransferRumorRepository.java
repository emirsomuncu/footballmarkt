package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.TransferRumor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransferRumorRepository extends JpaRepository<TransferRumor , Long> {

    public Page<TransferRumor> findAllByOrderByProbabilityDesc(Pageable pageable);
    public Page<TransferRumor> findAllByPlayerId(Long playerId , Pageable pageable);
    public Page<TransferRumor> findAllByCurrentClubId(Long currentClubId , Pageable pageable);
    public Page<TransferRumor> findAllByNextClubId(Long nextClubId , Pageable pageable);

    @Query("SELECT tr FROM TransferRumor tr WHERE tr.currentClub.id = :clubId OR tr.nextClub.id = :clubId")
    public Page<TransferRumor> findAllTransferRumorsForOneClub(Long clubId , Pageable pageable);
    // you can do with using JPA keyword like "findByCurrentClubIdOrNextClubId(Long clubId1, Long clubId2);" bu it requires two parameter
}
