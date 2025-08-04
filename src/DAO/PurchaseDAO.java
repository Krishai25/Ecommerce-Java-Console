package DAO;

import Database.Databaseconnection;
import Entities.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    // 1. Record a purchase
    public static void recordPurchase(int userId, String customerName, int productId, String productName, int quantity) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "INSERT INTO purchases(userId, customer_name, productId, product_name, purchase_datetime, delivery_status) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setString(2, customerName);
            pst.setInt(3, productId);
            pst.setString(4, productName);
            pst.setString(5, "Pending"); // ✅ Always set "Pending"

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Purchase recorded successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error recording purchase: " + e.getMessage());
        }
    }


    // 2. Get all purchases (for admin)
    public static List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM purchases";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Purchase purchase = new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("userId"),
                        rs.getString("customer_name"),
                        rs.getInt("productId"),
                        rs.getString("product_name"),
                        rs.getTimestamp("purchase_datetime"),
                        rs.getString("delivery_status"),
                        rs.getTimestamp("delivery_datetime")
                );
                purchases.add(purchase);
            }
        } catch (SQLException se) {
            System.out.println("Error in viewing purchase list: " + se.getMessage());
        }
        return purchases;
    }

    // 3. Mark purchase as delivered
    public static List<Purchase> getUndeliveredPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM purchases WHERE delivery_status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Purchase purchase = new Purchase(
                        rs.getInt("userId"),
                        rs.getString("customer_name"),
                        rs.getInt("productId"),
                        rs.getString("product_name")
                );
                purchase.setPurchase_datetime(rs.getTimestamp("purchase_datetime"));
                purchase.setDelivery_status(rs.getString("delivery_status"));
                purchase.setDelivery_datetime(rs.getTimestamp("delivery_datetime"));
                purchases.add(purchase);
            }
        } catch (SQLException se) {
            System.out.println("Error fetching undelivered purchases: " + se.getMessage());
        }
        return purchases;
    }

    // 4. Get purchases by a specific user
    public static List<Purchase> getPurchasesByUser(int userId) {
        List<Purchase> list = new ArrayList<>();
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM purchases WHERE userId = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Purchase purchase = new Purchase(
                        rs.getInt("purchase_id"),
                        rs.getInt("userId"),
                        rs.getString("customer_name"),
                        rs.getInt("productId"),
                        rs.getString("product_name"),
                        rs.getTimestamp("purchase_datetime"),
                        rs.getString("delivery_status"),
                        rs.getTimestamp("delivery_datetime")
                );
                list.add(purchase);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user's purchases: " + e.getMessage());
        }
        return list;
    }
    // 5. Mark all pending purchases as delivered for a given customer ID
    public static boolean updateDeliveryStatusByUserId(int userId) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "UPDATE purchases SET delivery_status = 'Delivered', delivery_datetime = CURRENT_TIMESTAMP WHERE userId = ? AND delivery_status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Delivery status updated for " + rowsUpdated + " pending purchase(s) of user ID: " + userId);
                return true;
            } else {
                System.out.println("No pending purchases found for this user ID.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating delivery status: " + e.getMessage());
            return false;
        }
    }


}
