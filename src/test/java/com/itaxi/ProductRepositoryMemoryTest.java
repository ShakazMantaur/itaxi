package com.itaxi;

import com.itaxi.exception.UpdateOperationNotSupportedException;
import com.itaxi.model.Product;
import com.itaxi.repository.ProductRepository;
import com.itaxi.repository.ProductRepositoryMemory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductRepositoryMemoryTest {

    ProductRepository repository = new ProductRepositoryMemory();

    @Test
    void should_return_all_products() {
        // given
        createTestData();

        // when
        List<Product> products = repository.findAll();

        // then
        assertThat(products).hasSize(3);
        assertThat(products).map(Product::getName).contains("name1", "name2", "name3");
    }

    @Test
    void should_return_no_products_when_empty() {
        // given

        // when
        List<Product> products = repository.findAll();

        // then
        assertThat(products).hasSize(0);
    }

    @Test
    void should_return_product_when_exists() {
        // given
        createTestData();

        // when
        Optional<Product> maybeProduct = repository.findById(1L);

        // then
        assertThat(maybeProduct).isNotEmpty();
        assertThat(maybeProduct.get().getName()).isEqualTo("name1");
        assertThat(maybeProduct.get().getPrice()).isEqualTo(new BigDecimal(1));
    }

    @Test
    void should_not_return_product_when_not_exists() {
        // given
        createTestData();

        // when
        Optional<Product> maybeProduct = repository.findById(10L);

        // then
        assertThat(maybeProduct).isEmpty();
    }

    @Test
    void should_not_return_product_when_products_empty() {
        // given

        // when
        Optional<Product> maybeProduct = repository.findById(1L);

        // then
        assertThat(maybeProduct).isEmpty();
    }

    @Test
    void should_delete_product_when_exists() {
        // given
        createTestData();

        // when
        boolean result = repository.deleteById(2L);

        // then
        assertThat(result).isTrue();
        assertThat(repository.findAll()).hasSize(2);
        assertThat(repository.findAll()).map(Product::getName).contains("name1", "name3");
    }

    @Test
    void should_not_delete_anything_when_product_not_exists() {
        // given
        createTestData();

        // when
        boolean result = repository.deleteById(10L);

        // then
        assertThat(result).isFalse();
        assertThat(repository.findAll()).hasSize(3);
        assertThat(repository.findAll()).map(Product::getName).contains("name1", "name2", "name3");
    }

    @Test
    void should_not_delete_anything_when_products_empty() {
        // given

        // when
        boolean result = repository.deleteById(10L);

        // then
        assertThat(result).isFalse();
        assertThat(repository.findAll()).hasSize(0);
    }

    @Test
    void should_save_product_without_id_when_not_exists() {
        // given
        createTestData();

        // when
        Product createdProduct = repository.save(new Product("name10", new BigDecimal(10)));

        // then
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("name10");
        assertThat(createdProduct.getPrice()).isEqualTo(new BigDecimal(10));

        Optional<Product> foundProduct = repository.findById(4L);
        assertThat(foundProduct).isNotEmpty();
        assertThat(foundProduct.get().getName()).isEqualTo(createdProduct.getName());
        assertThat(foundProduct.get().getPrice()).isEqualTo(createdProduct.getPrice());
    }

    @Test
    void should_not_save_product_with_id() {
        // given
        createTestData();
        Product product = new Product("name10", new BigDecimal(10));
        product.setId(10L);

        // when
        assertThrows(UpdateOperationNotSupportedException.class,
                ()-> repository.save(product));
    }

    @Test
    void should_increment_id_when_two_products_saved() {
        // given
        repository.save(new Product("name1", new BigDecimal(1)));
        repository.save(new Product("name2", new BigDecimal(2)));

        // when
        List<Product> products = repository.findAll();

        // then
        assertThat(products).hasSize(2);
        assertThat(products).map(Product::getId).containsOnly(1L, 2L);
    }

    private void createTestData() {
        Product p1 = new Product("name1", new BigDecimal(1));
        Product p2 = new Product("name2", new BigDecimal(2));
        Product p3 = new Product("name3", new BigDecimal(3));
        repository.save(p1);
        repository.save(p2);
        repository.save(p3);
    }

}
