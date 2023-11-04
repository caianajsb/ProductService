package com.ecommerce.product;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Assertions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductServiceApplicationTests{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        repository.deleteAll();
    }

    @Test
    public void createAndReturnSavedProduct() throws Exception{

        // given - precondition or setup
        Product product = Product.builder()
                .name("New product")
                .description("description of product")
                .price(new BigDecimal(10.32))
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/ecommerce/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated());
        
        Assertions.assertTrue(repository.findAll().size() == 1);

    }

    // JUnit test for Get All products REST API
    @Test
    public void createAndReturnAllSavedProduct() throws Exception{
        // given - precondition or setup
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(Product.builder().name("book").description("book description").price(new BigDecimal(14.57)).build());
        listOfProducts.add(Product.builder().name("book 2").description("book description 2").price(new BigDecimal(16.29)).build());
        repository.saveAll(listOfProducts);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/ecommerce/products"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfProducts.size())));

    }
}