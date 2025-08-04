package DAO;

import Database.Databaseconnection;
import Entities.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAO {
    public static void addCategory(String name) {
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "INSERT INTO categories(name) VALUES(?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.executeUpdate();
            System.out.println("Category Added Successfully");
        } catch (SQLException e) {
            System.out.println("Error Adding Category: " + e.getMessage());
        }
    }

    public static List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT * FROM categories";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Error Fetching Categories: " + e.getMessage());
        }
        return list;
    }
}
