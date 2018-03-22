package objects;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Brand {

    String name;
    String vendor;

    public Brand(String name, String vendor) {
        this.name = name;
        this.vendor = vendor;
    }

    public Brand(String[] data) {
        this.name = data[0];
        this.vendor = data[1];
    }

    public String getName() {
        return name;
    }

    public String getVendor() {
        return vendor;
    }
}
