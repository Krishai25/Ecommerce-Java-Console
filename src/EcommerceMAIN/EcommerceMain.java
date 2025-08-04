// File: EcommerceMain.java
package EcommerceMAIN;
import Entities.*;
import Database.*;
import DAO.*;

import java.sql.SQLException;
import java.util.*;

public class EcommerceMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Databaseconnection.getconnection();
            System.out.println("Connection Established Successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boolean flag = true;

        while (flag) {
            System.out.println("---------*****************---------");
            System.out.println("Welcome TO Krishai's E-Commerce Platform");
            System.out.println("---------*****************---------");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Your Choice: ");
            int choice_of_user = sc.nextInt();

            switch (choice_of_user) {
                case 1: {
                    System.out.print("Enter Username: ");
                    String username = sc.next();
                    System.out.print("Enter Email ID: ");
                    String email = sc.next();
                    System.out.print("Enter Password: ");
                    String password = sc.next();
                    System.out.print("Enter Role (Admin/Customer): ");
                    String role = sc.next();
                    if(role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Customer")) {
                        User newUser = new User(username, email, password, role);
                        UserDAO.registeruser(newUser);
                    }
                    else{
                        System.out.println("Enter valid Role and other Credentials");
                    }
                    break;
                }

                case 2: {
                    System.out.print("Enter Email: ");
                    String email = sc.next();
                    System.out.print("Enter Password: ");
                    String password = sc.next();

                    boolean isAuthenticated = UserDAO.authenticateuser(email, password);
                    if (isAuthenticated) {
                        String role = UserDAO.getUserRole(email);
                        System.out.println("Logged in as " + role);

                        if (role.equalsIgnoreCase("Admin")) {
                            boolean adminFlag = true;
                            while (adminFlag) {
                                System.out.println("\nAdmin Menu:");
                                System.out.println("1. Add Category");
                                System.out.println("2. View All Categories");
                                System.out.println("3. Add Product Under Category");
                                System.out.println("4. Restock A Product");
                                System.out.println("5. Delivery Options");
                                System.out.println("6. Delete a Product");
                                System.out.println("7. Logout");
                                System.out.print("Enter choice: ");
                                int adminChoice = sc.nextInt();

                                switch (adminChoice) {
                                    case 1: {
                                        System.out.print("Enter Category Name: ");
                                        sc.nextLine();
                                        String catName = sc.nextLine();
                                        CategoryDAO.addCategory(catName);
                                        break;
                                    }
                                    case 2: {
                                        List<Category> categories = CategoryDAO.getAllCategories();
                                        System.out.println("Available Categories:");
                                        for (Category c : categories) {
                                            System.out.println(c.getCategory_id() + ": " + c.getName());
                                        }
                                        System.out.print("Enter Category ID to view products: ");
                                        int selectedCatId = sc.nextInt();
                                        List<Product> products = ProductDAO.getproductsbycategoryid(selectedCatId);
                                        if (products.isEmpty()) {
                                            System.out.println("No products found in this category.");
                                        } else {
                                            for (Product p : products) {
                                                System.out.println("Name: " + p.getName());
                                                System.out.println("Description: " + p.getDescription());
                                                System.out.println("Price: ₹" + p.getPrice());
                                                System.out.println("Stock: " + p.getStock_quantity());
                                                System.out.println("------------------------");
                                            }
                                        }
                                        break;
                                    }
                                    case 3: {
                                        List<Category> categories = CategoryDAO.getAllCategories();
                                        System.out.println("Select Category ID to add product into:");
                                        for (Category c : categories) {
                                            System.out.println(c.getCategory_id() + ": " + c.getName());
                                        }
                                        int catId = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Enter Product Name: ");
                                        String pname = sc.nextLine();
                                        System.out.print("Enter Description: ");
                                        String desc = sc.nextLine();
                                        System.out.print("Enter Price: ");
                                        double price = sc.nextDouble();
                                        System.out.print("Enter Stock Quantity: ");
                                        int qty = sc.nextInt();

                                        Product product = new Product(pname, desc, price, qty, catId);
                                        ProductDAO.addProducts(product);
                                        break;
                                    }
                                    case 4: {
                                        // restock option
                                        List<Category> categories = CategoryDAO.getAllCategories();
                                        System.out.println("Available Categories:");
                                        for (Category c : categories) {
                                            System.out.println(c.getCategory_id() + ": " + c.getName());
                                        }
                                        System.out.print("Enter category ID to view products: ");
                                        int catId = sc.nextInt();
                                        List<Product> products = ProductDAO.getproductsbycategoryid(catId);
                                        for (Product p : products) {
                                            System.out.println("Product ID: " + p.getProduct_id());
                                            System.out.println("Name: " + p.getName());
                                            System.out.println("Stock: " + p.getStock_quantity());
                                            System.out.println("-----------------------");
                                        }

                                        System.out.print("Enter Product ID to restock: ");
                                        int productId = sc.nextInt();
                                        System.out.print("Enter quantity to add: ");
                                        int quantity = sc.nextInt();
                                        boolean success = ProductDAO.increaseStock(productId, quantity);
                                        if (success) {
                                            System.out.println("Stock updated successfully.");
                                        } else {
                                            System.out.println("Failed to update stock.");
                                        }
                                        break;
                                    }
                                    case 5: {
                                        List<Purchase> undelivered = PurchaseDAO.getUndeliveredPurchases();
                                        if (undelivered.isEmpty()) {
                                            System.out.println("No pending deliveries.");
                                            break;
                                        }

                                        System.out.println("Pending Deliveries:");
                                        int index = 1;
                                        for (Purchase purchase : undelivered) {
                                            System.out.println(index++ + ") Customer: " + purchase.getCustomer_name());
                                            System.out.println("   Customer ID: "+purchase.getUserId());
                                            System.out.println("   Product: " + purchase.getProduct_name());
                                            System.out.println("   Purchased on: " + purchase.getPurchase_datetime());
                                            System.out.println("   Delivery Status: " + purchase.getDelivery_status());
                                            System.out.println("-----------------------------");
                                        }

                                        System.out.print("Enter Customer ID to mark their pending orders as Delivered: ");
                                        int customerId = sc.nextInt();
                                        boolean updated = PurchaseDAO.updateDeliveryStatusByUserId(customerId);
                                        if (updated) {
                                            System.out.println("Delivery status updated.");
                                        } else {
                                            System.out.println("Failed to update delivery status or no pending orders found.");
                                        }
                                        break;
                                    }
                                    case 6: {
                                        List<Category> categories = CategoryDAO.getAllCategories();
                                        System.out.println("Available Categories:");
                                        for (Category c : categories) {
                                            System.out.println(c.getCategory_id() + ": " + c.getName());
                                        }

                                        System.out.print("Enter category ID to view products: ");
                                        int catId = sc.nextInt();
                                        List<Product> products = ProductDAO.getproductsbycategoryid(catId);

                                        if (products.isEmpty()) {
                                            System.out.println("No products in this category.");
                                        } else {
                                            System.out.println("Products:");
                                            for (Product p : products) {
                                                System.out.println("Product ID: " + p.getProduct_id() + ", Name: " + p.getName());
                                            }

                                            System.out.print("Enter Product ID to delete: ");
                                            int deleteId = sc.nextInt();

                                            boolean deleted = ProductDAO.deleteProductById(deleteId);
                                            if (deleted) {
                                                System.out.println("Product deleted successfully.");
                                            } else {
                                                System.out.println("Failed to delete product.");
                                            }
                                        }
                                        break;
                                    }
                                    case 7:
                                        adminFlag = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Choice.");
                                }
                            }

                        } else if (role.equalsIgnoreCase("Customer")) {
                            boolean customerFlag = true;
                            List<CartItem> cart = new ArrayList<>();

                            while (customerFlag) {
                                System.out.println("\nCustomer Menu:");
                                System.out.println("1. View Products by Category");
                                System.out.println("2. View Cart");
                                System.out.println("3. Buy from Cart");
                                System.out.println("4. View Past Purchases");
                                System.out.println("5. Logout");
                                System.out.print("Enter your choice: ");
                                int custChoice = sc.nextInt();

                                switch (custChoice) {
                                    case 1: {
                                        List<Category> categories = CategoryDAO.getAllCategories();
                                        System.out.println("Available Categories:");
                                        for (Category c : categories) {
                                            System.out.println(c.getCategory_id() + ": " + c.getName());
                                        }
                                        System.out.print("Enter Category ID to view products: ");
                                        int selectedCatId = sc.nextInt();
                                        List<Product> products = ProductDAO.getproductsbycategoryid(selectedCatId);
                                        if (products.isEmpty()) {
                                            System.out.println("No products found in this category.");
                                        } else {
                                            for (Product p : products) {
                                                System.out.println("Product ID: " + p.getProduct_id());
                                                System.out.println("Name: " + p.getName());
                                                System.out.println("Description: " + p.getDescription());
                                                System.out.println("Price: ₹" + p.getPrice());
                                                System.out.println("Stock: " + p.getStock_quantity());
                                                System.out.println("------------------------");
                                            }
                                            System.out.print("Enter Product ID to add to cart or 0 to cancel: ");
                                            int pid = sc.nextInt();
                                            if (pid != 0) {
                                                Product selectedProduct = ProductDAO.getProductById(pid);
                                                if (selectedProduct != null) {
                                                    System.out.print("Enter quantity: ");
                                                    int qty = sc.nextInt();
                                                    if (qty > selectedProduct.getStock_quantity()) {
                                                        System.out.println("Insufficient stock.");
                                                    } else {
                                                        cart.add(new CartItem(selectedProduct, qty));
                                                        System.out.println("Item added to cart.");
                                                    }
                                                } else {
                                                    System.out.println("Invalid Product ID.");
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("Your Cart:");
                                        for (CartItem item : cart) {
                                            System.out.println(item.getProduct().getName() + " x " + item.getQuantity());
                                        }
                                        break;
                                    }
                                    case 3: {
                                        if (cart.isEmpty()) {
                                            System.out.println("Cart is empty.");
                                            break;
                                        }

                                        int userId = UserDAO.getUserIdByEmail(email);
                                        String customerName = UserDAO.getUsernameByEmail(email); // Add this helper if needed

                                        for (CartItem item : cart) {
                                            Product p = item.getProduct();
                                            int qty = item.getQuantity();
                                            boolean stockReduced = ProductDAO.reduceStock(p.getProduct_id(), qty);
                                            if (stockReduced) {
                                                PurchaseDAO.recordPurchase(userId, customerName, p.getProduct_id(), p.getName(), qty);
                                            } else {
                                                System.out.println("Failed to reduce stock for product: " + p.getName());
                                            }
                                        }


                                        System.out.println("Purchase complete. Thank you!");
                                        cart.clear();
                                        break;
                                    }
                                    case 4: { // View past purchases
                                        int userId = UserDAO.getUserIdByEmail(email);
                                        List<Purchase> pastOrders = PurchaseDAO.getPurchasesByUser(userId);
                                        if (pastOrders.isEmpty()) {
                                            System.out.println("You have no previous purchases.");
                                        } else {
                                            System.out.println("Your Past Purchases:");
                                            for (Purchase purchase : pastOrders) {
                                                System.out.println("Product: " + (purchase.getProduct_name() != null ? purchase.getProduct_name() : "[Deleted Product]"));
                                                System.out.println("Purchased On: " + purchase.getPurchase_datetime());
                                                System.out.println("Delivery Status: " + purchase.getDelivery_status());
                                                System.out.println("Delivered On: " + purchase.getDelivery_datetime());
                                                System.out.println("-----------------------------");
                                            }
                                        }
                                        break;
                                    }

                                    case 5:
                                        customerFlag = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice.");
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;
                }

                case 3: {
                    System.out.println("Exiting... Thank you!");
                    flag = false;
                    break;
                }

                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }

        sc.close();
    }
}
