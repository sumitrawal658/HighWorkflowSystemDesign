package com.highload.service.external.impl;

import com.highload.service.external.InventoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final RestTemplate restTemplate;
    private final String inventoryServiceUrl;

    public InventoryServiceImpl(RestTemplate restTemplate, @Value("${inventory.service.url}") String inventoryServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventoryServiceUrl = inventoryServiceUrl;
    }



    private final RestTemplate restTemplate;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public InventoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Timed(value = "inventory.check_stock", description = "Time taken to check stock availability")
    public boolean checkStockAvailability(Long productId, int quantity) {
        String url = inventoryServiceUrl + "/api/inventory/check?productId=" + productId + "&quantity=" + quantity;
        return restTemplate.getForObject(url, Boolean.class);
    }

    @Override
    @Timed(value = "inventory.update_stock", description = "Time taken to update stock")
    public void updateStock(Long productId, int quantity) {
        String url = inventoryServiceUrl + "/api/inventory/update?productId=" + productId + "&quantity=" + quantity;
        restTemplate.postForObject(url, null, Void.class);
    }

    @Override
    public boolean checkStockAvailability(Long productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/inventory/check?productId=" + productId + "&quantity=" + quantity;
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check stock availability: " + e.getMessage());
        }
    }

    @Override
    public void updateStock(Long productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/inventory/update?productId=" + productId + "&quantity=" + quantity;
            restTemplate.postForObject(url, null, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }


    public InventoryServiceImpl(RestTemplate restTemplate, @Value("${inventory.service.url}") String inventoryServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventoryServiceUrl = inventoryServiceUrl;
    }

    @Override
    @Timed(value = "inventory.check_stock", description = "Time taken to check stock availability")
    public boolean checkStockAvailability(Long productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/inventory/check?productId=" + productId + "&quantity=" + quantity;
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to check stock availability: " + e.getMessage());
        }
    }

    @Override
    @Timed(value = "inventory.update_stock", description = "Time taken to update stock")
    public void updateStock(Long productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/inventory/update?productId=" + productId + "&quantity=" + quantity;
            restTemplate.postForObject(url, null, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }
}
}