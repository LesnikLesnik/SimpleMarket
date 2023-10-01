package com.example.buysell.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity //данный класс не просто класс, а является тем что эмулирует таблицу из базы даных
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "city")
    private String city;

    @Column(name = "author")
    private String author;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId; //чтобы не обращаться к списку выше и чтобы получить превьюшную фото сразу при загрузке товара

    private LocalDateTime dateOfCreated;

    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image) {
        image.setProduct(this); //устанавливаем фото на текущий товар
        images.add(image);
    }
    /*
    cascade.All значит и то, что при сохранении товара включающий в свой список фото, сохранятся будет не только товар, но и все
    связанные с ним сущности (фото). Также при удалении. Нам не нужно обращаться к репозиторию чтобы отдельно сохранить фото,
    этим занимаются хибернейт
     */
}
