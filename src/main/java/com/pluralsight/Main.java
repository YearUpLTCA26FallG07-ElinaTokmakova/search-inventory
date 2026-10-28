package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        ArrayList<Product> inventory = getInventory();
        getInventoryFromFile(inventory);

        //BONUS: Sort the products by name before you display them.
        //Hint: Collections.sort() with a Comparator on getName()
        inventory.sort(Comparator.comparing(Product::getName));

        System.out.println("We got the following products: %n");

        for (Product product : inventory) {
            System.out.printf("id: %6d | %-40s | Price in $%10.2f%n",
                    product.getId(), product.getName(), product.getPrice());
        }
    }

    public static ArrayList<Product> getInventory() { //loads product object into inventory

        //create arraylist
        ArrayList<Product> inventory = new ArrayList<Product>();

        //create object
        inventory.add(0, new Product(100, "Kidney", 5000.00));
        inventory.add(1, new Product(110, "Liver", 7500.99));
        inventory.add(2, new Product(99, "Brain", 6200.00));
        inventory.add(3, new Product(200, "Knee cap", 570.00));
        inventory.add(4, new Product(573, "Ear", 2304.00));
        inventory.add(5, new Product(86, "Tail bone", 3495.05));

        return inventory;
    }

    public static ArrayList<Product> getInventoryFromFile(ArrayList<Product>  inventory) {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("inventory.csv"));
            String input;

            //process data from file
            while((input = bufReader.readLine()) != null) {
                String[] parts = input.split(Pattern.quote("|"));

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                inventory.add(new Product(id, name, price));
            }

        } catch (IOException e){
            System.out.println("Error while processing the file.");
            e.printStackTrace();
        }

        return inventory;
    }
}
