package com.example.evaluacion.tienda.controller;

import com.example.evaluacion.tienda.model.Product;
import com.example.evaluacion.tienda.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String findProducts(@RequestParam(name= "buscarProducto", required = false, defaultValue = "") String name, Model model){
        List<Product> products = productService.findBookByTitle(name);
        model.addAttribute("products", products);
        model.addAttribute("name", name);
        return "pages/ProductsList";
    }

    // Save
    @GetMapping("/registerProduct")
    public String registerProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "pages/formProduct";
    }

    //
    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {

        Product product = productService.findBookById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        model.addAttribute("product", product);
        return "pages/formProduct";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteBookById(id);
        return "redirect:/products";
    }
}
