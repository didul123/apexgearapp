package com.didul.apexgearapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name;
    private String brand;
    private String specifications;
    private double price;
    private int stock;
    private String image;

    // Constructor
    public Product(String name, String brand, String specifications, double price, int stock, String image) {
        this.name = name;
        this.brand = brand;
        this.specifications = specifications;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for brand
    public String getBrand() {
        return brand;
    }

    // Getter for specifications
    public String getSpecifications() {
        return specifications;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Getter for stock
    public int getStock() {
        return stock;
    }

    // Getter for image
    public String getImage() {
        return image;
    }

    // Method to parse JSON and return a list of Product objects
    public static List<Product> fromJson(String jsonString) {
        List<Product> productList = new ArrayList<>();
        try {
            // Parse the JSON string into a JSONArray
            JSONArray jsonArray = new JSONArray(jsonString);

            // Loop through the array and create Product objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject productJson = jsonArray.getJSONObject(i);

                // Create a Product object from the JSON data
                Product product = new Product(
                        productJson.getString("name"),
                        productJson.getString("brand"),
                        productJson.getString("specifications"),
                        productJson.getDouble("price"),
                        productJson.getInt("stock"),
                        productJson.getString("image")
                );

                // Add the Product object to the list
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}
