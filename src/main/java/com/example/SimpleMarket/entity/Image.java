package com.example.SimpleMarket.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "contentType")
    private String contentType; //расширение файла
    @Column(name = "isPreviewImage")
    private boolean isPreviewImage;
    @Lob //означает что данное поле в БД будет храниться в типе LONGBLOG
    @Column(name = "bytes", columnDefinition = "longblob")
    @JsonIgnore
    private byte[] bytes;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER) //как повлияет действие с сущностью товара на сущность с фотографией
    @JsonIgnore
    private Product product;
}
