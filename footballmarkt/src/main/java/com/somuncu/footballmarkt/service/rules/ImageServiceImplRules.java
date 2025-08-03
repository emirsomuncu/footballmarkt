package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.images.WrongSaveImageRequestException;
import com.somuncu.footballmarkt.dao.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ImageServiceImplRules {

    private final ImageRepository repository;

    public void checkSaveAndUpdateImageParameters(Long playerId , Long clubId , Long leagueId , Long trophyId , Long newsId) {

        ArrayList<Long> idList = new ArrayList<>();
        idList.add(playerId);
        idList.add(clubId);
        idList.add(leagueId);
        idList.add(trophyId);
        idList.add(newsId);

        int counter = 0;
        for(Long id : idList) {
            if(id != null ) {
                counter++;
            }
        }

        if(counter == 0) {
            throw new WrongSaveImageRequestException("You must choose whether you want to upload this image for a player, a league, a club  , a news or a cup !");
        } else if ( counter >=2 ) {
            throw new WrongSaveImageRequestException("You can only choose one of them player , league , club , news or cup !");
        }

    }

}
