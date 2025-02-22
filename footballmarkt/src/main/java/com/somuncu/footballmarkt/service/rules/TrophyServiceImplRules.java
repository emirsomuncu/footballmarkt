package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.dao.TrophyRepository;
import com.somuncu.footballmarkt.entities.Trophy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrophyServiceImplRules {

    private final TrophyRepository trophyRepository;


    public void checkIfTrophyListEmpty(List<Trophy> trophies) {

        if (trophies.isEmpty()) {
            throw new NoTrophyFoundException("No trophy found");
        }
    }

}
