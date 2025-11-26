package com.catsshop.cats_shop_backend.service;

import com.catsshop.cats_shop_backend.model.Product;
import com.catsshop.cats_shop_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product create(Product p) {
        return repo.save(p);
    }

    public Product update(Long id, Product p) {
        Product existing = findById(id);
        existing.setNombre(p.getNombre());
        existing.setDescripcion(p.getDescripcion());
        existing.setPrecio(p.getPrecio());
        existing.setStock(p.getStock());
        existing.setImagenUrl(p.getImagenUrl());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
