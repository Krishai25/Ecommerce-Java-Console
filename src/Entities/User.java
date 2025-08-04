package Entities;
import java.security.*;
public class User {
    //    private int userid;
    private String name;
    private String email;
    private String password;
    private String role;

    //Constructor
    public User(/*int userid*/ String name, String email, String password, String role) {
//        this.userid = userid;
        this.name = name;
        this.email = email;
        setPassword(password);
        this.role = role;
    }

    // GETTERS AND SETTERS FOR THE ENTITIES
//    public int getUserid() {
//        return userid;
//    }
//
//    public void setUserid(int userid) {
//        this.userid = userid;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password=passwordhashingmethod(password);
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String byteArraytoHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));//converts the binary data from message digest to hexadecimal
        }
        return sb.toString();
    }
    private String passwordhashingmethod(String pwd) {
        String passwordhash="";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] passwordbytes = messageDigest.digest(pwd.getBytes());
            passwordhash = byteArraytoHexString(passwordbytes);
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Error in password Hashing " + nsae);
        }
        return passwordhash;
    }

}