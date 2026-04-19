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
        System.out.println("\nSUPPLIER LIST (Total: " + vendorCount + ")");
        Enumeration<Vendor> vendors = vendorMap.elements();
        while(vendors.hasMoreElements()) {
            Vendor v = vendors.nextElement();
            System.out.println("ID: " + v.getVendorId() + " | Name: " + v.getName() + " | Rating: " + v.getRating());
        }
    }
}
