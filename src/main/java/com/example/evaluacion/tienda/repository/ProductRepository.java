package com.example.evaluacion.tienda.repository;

import com.example.evaluacion.tienda.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>  {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoria(String categoria);
}
