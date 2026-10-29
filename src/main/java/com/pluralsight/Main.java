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

        ArrayList<Product> inventory = getInventory(); //load elements in a list manually
        getInventoryFromFile(inventory); //load more elements in a list from file
        displayMenu(inventory);

    }

    public static void displayMenu(ArrayList<Product> inventory) {
        int command;

        do {
            System.out.printf("%n+------------------------------------------------+" +
                    "%n|            What do you want to do?             |" +
                    "%n+------------------------------------------------+" +
                    "%n|  [1] list all products                         |" +
                    "%n|  [2] lookup a product by its id                |" +
                    "%n|  [3] find all products within a price range    |" +
                    "%n|  [4] add a new product                         |" +
                    "%n|  [5] quit the application                      |" +
                    "%n+------------------------------------------------+");

            command = Console.promptForInt("\nEnter command (1-5): ");

            switch (command) {
                case 1 -> listAllProducts(inventory);
                case 2 -> searchById(inventory);
                case 3 -> searchByPriceRange(inventory);
                case 4 -> addNewProduct(inventory);
                case 5 -> {}
                default -> System.out.println("\nInvalid command.");
            }
        } while (command != 5);

        System.out.println("Goodbye!");
    }

    private static void listAllProducts(ArrayList<Product> inventory) {
        //BONUS: Sort the products by name before you display them.
        //Hint: Collections.sort() with a Comparator on getName()
        inventory.sort(Comparator.comparing(Product::getName));

        System.out.printf("%nAll products:%n%n" +
                "%6s | %-40s | Price in $%n%n", "id", "Product name", "Price in $");

        for (Product product : inventory) {
            System.out.printf("%6d | %-40s | %10.2f%n",
                    product.getId(), product.getName(), product.getPrice());
        }
    }
    private static void searchById(ArrayList<Product> inventory) {
        int searchId = Console.promptForInt("Enter product id: ");

        for (Product product : inventory) {
            if (searchId == product.getId()) {
                System.out.printf("%nProduct found:%n%n" +
                        "%6s | %-40s | Price in $%n%n", "id", "Product name", "Price in $");
                System.out.printf("%6d | %-40s | %10.2f%n",
                        product.getId(), product.getName(), product.getPrice());
                return;
            }
        }
        // if not found
        System.out.println("\nProduct id is not found.");
    }

    private static void searchByPriceRange(ArrayList<Product> inventory) {
        int minRange = Console.promptForInt("Enter minimum of your price range: ");
        int maxRange = Console.promptForInt("Enter maximum of your price range: ");

        System.out.printf("%nSearch result:%n");

        boolean found = false; //no matches found yet

        for (Product product : inventory) {
            if (product.getPrice() >= minRange && product.getPrice() <= maxRange) {

                //only when match is found, print header for results
                //this conditional prints header only once cause found
                // changes to true for next loop runs
                if (found == false) {
                    System.out.printf("%6s | %-40s | Price in $%n", "id", "Product name", "Price in $");
                    found = true; //change to true if at least one match is found
                }

                //now print match
                System.out.printf("%n%6d | %-40s | %10.2f",
                        product.getId(), product.getName(), product.getPrice());
            }
        }
        if (found == false) {
            System.out.println("\nNo products found in this price range."); // if not found
        }
    }

    private static void addNewProduct(ArrayList<Product> inventory) {
        try {
            int id = Console.promptForInt("Enter new product id: ");
            String name = Console.promptForString("Enter new product name: ");
            double price = Console.promptForDouble("Enter new product price: ");

            inventory.add(new Product(id, name, price));
            System.out.println("Product successfully added to inventory.");

        } catch (Exception e) {
            System.out.println("Error happened while adding a new product.");
            e.printStackTrace();
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

    public static ArrayList<Product> getInventoryFromFile(ArrayList<Product> inventory) {
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("inventory.csv"));
            String input;

            //process data from file
            while ((input = bufReader.readLine()) != null) {
                String[] parts = input.split(Pattern.quote("|"));

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                inventory.add(new Product(id, name, price));
            }

        } catch (IOException e) {
            System.out.println("Error while processing the file.");
            e.printStackTrace();
        }

        return inventory;
    }
}
