package com.projoker.smart_bazar.Repository;

import com.projoker.smart_bazar.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long id);
}
