import java.util.List;

public class ShipmentTracker implements Runnable {
    private String shipmentId;
    private String status;
    private DeliveryLog deliveryLog;
    private List<String> waitingTrucks;

    public ShipmentTracker(String shipmentId, DeliveryLog deliveryLog) {
        this(shipmentId, deliveryLog, null);
    }

    public ShipmentTracker(String shipmentId, DeliveryLog deliveryLog, List<String> waitingTrucks) {
        this.shipmentId = shipmentId;
        this.status = "Pending";
        this.deliveryLog = deliveryLog;
        this.waitingTrucks = waitingTrucks;
    }

    @Override
    public void run() {
        try {
            status = "In Transit";
            System.out.println("[Delivery Truck-" + shipmentId + "] is currently: Driving to Store");
            
            // Simulate transit time
            Thread.sleep((long) (Math.random() * 2000 + 1000)); 
            
            status = "Delivered";
            System.out.println("[Delivery Truck-" + shipmentId + "] has: Arrived at Store");
            
            if (waitingTrucks != null) {
                System.out.println("Truck " + shipmentId + " has arrived and is waiting for confirmation.");
                waitingTrucks.add(shipmentId);
            } else {
                // simple console fallback
                deliveryLog.logDelivery("Truck " + shipmentId + " arrived safely at timestamp " + System.currentTimeMillis());
            }
            
        } catch (InterruptedException e) {
            System.out.println("[Delivery Truck-" + shipmentId + "] was interrupted/crashed.");
        }
    }

    public void trackShipment() {
        System.out.println("Dispatching Delivery Truck for " + shipmentId);
        Thread trackingThread = new Thread(this);
        trackingThread.start();
    }
}
