package pdm.h2.demo.objects.Products.Computers;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Laptop {

    int UPC;
    int battery_life;
    int screen_size;

    public Laptop(int UPC, int battery_life, int screen_size) {
        this.UPC = UPC;
        this.battery_life = battery_life;
        this.screen_size = screen_size;
    }

    public int getUPC() {
        return UPC;
    }

    public int getBattary_life() {
        return battery_life;
    }

    public int getScreen_size() {
        return screen_size;
    }
}
