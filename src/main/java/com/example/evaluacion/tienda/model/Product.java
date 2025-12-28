package com.example.evaluacion.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(
            regexp = "^[A-Z ]+$",
            message = "El nombre solo debe contener letras MAYÚSCULAS"
    )
    private String name;

    @NotBlank(message = "El código no puede estar vacío")
    @Pattern(
            regexp = "^PI-\\d{5}$",
            message = "El código debe iniciar con PI- y tener 5 números (Ej: PI-12345)"
    )
    private String codigo;

    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoria;

    @NotBlank(message = "El proveedor no puede estar vacío")
    @Pattern(
            regexp = "^PR-[A-Z ]+$",
            message = "El proveedor debe iniciar con PR- y estar en MAYÚSCULAS"
    )
    private String proveedor;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Pattern(
            regexp = "^P\\d+-E\\d+$",
            message = "Formato inválido. Ejemplo válido: P3-E12"
    )
    private String ubicacion;

    @NotBlank(message = "El precio no puede estar vacío")
    private String precio;

    @NotBlank(message = "El stock no puede estar vacío")
    private String stock;

    private String imagen;

    @NotBlank(message = "La ubicación no puede estar vacía")
    private String descripcion;
}


