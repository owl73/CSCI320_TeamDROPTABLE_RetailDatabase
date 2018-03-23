package pdm.h2.demo.objects.Products.Computers;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Desktop {

    int UPC;
    String powersupply;
    String card;

    public Desktop(int UPC, String powersupply, String card) {
        this.UPC = UPC;
        this.powersupply = powersupply;
        this.card = card;
    }

    public int getUPC() {
        return UPC;
    }

    public String getPowersupply() {
        return powersupply;
    }

    public String getCard() {
        return card;
    }
}
