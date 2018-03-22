package objects;

import java.sql.Timestamp;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Purchase {

    int ID;
    Timestamp date;
    double total;
    String paymentMethod;
    int storeID;
    int customerID;

    public Purchase(int ID, Timestamp date, double total, String paymentMethod, int storeID, int customerID) {
        this.ID = ID;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.storeID = storeID;
        this.customerID = customerID;
    }

    public int getID() {
        return ID;
    }

    public Timestamp getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getCustomerID() {
        return customerID;
    }
}
