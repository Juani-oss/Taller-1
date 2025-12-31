package com.example.evaluacion.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = "products")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(
            regexp = "^[A-Z ]+$",
            message = "El nombre solo debe contener letras MAYÚSCULAS"
    )
    private String name;
    private String city;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<Product> products;


}
