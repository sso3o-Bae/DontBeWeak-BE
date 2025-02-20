package com.finalproject.dontbeweak.repository;

import com.finalproject.dontbeweak.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findAllById(long id);

    Optional<User> findUserByUsername(String friendname);
}