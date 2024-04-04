package com.itaxi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(Long id, @NotBlank String name, @NotNull BigDecimal price) {}
