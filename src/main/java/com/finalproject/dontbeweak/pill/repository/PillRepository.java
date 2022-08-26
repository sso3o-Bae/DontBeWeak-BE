package com.finalproject.dontbeweak.pill.repository;

import com.finalproject.dontbeweak.pill.model.Pill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PillRepository extends JpaRepository<Pill, Long> {
    List<Pill> findByUser_Username(String username);
    boolean existsByUser_IdAndProductName(Long userId, String productName);
//    AndProductName
    Pill findByUser_IdAndProductName(Long userId, String productName);
}
