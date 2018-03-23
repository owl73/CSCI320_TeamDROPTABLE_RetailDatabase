package pdm.h2.demo.objects.Products;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Monitor {

    int UPC;
    int size;
    int ppi;
    String panal_type;

    public Monitor(int UPC, int size, int ppi, String panal_type) {
        this.UPC = UPC;
        this.size = size;
        this.ppi = ppi;
        this.panal_type = panal_type;
    }

    public int getUPC() {
        return UPC;
    }

    public int getSize() {
        return size;
    }

    public int getPpi() {
        return ppi;
    }

    public String getPanal_type() {
        return panal_type;
    }
}
