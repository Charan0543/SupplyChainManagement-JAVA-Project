import java.util.Queue;
import java.util.LinkedList;

public class ProcurementQueue {
    private Queue<PurchaseOrder> orders = new LinkedList<>();

    public void queueOrder(PurchaseOrder order) {
        orders.add(order);
        System.out.println("Order " + order.getPoId() + " placed in waiting line.");
    }

    public PurchaseOrder processNextOrder() {
        PurchaseOrder order = orders.poll();
        if (order != null) {
            System.out.println("Fulfilling Order " + order.getPoId() + " (Deadline: " + order.getDeadlineDate() + ")");
        } else {
            System.out.println("No orders in the waiting line.");
        }
        return order;
    }
}
