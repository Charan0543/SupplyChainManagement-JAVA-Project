public class SCMS {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  Supply Chain Management System (SCMS) ");
        System.out.println("===========================================\n");

        // Initialize Managers and Queues
        VendorManager vendorManager = new VendorManager();
        ProcurementQueue procurementQueue = new ProcurementQueue();
        DeliveryLog deliveryLog = new DeliveryLog();

        // 1. Add Vendors
        System.out.println("1. Adding Vendors...");
        vendorManager.addVendor(new Vendor("V001", "Acme Corp", 4.5));
        vendorManager.addVendor(new Vendor("V002", "Global Tech Supplies", 4.8));
        vendorManager.addVendor(new Vendor("V003", "QuickLogistics", 3.9));

        // 2. Display Vendors
        System.out.println("\n2. Displaying Vendors...");
        vendorManager.enumerateVendors();

        // 3. Add Purchase Orders
        System.out.println("\n3. Adding Purchase Orders...");
        PurchaseOrder po1 = new PurchaseOrder("PO-101", "V001", 5);
        PurchaseOrder po2 = new PurchaseOrder("PO-102", "V002", 2);
        
        procurementQueue.queueOrder(po1);
        procurementQueue.queueOrder(po2);

        // 4. Process Orders & 5. Track Shipments
        System.out.println("\n4 & 5. Processing Orders and Starting Shipment Threads...");
        
        PurchaseOrder currentOrder = procurementQueue.processNextOrder();
        if (currentOrder != null) {
            ShipmentTracker tracker1 = new ShipmentTracker("SHP-12A", deliveryLog);
            tracker1.trackShipment();
        }

        currentOrder = procurementQueue.processNextOrder();
        if (currentOrder != null) {
            ShipmentTracker tracker2 = new ShipmentTracker("SHP-99B", deliveryLog);
            tracker2.trackShipment();
        }

        // Wait for threads to finish simulation (just for demonstration purposes in console)
        try {
            Thread.sleep(4000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6 & 7. Log Deliveries and View Last log
        System.out.println("\n6 & 7. Viewing Logs...");
        System.out.println("Last Delivery Data: " + deliveryLog.getLastDelivery());
        
        deliveryLog.printAllLogs();
        
        System.out.println("\n===========================================");
        System.out.println("        SCMS Execution Complete.         ");
        System.out.println("===========================================");
    }
}
