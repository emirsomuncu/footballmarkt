package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.ClubAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.entities.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClubServiceImplRules {

    private final ClubRepository clubRepository;

    public void checkIfListEmpty(List<Club> clubList) {

        if(clubList.isEmpty()) {
            throw new NoClubFoundException("No club found");
        }
    }

    public void checkIfClubExists(Club club) {

        if(clubRepository.existsClubByName(club.getName())) {
            throw new ClubAlreadyExistsException("Club '" +club.getName()+ "' already exists");
        }
    }

}
