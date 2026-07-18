package com.projoker.smart_bazar.service.image;

import com.projoker.smart_bazar.dto.ImageDto;
import com.projoker.smart_bazar.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
