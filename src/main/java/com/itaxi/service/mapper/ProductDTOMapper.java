package com.itaxi.service.mapper;

import com.itaxi.dto.ProductDTO;
import com.itaxi.model.Product;

public class ProductDTOMapper {

    public static ProductDTO modelToDto(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }
    public static Product dtoToModel(ProductDTO productDTO) {
        if (productDTO.id() == null) {
            return new Product(productDTO.name(), productDTO.price());
        } else {
            Product product = new Product(productDTO.name(), productDTO.price());
            product.setId(productDTO.id());
            return product;
        }
    }
}
