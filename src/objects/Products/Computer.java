package objects.Products;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Computer {

    int UPC;
    int Ram;
    String processor;
    String Disk;

    public Computer(int UPC, int ram, String processor, String disk) {
        this.UPC = UPC;
        Ram = ram;
        this.processor = processor;
        Disk = disk;
    }

    public int getUPC() {
        return UPC;
    }

    public int getRam() {
        return Ram;
    }

    public String getProcessor() {
        return processor;
    }

    public String getDisk() {
        return Disk;
    }
}
