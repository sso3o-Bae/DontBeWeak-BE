package com.finalproject.dontbeweak.repository;

import com.finalproject.dontbeweak.model.Pill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PillRepository extends JpaRepository<Pill, Long> {
    List<Pill> findByUser_Username(String username);
    Pill findByUserAndProductName(String username, String productName);

    Pill findByProductName(String productName);

    Pill findByUser_IdAndProductName(Long userId, String productName);
}
