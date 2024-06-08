package com.rkjavahub.inventoryservice;

import com.rkjavahub.inventoryservice.model.Inventory;
import com.rkjavahub.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("Samsung Galaxy S23FE");
            inventory.setQuantity(7);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("Apple Iphone 15");
            inventory1.setQuantity(17);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);

        };

    }

}
