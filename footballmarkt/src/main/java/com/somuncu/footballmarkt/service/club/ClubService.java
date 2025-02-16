package com.somuncu.footballmarkt.service.club;

import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.request.club.CreateClubRequest;
import com.somuncu.footballmarkt.request.club.UpdateClubRequest;
import com.somuncu.footballmarkt.request.dtos.club.ClubDto;

import java.util.List;

public interface ClubService {

    public List<Club> getAllClubs();
    public List<Club> getAllClubsByAccordingToLeagueName(String leagueName);
    public Club getClubByName(String clubName);
    public void createClub(CreateClubRequest createClubRequest);
    public void updateClub(UpdateClubRequest updateClubRequest);
    public void deleteClub(Long clubId);
    public ClubDto convertClubToClubDto(Club club);
    public List<ClubDto> convertClubListToClubDtoList(List<Club> clubList);

}
