package com.example.evaluacion.tienda.controller;


import com.example.evaluacion.tienda.model.Product;
import com.example.evaluacion.tienda.service.ProductService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/categoria")
    public String productsByCategoria(@PathVariable String categoria, Model model) {

        List<Product> products = productService.getProductsByCategoria(categoria);

        model.addAttribute("products", products);
        model.addAttribute("categoria", categoria);

        return "pages/Products";
    }
    @GetMapping
    public String findProducts(@RequestParam(name= "buscarProducto", required = false, defaultValue = "") String name, Model model){
        List<Product> products = productService.findBookByTitle(name);
        model.addAttribute("products", products);
        model.addAttribute("name", name);
        return "pages/ProductsList";
    }

    @GetMapping("/listar")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "pages/Products";
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

    //metodo para generar listado pdf
    @GetMapping("/pdf")
    public void generarPdf(HttpServletResponse response) {

        try {
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=products.pdf");

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            document.add(new Paragraph("LISTADO DE PRODUCTOS"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            table.addCell("ID");
            table.addCell("NOMBRE");
            table.addCell("CODIGO");
            table.addCell("PROVEEDOR");
            table.addCell("UBICACION");
            table.addCell("PRECIO");
            table.addCell("STOCK");

            for (Product product : productService.findAll()) {
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getName());
                table.addCell(product.getCodigo());
                table.addCell(product.getProveedor());
                table.addCell(product.getUbicacion());
                table.addCell(String.valueOf(product.getPrecio()));
                table.addCell(String.valueOf(product.getStock()));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pdf-catalogo")
    public void generarCatalogoPdf(HttpServletResponse response) {

        try {
            // CONFIGURACIÓN DE RESPUESTA
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=catalogo-productos.pdf"
            );

            // DOCUMENTO
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // FUENTES
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font nameFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 10);
            Font priceFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);

            // TÍTULO
            Paragraph title = new Paragraph("CATÁLOGO DE PRODUCTOS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // TABLA → 2 PRODUCTOS POR FILA
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[]{1, 1});

            // RECORRER PRODUCTOS
            for (Product product : productService.findAll()) {

                PdfPCell cell = new PdfPCell();
                cell.setPadding(10);
                cell.setBorder(PdfPCell.BOX);

                // IMAGEN
                try {
                    if (product.getImagen() != null && !product.getImagen().isEmpty()) {
                        Image image = Image.getInstance(product.getImagen());
                        image.scaleToFit(150, 150);
                        image.setAlignment(Image.ALIGN_CENTER);
                        cell.addElement(image);
                    }
                } catch (Exception e) {
                    cell.addElement(new Paragraph("Imagen no disponible", textFont));
                }


                Paragraph name = new Paragraph(product.getName(), nameFont);
                name.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(name);


                Paragraph desc = new Paragraph(product.getDescripcion(), textFont);
                desc.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(desc);


                Paragraph price = new Paragraph(
                        "Precio: $" + product.getPrecio(),
                        priceFont
                );
                price.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(price);

                table.addCell(cell);
            }


            table.completeRow();


            document.add(table);


            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
