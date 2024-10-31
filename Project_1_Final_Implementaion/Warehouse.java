import java.io.*;
import java.util.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ProductList productList;
    private ClientList clientList;
    private static Warehouse warehouse;

    private Warehouse() {
        productList = ProductList.instance();
        clientList = ClientList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            return (warehouse = new Warehouse());
        }
        return warehouse;
    }

    public Product addProduct(String name, String productID, double price, int quantity) {
        Product product = new Product(name, productID, price, quantity);
        if (productList.insertProduct(product)) {
            return product;
        }
        return null;
    }

    public Client addClient(String clientID, String firstName, String lastName, String address, String phone) {
        Client client = new Client(clientID, firstName, lastName, address, phone);
        if (clientList.insertClient(client)) {
            return client;
        }
        return null;
    }

    public Iterator<Product> getProducts() {
        return productList.getProducts();
    }

    public Iterator<Client> getClients() {
        return clientList.getClients();
    }

    // Get the waitlist for a specific product
    public List<Client> getWaitlistForProduct(String productID) {
        Product product = productList.getProductById(productID);
        return (product != null) ? product.getWaitlistedQuantity() : new ArrayList<>();
    }    
   
    public Client getClientById(String clientID) {
        return clientList.findClientById(clientID);
    }

    public String addToWishList(String clientID, String productID, int quantity) {
        Client client = getClientById(clientID);
        Product product = productList.getProductById(productID);

        if (client == null) {
            return "Client not found.";
        }
        if (product == null) {
            return "Product not found.";
        }

        client.addToWishList(product, quantity);
        return "Product added to wishlist.";
    }

    public String processClientOrder(String clientID) {
        return clientList.processClientOrder(clientID, productList);
    }

    public String receivePayment(String clientID, float paymentAmount) {
        Client client = getClientById(clientID);
        if (client == null) {
            return "Client not found.";
        }

        client.addToBalance(paymentAmount);
        Transaction transaction = new Transaction("Payment received: $" + String.format("%.2f", paymentAmount));
        client.addTransaction(transaction);

        return "Payment of $" + String.format("%.2f", paymentAmount) + " received. Updated balance: $" + String.format("%.2f", client.getBalance());
    }

    public String receiveShipment(String productID, int quantityReceived) {
        Product product = productList.getProductById(productID);
        if (product == null) {
            return "Product not found.";
        }

        product.setQuantity(product.getQuantity() + quantityReceived);
        return "Shipment received. Stock updated to " + product.getQuantity();
    }

    public void printInvoices(String clientID) {
        Client client = getClientById(clientID);
        if (client == null) {
            System.out.println("Client not found.");
            return;
        }

        List<Transaction> transactions = client.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No invoices found for client " + clientID);
        } else {
            System.out.println("Invoices for client " + clientID + ":");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public static boolean save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("WarehouseData"))) {
            out.writeObject(warehouse);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Warehouse retrieve() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("WarehouseData"))) {
            warehouse = (Warehouse) in.readObject();
            return warehouse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Warehouse: \n" + productList + "\n" + clientList;
    }
}
