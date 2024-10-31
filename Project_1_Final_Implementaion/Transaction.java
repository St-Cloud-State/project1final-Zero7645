import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String details; // Add appropriate fields later as needed

    public Transaction(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Transaction details: " + details;
    }
}
