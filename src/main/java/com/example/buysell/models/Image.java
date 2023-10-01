package com.example.buysell.models;


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
    private byte[] bytes;
/*
Hibernate преобразует в For in key по которому мы сможем получить товар из фотографий
будет таблица images и products в таблице images будет модель фотографий
если эта фотография прикреплена к полю с id-3, то будет присвоена товару с таким же id
 */

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER) //как повлияет действие с сущностью товара на сущность с фотографией
    private Product product;
}
