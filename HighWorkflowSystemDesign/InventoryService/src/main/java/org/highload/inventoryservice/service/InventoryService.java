package com.highload.service.external;

public interface InventoryService {
    boolean checkStockAvailability(Long productId, int quantity);
    void updateStock(Long productId, int quantity);
}