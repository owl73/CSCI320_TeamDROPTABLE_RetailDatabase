package objects;

/**
 * Created by Oscar on 3/21/2018.
 */
public class Product {

    int UPC;
    String desc;
    String brand;

    public Product(int UPC, String desc, String brand) {
        this.UPC = UPC;
        this.desc = desc;
        this.brand = brand;
    }

    public int getUPC() {
        return UPC;
    }

    public String getDesc() {
        return desc;
    }

    public String getBrand() {
        return brand;
    }
}
