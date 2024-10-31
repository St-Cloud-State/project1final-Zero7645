import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientID;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private float balance;
    private List<Product> wishList = new LinkedList<>();  
    private List<Product> waitList = new LinkedList<>();  
    private List transactions = new LinkedList();

    public Client(String clientID, String firstName, String lastName, String address, String phone) {
        this.clientID = clientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.balance = 0.00f;
    }

    public String getClientID() {
        return clientID;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void addToBalance(float amount) {
        this.balance += amount;
    }

    public void subtractFromBalance(float amount) {
        this.balance -= amount;
    }

    public void addToWishList(Product product, int quantity) {
        // Product wishlistProduct = new Product(product.getName(), product.getProductID(), product.getPrice(), quantity);
        wishList.add(product);
    }

    public void addToWaitList(Product product, int quantity) {
        Product waitlistProduct = new Product(product.getName(), product.getProductID(), product.getPrice(), quantity);
        wishList.add(waitlistProduct);
    }


    public List<Product> getWishList() {
        return wishList;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void clearWishlist() {
        wishList.clear();
    }

    @Override
    public String toString() {
        return "\t" + clientID + " " + firstName + lastName + ", ID: " + clientID + ", Address: " + address + ", Phone Number: " + phone + " $" + String.format("%.2f", balance);
    }
}
