package com.somuncu.footballmarkt.service.image;

import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ImageRepository;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.entities.Image;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.response.SaveImageResponse;
import com.somuncu.footballmarkt.response.UpdateImageResponse;
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
    private final ModelMapperService modelMapperService;

    @Override
    public List<SaveImageResponse> saveImage(List<MultipartFile> files , Long playerId) {

        Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to add images"));

        List<SaveImageResponse> saveImageResponses = files.stream().map(file -> {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setPlayer(player);

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
    public UpdateImageResponse updateImage(MultipartFile file, Long imageId , Long playerId) throws SQLException, IOException {

        Image image = this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found to update"));
        Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to set it to image"));

        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        image.setPlayer(player);
        this.imageRepository.save(image);

        return this.modelMapperService.forResponse().map(image , UpdateImageResponse.class);

    }

    @Override
    public void deleteImage(Long imageId) {
        Image image = this.imageRepository.findById(imageId).orElseThrow(()-> new NoImageFoundException("No image found to delete"));
        this.imageRepository.delete(image);
    }


}
