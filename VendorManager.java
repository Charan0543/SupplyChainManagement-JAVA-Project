import java.util.Hashtable;
import java.util.Enumeration;

public class VendorManager {
    private Hashtable<String, Vendor> vendorMap = new Hashtable<>();
    private static int vendorCount = 0;

    public void addVendor(Vendor vendor) {
        vendorMap.put(vendor.getVendorId(), vendor);
        vendorCount++;
    }

    public Vendor getVendor(String vendorId) {
        return vendorMap.get(vendorId);
    }

    public void enumerateVendors() {
        System.out.println("\n==============================================");
        System.out.println("        SUPPLIER LIST (Total: " + vendorCount + ")");
        System.out.println("==============================================");
        Enumeration<Vendor> vendors = vendorMap.elements();
        while (vendors.hasMoreElements()) {
            Vendor v = vendors.nextElement();
            System.out.println(" ID: " + v.getVendorId());
            System.out.println(" Name: " + v.getName());
            System.out.println(" Rating: " + v.getRating());
            System.out.println("----------------------------------------------");
        }
        System.out.println("==============================================\n");
    }
}
