package com.proyecto.cart.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.cart.entities.Sale;

import java.util.List;

/**Para hacer operaciones bàsicas CRUD sirviendo como nexo entre la db y el backend */

public interface SaleRepository extends MongoRepository<Sale,String> {
    @Query("{userName:'?0'}")
    List<Sale> findByClient_UserName(String userName);
}
