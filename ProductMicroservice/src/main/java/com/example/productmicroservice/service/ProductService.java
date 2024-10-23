package com.example.productmicroservice.service;

import com.example.productmicroservice.dto.CreateProductDto;

public interface ProductService {

    String createProduct(CreateProductDto createProductDto);
}
