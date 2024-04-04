package com.itaxi.repository;

import com.itaxi.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(long id);

    List<Product> findAll();

    Product save(Product product);

    boolean deleteById(long id);

    void clear();
}
