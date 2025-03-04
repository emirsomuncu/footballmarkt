package com.somuncu.footballmarkt.service.image;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.league.NoLeaguesFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.*;
import com.somuncu.footballmarkt.entities.*;
import com.somuncu.footballmarkt.response.SaveImageResponse;
import com.somuncu.footballmarkt.response.UpdateImageResponse;
import com.somuncu.footballmarkt.service.rules.ImageServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final LeagueRepository leagueRepository;
    private final TrophyRepository trophyRepository;
    private final ModelMapperService modelMapperService;
    private final ImageServiceImplRules imageServiceImplRules;

    @Override
    public List<SaveImageResponse> saveImage(List<MultipartFile> files , Long playerId , Long clubId ,  Long trophyId ,Long leagueId ) {

        this.imageServiceImplRules.checkSaveAndUpdateImageParameters(playerId , clubId , trophyId , leagueId );

        List<SaveImageResponse> saveImageResponses = files.stream().map(file -> {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                if(playerId != null) {
                    Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to add images"));
                    image.setPlayer(player);
                }
                else if(clubId != null) {
                    Club club = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("No club found to add images"));
                    image.setClub(club);
                }
                else if(leagueId != null) {
                    League league = this.leagueRepository.findById(leagueId).orElseThrow(()-> new NoLeaguesFoundException("No league found to add image"));
                    image.setLeague(league);
                }
                else if (trophyId != null) {
                    Trophy trophy = this.trophyRepository.findById(trophyId).orElseThrow(()-> new NoTrophyFoundException("No trophy found to add image"));
                    image.setTrophy(trophy);
                }

                String buildDownloadUrl = "/api/v1/images/image/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = this.imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+ savedImage.getId());
                this.imageRepository.save(savedImage);

                SaveImageResponse saveImageResponse = this.modelMapperService.forResponse().map(savedImage , SaveImageResponse.class);
                return saveImageResponse;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }

        }).collect(Collectors.toList());

        return saveImageResponses;
    }

    @Override
    public Image getImageById(Long imageId) {
        return this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found to download"));
    }

    @Override
    public UpdateImageResponse updateImage(MultipartFile file, Long imageId , Long playerId , Long clubId , Long leagueId , Long trophyId) throws SQLException, IOException {

        this.imageServiceImplRules.checkSaveAndUpdateImageParameters(playerId , clubId , leagueId , trophyId);

        Image image = this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found to update"));
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        // refreshing relationships
        image.setPlayer(null);
        image.setClub(null);
        image.setLeague(null);
        image.setTrophy(null);

        if(playerId != null) {
            Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to add images"));
            image.setPlayer(player);
        }
        else if(clubId != null) {
            Club club = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("No club found to add images"));
            image.setClub(club);
        }
        else if(leagueId != null) {
            League league = this.leagueRepository.findById(leagueId).orElseThrow(()-> new NoLeaguesFoundException("No league found to add image"));
            image.setLeague(league);
        }
        else if (trophyId != null) {
            Trophy trophy = this.trophyRepository.findById(trophyId).orElseThrow(()-> new NoTrophyFoundException("No trophy found to add image"));
            image.setTrophy(trophy);
        }

        this.imageRepository.save(image);

        return this.modelMapperService.forResponse().map(image , UpdateImageResponse.class);

    }

    @Override
    public void deleteImage(Long imageId) {
        Image image = this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found to delete"));
        this.imageRepository.delete(image);
    }


}
