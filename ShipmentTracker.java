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
            System.out.println("\n[Delivery Truck-" + shipmentId + "] is currently: Driving to Store");
            
            // Simulate transit time
            Thread.sleep((long) (Math.random() * 2000 + 1000)); 
            
            status = "Pending Confirmation";
            
            if (waitingTrucks != null) {
                System.out.println("\nTruck " + shipmentId + " is waiting for confirmation");
                System.out.println("==============================================");
                waitingTrucks.add(shipmentId); // store in pending list
            } else {
                // simple console fallback
                deliveryLog.logDelivery("Truck " + shipmentId + " arrived safely at timestamp " + System.currentTimeMillis());
            }
            
        } catch (InterruptedException e) {
            System.out.println("\nDelivery Truck " + shipmentId + " crashed.");
            System.out.println("==============================================");
        }
    }

    public void trackShipment() {
        System.out.println("\nDispatching Delivery Truck for " + shipmentId);
        
        Thread trackingThread = new Thread(this);
        trackingThread.start();
    }
}
