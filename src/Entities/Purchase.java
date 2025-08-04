package Entities;

import java.sql.Timestamp;

public class Purchase {
    private int purchase_id;
    private int userId;
    private String customer_name;
    private int productId;
    private String product_name;
    private Timestamp purchase_datetime;
    private String delivery_status;
    private Timestamp delivery_datetime;

    public Purchase(int userId,String customer_name,int productId,String product_name)
    {
        this.userId=userId;
        this.customer_name=customer_name;
        this.productId=productId;
        this.product_name=product_name;
    }
    public Purchase(int purchase_id, int userId, String customer_name, int productId, String product_name,
                    Timestamp purchase_datetime, String delivery_status, Timestamp delivery_datetime) {
        this.purchase_id = purchase_id;
        this.userId = userId;
        this.customer_name = customer_name;
        this.productId = productId;
        this.product_name = product_name;
        this.purchase_datetime = purchase_datetime;
        this.delivery_status = delivery_status;
        this.delivery_datetime = delivery_datetime;
    }

    // Getters and Setters (you can auto-generate these)
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public String getCustomer_name() { return customer_name; }
    public String getProduct_name() { return product_name; }
    public String getDelivery_status() { return delivery_status; }
    public Timestamp getPurchase_datetime() { return purchase_datetime; }
    public Timestamp getDelivery_datetime() { return delivery_datetime; }

    public void setDelivery_status(String status) { this.delivery_status = status; }
    public void setDelivery_datetime(Timestamp dt) { this.delivery_datetime = dt; }

    public void setPurchase_datetime(Timestamp purchase_datetime) {
        this.purchase_datetime = purchase_datetime;
    }

    public int getPurchase_id() {
        return purchase_id;
    }
}




