package objects;

import java.sql.Timestamp;

/**
 * Created by Oscar on 3/21/2018.
 */
public class Store {

    int id;
    String phone;
    Timestamp date_opened;
    double budget;
    String address;

    public Store(int id, String phone, Timestamp date_opened, double budget, String address) {
        this.id = id;
        this.phone = phone;
        this.date_opened = date_opened;
        this.budget = budget;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public Timestamp getDate_opened() {
        return date_opened;
    }

    public double getBudget() {
        return budget;
    }

    public String getAddress() {
        return address;
    }
}
