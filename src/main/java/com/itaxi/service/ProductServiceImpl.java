package com.itaxi.service;

import com.itaxi.dto.ProductDTO;
import com.itaxi.model.Product;
import com.itaxi.repository.ProductRepository;
import com.itaxi.service.mapper.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDTOMapper::modelToDto)
                .toList();
    }

    @Override
    public Optional<ProductDTO> getProduct(long id) {
        return productRepository.findById(id).map(ProductDTOMapper::modelToDto);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productRepository.save(ProductDTOMapper.dtoToModel(productDTO));
        return ProductDTOMapper.modelToDto(product);
    }

    @Override
    public boolean deleteProduct(long id) {
        return productRepository.deleteById(id);
    }
}
