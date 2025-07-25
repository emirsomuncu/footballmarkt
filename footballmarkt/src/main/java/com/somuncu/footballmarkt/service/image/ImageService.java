package com.somuncu.footballmarkt.service.image;

import com.somuncu.footballmarkt.entities.Image;
import com.somuncu.footballmarkt.response.SaveImageResponse;
import com.somuncu.footballmarkt.response.UpdateImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {

    public List<SaveImageResponse> saveImage(List<MultipartFile> files , Long playerId , Long clubId , Long leagueId , Long trophyId , Long newsId);
    public List<SaveImageResponse> saveImageForNewThings(List<MultipartFile> files);
    public Image getImageById(Long imageId);
    public UpdateImageResponse updateImage(MultipartFile file , Long imageId , Long playerId , Long clubId , Long leagueId , Long trophyId , Long newsId) throws IOException, SQLException;
    public void deleteImage(Long imageId);

}
