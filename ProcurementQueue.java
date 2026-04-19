import java.util.Queue;
import java.util.LinkedList;

public class ProcurementQueue {
    private Queue<PurchaseOrder> orders = new LinkedList<>();

    public void queueOrder(PurchaseOrder order) {
        orders.add(order); // add order
        System.out.println("\n----------------------------------------------");
        System.out.println("Order " + order.getPoId() + " placed in waiting line.");
        System.out.println("----------------------------------------------");
    }

    public PurchaseOrder processNextOrder() {
        PurchaseOrder order = orders.poll(); // grab next order
        System.out.println("\n==============================================");
        if (order != null) {
            System.out.println("Fulfilling Order " + order.getPoId() + " (Deadline: " + order.getDeadlineDate() + ")");
        } else {
            System.out.println("No orders in the waiting line.");
            System.out.println("==============================================");
        }
        return order;
    }
}
