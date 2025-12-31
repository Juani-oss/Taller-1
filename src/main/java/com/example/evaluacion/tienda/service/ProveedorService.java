package com.example.evaluacion.tienda.service;
import com.example.evaluacion.tienda.model.Proveedor;
import com.example.evaluacion.tienda.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;


    /*public List<Proveedor> getProductsByCategoria(String categoria) {
        return productRepository.findByCategoria(categoria);
    }*/

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    //buscarpor id
    public Optional<Proveedor> findProveedorById(Long id) {
        return proveedorRepository.findById(id);
    }

   /* public List<Product> findBookByTitle(String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findAll();
        } else {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
    }*/

    //guardaar proveeedor
    public Proveedor saveProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(Long id, Proveedor proveedor) {
        Proveedor oldProveedor = findProveedorById(id).orElseThrow(() -> new RuntimeException("Proveedor no existente"));

        oldProveedor.setName(proveedor.getName());
        oldProveedor.setCity(proveedor.getCity());
        return proveedorRepository.save(oldProveedor);
    }

    public void deleteProveedorById(Long id) {
        Proveedor proveedor = findProveedorById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
        proveedorRepository.deleteById(id);
    }

    //metodo para obtener el autor con sus libros
    @Transactional
    public Proveedor obtenerProveedorConProveedorr(Long id){
        Proveedor proveedor = findProveedorById(id) .orElseThrow();
        return proveedor;

    }
}
