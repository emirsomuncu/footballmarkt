package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Image;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.SaveImageResponse;
import com.somuncu.footballmarkt.response.UpdateImageResponse;
import com.somuncu.footballmarkt.service.image.ImageService;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImagesController {

    private final ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files , @RequestParam Long playerId) {
        List<SaveImageResponse> saveImageResponses = this.imageService.saveImage(files , playerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Images successfully saved" , saveImageResponses));
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId ) throws SQLException {

        Image image = this.imageService.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1L, (int) image.getImage().length()));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file ,@PathVariable Long imageId ,@RequestParam Long playerId) {
        try{
            UpdateImageResponse updateImageResponse = this.imageService.updateImage(file,imageId,playerId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Updated successfully" , updateImageResponse));
        }catch(Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        this.imageService.deleteImage(imageId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully deleted" , null));
    }


}
