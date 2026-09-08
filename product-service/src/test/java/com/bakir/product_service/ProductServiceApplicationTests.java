package com.bakir.product_service;

import com.bakir.product_service.dto.ProductRequest;
import com.bakir.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
         dynamicPropertyRegistry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

	@Test
	void shouldCreateProduct() throws Exception {

        ProductRequest productRequest=getProductRequest();
        String  productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
                .andExpect(status().isCreated()
        );

//        Assertions.assertEquals(1, productRepository.findAll().size());

	}
    private ProductRequest getProductRequest(){

        return ProductRequest.builder()
                .name("Iphone 14")
                .description("Iphone 14")
                .price(BigDecimal.valueOf(1300))
                .build();

    }

}
