package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDTO;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository repository;
    
    public void createProduct(ProductDTO request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        
        repository.save(product);
        log.info("Product {} is saved.", product.getId());
    }
    
    public List<ProductDTO> getAllProducts() {
        List<Product> products = repository.findAll();
        
        return products.stream().map(this::mapToProductDTO).toList();
    }
    
    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        
        return productDTO;
    }
    
    public ProductDTO getById(String id) {
        Optional<Product> result = repository.findById(id);
        if (!result.isEmpty()) {
            return mapToProductDTO(result.get());
        }
        log.info("Product {} not exists.", id);
        return null;
    }
    
    public void delete(String id) {
        repository.deleteById(id);
        log.info("Product {} is deleted.", id);
    }

    public void update(ProductDTO request) {
         Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        
        repository.save(product);
        log.info("Product {} is updated.", product.getId());
    }
}
