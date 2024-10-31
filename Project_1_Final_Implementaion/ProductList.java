import java.io.*;
import java.util.*;

public class ProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> products = new LinkedList<>();
    private static ProductList productList;

    private ProductList() {}

    public static ProductList instance() {
        if (productList == null) {
            productList = new ProductList();
        }
        return productList;
    }

    public boolean insertProduct(Product product) {
        products.add(product);
        return true;
    }

    public Iterator<Product> getProducts() {
        return products.iterator();
    }

    public Product getProductById(String productID) {
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeObject(productList);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        productList = (ProductList) input.readObject();
    }

    @Override
    public String toString() {
        return products.toString();
    }
}

