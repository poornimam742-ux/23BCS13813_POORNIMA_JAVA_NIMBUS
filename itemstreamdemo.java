import java.util.*;
import java.util.stream.*;

class Product {
    String name;
    double price;
    String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " (₹" + price + ", " + category + ")";
    }
}

public class ProductStreamExample {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Notebook", 80000, "Electronics"),
            new Product("Mobile", 50000, "Electronics"),
            new Product("Television", 40000, "Electronics"),
            new Product("Kurta", 2000, "Clothing"),
            new Product("Trousers", 2500, "Clothing"),
            new Product("Couch", 30000, "Furniture"),
            new Product("Desk", 15000, "Furniture")
        );

        // Group by category
        Map<String, List<Product>> byCategory = products.stream()
                .collect(Collectors.groupingBy(p -> p.category));

        System.out.println("Products grouped by category:");
        byCategory.forEach((cat, list) -> 
            System.out.println(cat + " -> " + list)
        );

        // Most expensive in each category
        Map<String, Product> topProduct = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.category,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(p -> p.price)),
                                Optional::get
                        )
                ));

        System.out.println("\nMost expensive product in each category:");
        topProduct.forEach((cat, prod) -> 
            System.out.println(cat + " -> " + prod)
        );

        // Average price of all products
        double avg = products.stream()
                .mapToDouble(p -> p.price)
                .average()
                .orElse(0.0);

        System.out.println("\nAverage price of all products: " + avg);
    }
}
