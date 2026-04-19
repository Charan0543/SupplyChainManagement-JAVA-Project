import java.util.Calendar;
import java.util.Date;

public class PurchaseOrder {
    private String poId;
    private String vendorId;
    private Calendar deadline;

    public PurchaseOrder(String poId, String vendorId, int daysUntilDeadline) {
        this.poId = poId;
        this.vendorId = vendorId;
        this.deadline = Calendar.getInstance();
        this.deadline.add(Calendar.DAY_OF_MONTH, daysUntilDeadline);
    }

    public String getPoId() {
        return poId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public Date getDeadlineDate() {
        return deadline.getTime();
    }
}
