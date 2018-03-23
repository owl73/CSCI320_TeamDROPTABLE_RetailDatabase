package pdm.h2.demo.objects; /**
 * Created by Oscar on 3/21/2018.
 */

import java.sql.Timestamp;

public class Customer {

    int id;
    String name;
    Timestamp date;
    String phone1;
    String phone2;

    public Customer(int id, String name, Timestamp date, String phone1, String phone2) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public Customer(String[] data){
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.date = Timestamp.valueOf(data[2]);
        this.phone1 = data[3];
        this.phone2 = data[4];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }
}
