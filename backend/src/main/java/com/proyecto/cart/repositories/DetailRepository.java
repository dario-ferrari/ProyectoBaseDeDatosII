package com.proyecto.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.cart.entities.Detail;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, String> {
    List<Detail> findBySale_Id(String saleId);
}
