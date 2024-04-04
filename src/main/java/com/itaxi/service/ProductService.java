package com.itaxi.service;

import com.itaxi.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProduct(long id);

    ProductDTO createProduct(ProductDTO productDTO);

    boolean deleteProduct(long id);
}
