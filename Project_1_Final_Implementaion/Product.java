import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String productID;
    private double price;
    private int quantity;
    private int waitlistedQuantity; // New attribute to track waitlisted quantity

    public Product(String name, String productID, double price, int quantity) {
        this.name = name;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
        this.waitlistedQuantity = 0; // Initialize waitlist to 0
    }

    public String getName() {
        return name;
    }

    public String getProductID() {
        return productID;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWaitlistedQuantity() {
        return waitlistedQuantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addToWaitlist(int quantity) {
        this.waitlistedQuantity += quantity;
    }

    public void fillWaitlist(int receivedQuantity) {
        if (waitlistedQuantity > 0) {
            int fulfilled = Math.min(waitlistedQuantity, receivedQuantity);
            waitlistedQuantity -= fulfilled;
            this.quantity += receivedQuantity - fulfilled;
        } else {
            this.quantity += receivedQuantity;
        }
    }

    @Override
    public String toString() {
        return "Product name: " + name + ", ID: " + productID + ", Price: " + price + ", Stock: " + quantity + ", Waitlisted: " + waitlistedQuantity;
    }
}
