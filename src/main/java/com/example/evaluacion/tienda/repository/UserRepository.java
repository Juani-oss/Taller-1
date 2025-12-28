package com.example.evaluacion.tienda.repository;

import com.example.evaluacion.tienda.model.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String  username);
    List<User> findByNameContainingIgnoreCase(String name);
}
