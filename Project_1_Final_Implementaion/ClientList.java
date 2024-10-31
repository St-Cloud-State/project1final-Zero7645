import java.io.*;
import java.util.*;

public class ClientList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Client> clients = new LinkedList<>();
    private static ClientList clientList;

    private ClientList() {}

    public static ClientList instance() {
        if (clientList == null) {
            clientList = new ClientList();
        }
        return clientList;
    }

    public boolean insertClient(Client client) {
        clients.add(client);
        return true;
    }

    public Iterator<Client> getClients() {
        return clients.iterator();
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeObject(clientList);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        clientList = (ClientList) input.readObject();
    }

    // Currently using process_ver_2
    public String processClientOrder(String clientID, ProductList productList) {
        Client client = findClientById(clientID);
        Client new_Client = new Client(client.getClientID(), client.getfirstName(), client.getlastName(), 
        client.getAddress(), client.getPhone());        
        
        if (client == null) {
            return "Client not found.";
        }
    

        List<Product> wishListed = client.getWishList();
        List<Product> wishList = new LinkedList<>();
        Iterator<Product> iterator = wishListed.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            wishList.add(product);
        }

        if (wishList.isEmpty()) {
            return "Client's wishlist is empty.";
        }
    
        StringBuilder orderSummary = new StringBuilder("Order processed for " + client.getfirstName() + " " + client.getlastName() + ":\n");
        double totalCost = 0;
    
        iterator = wishList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            Product oldProduct = productList.getProductById(product.getProductID());
            if (product.getQuantity() > 0) {
                double itemCost = product.getPrice();
                int itemQuantity = product.getQuantity();
                double prodCost = itemCost * itemQuantity;
                int availQuantity = productList.getProductById(product.getProductID()).getQuantity();

                if (availQuantity < itemQuantity) {
                    oldProduct.setQuantity(product.getQuantity() - itemQuantity);
                    client.addToWaitList(product, itemQuantity - availQuantity);
                    orderSummary.append("  ").append(itemQuantity - availQuantity)
                    .append(" units added to waitlist\n");
                }

                else {
                    oldProduct.setQuantity(oldProduct.getQuantity() - availQuantity);
                }
                
                totalCost += prodCost;
                client.subtractFromBalance((float)prodCost); // Subtract the cost from client's balance, allowing it to go negative
                orderSummary.append("- ").append(product.getName())
                          .append(" (").append(product.getProductID()).append("): $")
                          .append(String.format("%.2f", itemCost)).append("\n");
                          
            } else {
                orderSummary.append("- ").append(product.getName())
                          .append(" (").append(product.getProductID())
                          .append("): Out of stock\n");
            }
        }
    
        client.clearWishlist();
        orderSummary.append("\nTotal cost: $").append(String.format("%.2f", totalCost));
        orderSummary.append("\nNew balance: $").append(String.format("%.2f", client.getBalance()));
    
        if (client.getBalance() < 0) {
            orderSummary.append("\nWARNING: Account balance is negative. Please add funds to your account.");
        }
    
        return orderSummary.toString();
    }

    public Client findClientById(String clientID) {
        for (Client client : clients) {
            if (client.getClientID().equals(clientID)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return clients.toString();
    }
}
