package com.nishintgoyal.ecommerce.inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication

public class InventoryServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
}
