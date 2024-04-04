package com.itaxi.repository;

import com.itaxi.exception.UpdateOperationNotSupportedException;
import com.itaxi.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepositoryMemory implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return products.values().stream().toList();
    }

    @Override
    public Product save(Product product) {
        // iTaxi comment -> updating model is not defined in the task, hence ignoring it
        if (product.getId() == null) {
            product.setId(idCounter.incrementAndGet());
            products.put(product.getId(), product);
            return products.get(product.getId());
        } else {
            throw new UpdateOperationNotSupportedException();
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (products.containsKey(id)) {
            products.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        products.clear();
        idCounter.set(0);
    }
}
