package pdm.h2.demo.objects;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Vendor {

    String name;
    String phone;
    String website;

    public Vendor(String name, String phone, String website) {
        this.name = name;
        this.phone = phone;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
