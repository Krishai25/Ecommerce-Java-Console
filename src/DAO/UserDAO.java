package DAO;
import Entities.User;
import Database.Databaseconnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAO {
    public static void registeruser(User user)
    {
        String query="INSERT INTO Users(username, email, password, role) VALUES (?, ?, ?, ?)";
        try {
            Connection con = Databaseconnection.getconnection();
            PreparedStatement pst=con.prepareStatement(query);
            pst.setString(1,user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3,user.getPassword());
            pst.setString(4,user.getRole());
            pst.executeUpdate();
            System.out.println(user.getName()+" Registered Successfully");
        } catch (Exception e) {
            System.out.println("Error Registering User "+e);
        }
    }
    public static boolean authenticateuser(String email,String password)
    {
        String query="Select password from Users where email=?";
        try {
            Connection con = Databaseconnection.getconnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,email);
            ResultSet rs = pst.executeQuery();
            String authpwd= hashpassword(password);
            if(rs.next())
            {
                if(rs.getString(1).equals(authpwd));{
                System.out.println("Login Successfull");
                return true;
            }
            }
        } catch (SQLException e) {
            System.out.println("Authentication Error "+e);
        }
        return false;
    }
    private static String hashpassword(String p)
    {
        String ph="";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] pb = messageDigest.digest(p.getBytes());
            ph = byteArraytoHexString(pb);
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Error in password Hashing " + nsae);
        }
        return ph;
    }
    private static String byteArraytoHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();


    }
    public static String getUserRole(String email) {
        String query = "SELECT role FROM Users WHERE email = ?";
        try {
            Connection con = Databaseconnection.getconnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            System.out.println("Error getting user role: " + e);
        }
        return null;
    }
    public static int getUserIdByEmail(String email) {
        int userId = -1;
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT userId FROM users WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("userId");
            }
        } catch (SQLException e) {
            System.out.println("Error getting user ID by email: " + e.getMessage());
        }
        return userId;
    }
    public static String getUsernameByEmail(String email) {
        String name = "";
        try {
            Connection con = Databaseconnection.getconnection();
            String query = "SELECT username FROM users WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching username by email: " + e.getMessage());
        }
        return name;
    }


}
