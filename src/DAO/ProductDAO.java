package DAO;

import Database.Databaseconnection;
import Entities.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public static void addProducts(Product product) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "INSERT INTO products(name, description, price, stockQuantity, category_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getPrice());
            pst.setInt(4, product.getStock_quantity());
            pst.setInt(5, product.getCategory_id());
            pst.executeUpdate();
            System.out.println("Product Has Been Added Successfully");
        } catch (SQLException se) {
            System.out.println("Error Adding Products " + se.getMessage());
        }
    }

    public static List<Product> getproductsbycategoryid(int category_id) {
        List<Product> products = new ArrayList<>();
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM products WHERE category_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, category_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("category_id")
                );
                products.add(product);
            }
        } catch (SQLException se) {
            System.out.println("Error in Viewing Products of the Category " + category_id + ": " + se.getMessage());
        }
        return products;
    }

    public static boolean reduceStock(int productId, int quantity) {
        try {
            Connection con = Databaseconnection.getconnection();

            // Check current stock
            String checkQuery = "SELECT stockQuantity FROM products WHERE productId = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentStock = rs.getInt("stockQuantity");
                if (currentStock < quantity) {
                    System.out.println("Not enough stock available.");
                    return false;
                }
            } else {
                System.out.println("Product not found.");
                return false;
            }

            // Update stock
            String updateQuery = "UPDATE products SET stockQuantity = stockQuantity - ? WHERE productId = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, productId);
            int rows = updateStmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error reducing stock: " + e.getMessage());
            return false;
        }
    }

    public static Product getProductById(int productId) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM products WHERE productId = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("category_id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product: " + e.getMessage());
        }
        return null;
    }
    public static boolean increaseStock(int productId, int quantity) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "UPDATE products SET stockQuantity = stockQuantity + ? WHERE productId = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, quantity);
            pst.setInt(2, productId);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error increasing stock: " + e.getMessage());
            return false;
        }
    }
    public static boolean deleteProductById(int productId) {
        try (Connection conn = Databaseconnection.getconnection()) {
            String deleteQuery = "DELETE FROM products WHERE productId = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, productId);
            int rows = deleteStmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }

}


