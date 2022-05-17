package com.practice.ProductService.query.api.projections;

import com.practice.ProductService.command.api.data.IProductRepository;
import com.practice.ProductService.command.api.data.ProductEntity;
import com.practice.ProductService.command.api.model.ProductRestModel;
import com.practice.ProductService.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjections {
    private IProductRepository productRepository;

    public ProductProjections(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery){
        List<ProductEntity> products = productRepository.findAll();

        List<ProductRestModel> productRestModels = products.stream().map(productEntity ->
                ProductRestModel.builder()
                        .name(productEntity.getName())
                        .quantity(productEntity.getQuantity())
                        .price(productEntity.getPrice())
                        .build()).collect(Collectors.toList());

        return productRestModels;
    }
}
