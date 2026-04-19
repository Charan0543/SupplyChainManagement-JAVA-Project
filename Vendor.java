public class Vendor {
    private String vendorId;
    private String name;
    private double rating;

    public Vendor(String vendorId, String name, double rating) {
        this.vendorId = vendorId;
        this.name = name;
        this.rating = rating;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
