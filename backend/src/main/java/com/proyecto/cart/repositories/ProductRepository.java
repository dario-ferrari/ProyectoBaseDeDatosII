package com.proyecto.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.cart.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findByCategoryAndIdNot(String category, String ProductId);
    List<Product> findFirst4ByOrderByPriceAsc();/**Primero los productos de menor precio */
}
