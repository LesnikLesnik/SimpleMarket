package com.example.SimpleMarket.repository;

import com.example.SimpleMarket.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
