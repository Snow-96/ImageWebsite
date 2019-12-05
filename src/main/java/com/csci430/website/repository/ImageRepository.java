package com.csci430.website.repository;

import com.csci430.website.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByUserEmail(String userEmail);

    Image findByUserEmailAndUrl(String userEmail, String url);
}
