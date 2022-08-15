package com.finalproject.dontbeweak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatLevelImageRepository extends JpaRepository<CatLevelImage, Long>{
    Optional<CatLevelImage> findByLevel(int level);
}
