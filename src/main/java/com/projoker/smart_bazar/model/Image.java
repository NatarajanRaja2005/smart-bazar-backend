package com.projoker.smart_bazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    //Here is your Image
    @Lob
    private Blob image;
    //URL for image
    private String downloadUrl;

    //Relationship of n - 1
    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonIgnore
    private Product product;
}
