# 🛒 Console-Based E-commerce Platform (Java + JDBC)

A fully functional **console-based e-commerce platform** built using **Java**, **JDBC**, and **MySQL**, designed as part of a learning project.

---

## 📌 Features

### 👤 Customer Functionalities
- Register and login (with secure password hashing)
- Browse products by category
- Add items to cart and buy them
- View past purchases with date/time, quantity, and total price

### 🛠️ Admin Functionalities
- Add new products and categories
- Update stock quantities
- Delete incorrect or outdated products
- View all customer purchases with delivery status
- Update delivery status and delivery time

---

## 🧰 Technologies Used

- Java (Console-based)
- JDBC (Java Database Connectivity)
- MySQL (Database)
- IntelliJ IDEA (IDE)
- Git & GitHub (Version control)

---

##👤 Author
V S Krishai
B.Tech Information Technology
KPR Institute of Engineering and Technology
Aspiring Java Web Developer | Cloud & DevOps Enthusiast 

---

## 📂 Project Structure

```plaintext
src/
├── DAO/
│   ├── CategoryDAO.java
│   ├── ProductDAO.java
│   ├── PurchaseDAO.java
│   └── UserDAO.java
├── Database/
│   └── Databaseconnection.java
├── Entities/
│   ├── User.java
│   ├── Product.java
│   ├── Purchase.java
│   ├── CartItem.java
│   └── Category.java
├── EcommerceMAIN/
│   └── EcommerceMain.java

