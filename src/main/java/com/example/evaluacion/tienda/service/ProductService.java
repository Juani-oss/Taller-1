package com.example.evaluacion.tienda.service;

import com.example.evaluacion.tienda.model.Product;
import com.example.evaluacion.tienda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getProductsByCategoria(String categoria) {
        return productRepository.findByCategoria(categoria);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findBookById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findBookByTitle(String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findAll();
        } else {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
       Product oldProduct = findBookById(id).orElseThrow(() -> new RuntimeException("Producto no existente"));

        oldProduct.setName(product.getName());
        oldProduct.setCodigo(product.getCodigo());
        oldProduct.setCategoria(product.getCategoria());
        oldProduct.setProveedor(product.getProveedor());
        oldProduct.setUbicacion(product.getUbicacion());
        oldProduct.setPrecio(product.getPrecio());
        oldProduct.setStock(product.getStock());

        return productRepository.save(oldProduct);
    }

    public void deleteBookById(Long id) {
        Product product = findBookById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        productRepository.deleteById(id);
    }
}
