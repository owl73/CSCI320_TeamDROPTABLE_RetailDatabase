package pdm.h2.demo.objects.Products;

/**
 * Created by Oscar on 3/22/2018.
 */
public class Computer {

    int UPC;
    int ram;
    String processor;
    String disk;

    public Computer(int UPC, int ram, String processor, String disk) {
        this.UPC = UPC;
        this.ram = ram;
        this.processor = processor;
        this.disk = disk;
    }

    public int getUPC() {
        return UPC;
    }

    public int getRam() {
        return ram;
    }

    public String getProcessor() {
        return processor;
    }

    public String getDisk() {
        return disk;
    }
}
