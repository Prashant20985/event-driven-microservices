package com.practice.ProductService.command.api.events;

import com.practice.ProductService.command.api.data.IProductRepository;
import com.practice.ProductService.command.api.data.ProductEntity;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {

    private IProductRepository productRepository;

    public ProductEventsHandler(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreateEvent event) throws Exception {
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }
}

