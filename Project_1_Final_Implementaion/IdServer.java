import java.io.*;

public class IdServer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int clientIdCounter = 1;
    private int productIdCounter = 1;
    private static IdServer server;

    private IdServer() {}

    public static IdServer instance() {
        if (server == null) {
            server = new IdServer();
        }
        return server;
    }

    public String getClientId() {
        return "C" + clientIdCounter++;
    }

    public String getProductId() {
        return "P" + productIdCounter++;
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeObject(server);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        server = (IdServer) input.readObject();
    }

    @Override
    public String toString() {
        return "IdServer: Client ID Counter: " + clientIdCounter + ", Product ID Counter: " + productIdCounter;
    }
}
