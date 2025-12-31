package com.example.evaluacion.tienda.controller;

import com.example.evaluacion.tienda.model.Product;
import com.example.evaluacion.tienda.model.Proveedor;
import com.example.evaluacion.tienda.service.ProductService;
import com.example.evaluacion.tienda.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductService productService;


   /* @GetMapping("/categoria")
    public String productsByCategoria(@PathVariable String categoria, Model model) {

        List<Product> products = productService.getProductsByCategoria(categoria);

        model.addAttribute("products", products);
        model.addAttribute("categoria", categoria);

        return "pages/Products";
    }*/


  /*  @GetMapping
    public String findProveedor(@RequestParam(name= "buscarProveedor", required = false, defaultValue = "") String name, Model model){
        List<Proveedor> proveedores = proveedorService.findBookByTitle(name);
        model.addAttribute("products", products);
        model.addAttribute("name", name);
        return "pages/ProductsList";
    }*/

    @GetMapping("/listar")
    public String listProveedores(Model model) {
        List<Proveedor> proveedores = proveedorService.findAll();
        model.addAttribute("proveedores", proveedores);
        return "pages/proveedoresList";
    }

    // Save
    @GetMapping("/registerProveedores")
    public String registerProveedores(Model model) {
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        return "pages/formProveedor";
    }

    @PostMapping("/saveProveedor")
    public String saveProveedor(@Valid @ModelAttribute Proveedor proveedor) {
        proveedorService.saveProveedor(proveedor);
        return "redirect:/proveedores/listar";
    }

    @GetMapping("/update/{id}")
    public String updateProveedor(@PathVariable Long id, Model model) {

        Proveedor proveedor = proveedorService.findProveedorById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        model.addAttribute("proveedor", proveedor);
        return "pages/formProveedor";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteProveedorById(id);
        return "redirect:/proveedores/listar";
    }

    @GetMapping("/proveedor/{id}")
    public String  obtenerProveedoresPorAutor(@PathVariable Long id, Model model){
        Optional<Product>  product  = productService.findBookById(id);
        Proveedor proveedor = proveedorService.findProveedorById(id)
                .orElseThrow(()-> new RuntimeException("Proveedor no existe"));
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("products", product);
        return "pages/listProductProveedor";

    }
}
