package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.TrophyAlreadyExistsException;
import com.somuncu.footballmarkt.dao.TrophyRepository;
import com.somuncu.footballmarkt.entities.Trophy;
import com.somuncu.footballmarkt.request.trophy.CreateTrophyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrophyServiceImplRules {

    private final TrophyRepository trophyRepository;


    public void checkIfTrophyExists(CreateTrophyRequest createTrophyRequest) {
        if(this.trophyRepository.existsBySeasonAndName(createTrophyRequest.getSeason(), createTrophyRequest.getName())) {
            throw new TrophyAlreadyExistsException("Trophy already exists");
        }
    }

    public void checkIfTrophyListEmpty(List<Trophy> trophies) {
        if (trophies.isEmpty()) {
            throw new NoTrophyFoundException("No trophy found");
        }
    }

}
