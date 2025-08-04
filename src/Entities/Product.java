package Entities;

public class Product {
    private int product_id;
    private String name;
    private String description;
    private double price;
    private int stock_quantity;
    private int category_id;

    // Constructor with product_id (used for retrieving from DB)
    public Product(int product_id, String name, String description, double price, int stock_quantity, int category_id) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.category_id = category_id;
    }

    // Constructor without product_id (used for inserting new product)
    public Product(String name, String description, double price, int stock_quantity, int category_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.category_id = category_id;
    }

    public int getProduct_id() { return product_id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public int getStock_quantity() { return stock_quantity; }

    public int getCategory_id() { return category_id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setPrice(double price) { this.price = price; }

    public void setStock_quantity(int stock_quantity) { this.stock_quantity = stock_quantity; }

    public void setCategory_id(int category_id) { this.category_id = category_id; }
}
