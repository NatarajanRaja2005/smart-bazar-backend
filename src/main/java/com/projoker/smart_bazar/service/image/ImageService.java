package com.projoker.smart_bazar.service.image;

import com.projoker.smart_bazar.Repository.ImageRepository;
import com.projoker.smart_bazar.dto.ImageDto;
import com.projoker.smart_bazar.exception.ResourceNotFoundException;
import com.projoker.smart_bazar.model.Image;
import com.projoker.smart_bazar.model.Product;
import com.projoker.smart_bazar.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements  IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found: "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                ()->{
            throw new ResourceNotFoundException("No image found: "+id);
        });
    }

    //A single product can hold of multiple images so list of MultiPartFile
    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product=productService.getProductById(productId);

        //A single product have multiple images so, here created list of ImageDtos
        List<ImageDto> savedImageDto=new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String BuildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl=BuildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage=imageRepository.save(image);

                savedImage.setDownloadUrl(BuildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto=new ImageDto();

                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch(IOException | SQLException e){
            throw  new RuntimeException(e.getMessage());
        }
    }
}
