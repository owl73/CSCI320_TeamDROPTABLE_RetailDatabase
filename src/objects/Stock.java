package objects;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Stock {

    int UPC;
    int storeID;
    int stock;
    double localPrice;

    public Stock(int UPC, int storeID, int stock, double localPrice) {
        this.UPC = UPC;
        this.storeID = storeID;
        this.stock = stock;
        this.localPrice = localPrice;
    }

    public int getUPC() {
        return UPC;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getStock() {
        return stock;
    }

    public double getLocalPrice() {
        return localPrice;
    }
}
