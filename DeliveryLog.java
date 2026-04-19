import java.util.Stack;

public class DeliveryLog {
    private Stack<String> deliveryHistory = new Stack<>();

    public synchronized void logDelivery(String details) {
        deliveryHistory.push(details);
        System.out.println("[Logbook] Truck arrival time recorded.");
    }

    public String getLastDelivery() {
        if (!deliveryHistory.isEmpty()) {
            return deliveryHistory.peek();
        }
        return "No trucks have arrived yet.";
    }

    public void printAllLogs() {
        System.out.println("\n==============================================");
        System.out.println("                 DELIVERY LOG                 ");
        System.out.println("----------------------------------------------");
        if (deliveryHistory.isEmpty()) {
            System.out.println("             No history recorded.             ");
            System.out.println("==============================================\n");
            return;
        }
        for (String log : deliveryHistory) {
            System.out.println("> " + log);
            System.out.println("----------------------------------------------");
        }
        System.out.println("==============================================\n");
    }
}
