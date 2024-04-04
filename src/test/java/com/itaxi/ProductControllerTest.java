package com.itaxi;

import com.itaxi.model.Product;
import com.itaxi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clear() {
        repository.clear();
    }

    @Test
    void should_return_ok_and_product_when_product_exists() throws Exception {
        // given
        repository.save(new Product("name", new BigDecimal(1)));

        // when
        mockMvc.perform(get("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1));
    }

    @Test
    void should_return_not_found_when_product_not_exists() throws Exception {
        mockMvc.perform(get("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_product() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"name\", \"price\":1}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/products/1")));
    }

    @Test
    void should_delete_product() throws Exception {
        // given
        repository.save(new Product("name", new BigDecimal(1)));

        // when
        mockMvc.perform(delete("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_bad_request_when_product_not_exists() throws Exception {
        mockMvc.perform(delete("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
