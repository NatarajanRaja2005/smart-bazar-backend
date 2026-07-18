package com.projoker.smart_bazar.controller;

import com.projoker.smart_bazar.dto.ImageDto;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Image;
import com.projoker.smart_bazar.response.ApiResponse;
import com.projoker.smart_bazar.service.image.IImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name="Image",
        description = "Api to for operations with images"
)
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
                                                  @RequestParam Long productId){
        //For bugging
        System.out.println("Inside saveImages()");
        try {
            List<ImageDto> imageDtos=imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success!",imageDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!",e.getMessage()));
        }
    }

    //This function returns the image in byte format
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam("file") MultipartFile file){
        try {
            Image image=imageService.getImageById(imageId);
            if(image!=null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success!",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            Image image=imageService.getImageById(imageId);
            if(image!=null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success!",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!",INTERNAL_SERVER_ERROR));
    }
}
