import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SCMSGUI extends JFrame {

    private JTextArea consoleTextArea;
    private VendorManager vendorManager;
    private ProcurementQueue procurementQueue;
    private DeliveryLog deliveryLog;
    private List<String> waitingTrucks = Collections.synchronizedList(new ArrayList<>());

    public SCMSGUI() {
        super("Supply Chain Management System");

        // setup backend
        vendorManager = new VendorManager();
        procurementQueue = new ProcurementQueue();
        deliveryLog = new DeliveryLog();

        // load dummy data
        vendorManager.addVendor(new Vendor("V001", "Acme Corp", 4.5));
        vendorManager.addVendor(new Vendor("V002", "Global Tech Supplies", 4.8));
        vendorManager.addVendor(new Vendor("V003", "QuickLogistics", 3.9));

        // setup ui
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // title
        JLabel titleLabel = new JLabel("Store Manager Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        // console output
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setBackground(new Color(30, 30, 30)); // Dark grey/black
        consoleTextArea.setForeground(new Color(0, 255, 0)); // Hacker green text
        consoleTextArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Live Store Activity Screen"));
        add(scrollPane, BorderLayout.CENTER);

        // redirect system out
        redirectSystemStreams();

        // controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // order form
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Order New Stock"));
        
        JTextField poIdField = new JTextField(8);
        JTextField vendorIdField = new JTextField(8);
        JTextField deadlineDaysField = new JTextField(5);
        
        formPanel.add(new JLabel("Order ID:"));
        formPanel.add(poIdField);
        formPanel.add(new JLabel("Supplier ID:"));
        formPanel.add(vendorIdField);
        formPanel.add(new JLabel("Deadline (Days):"));
        formPanel.add(deadlineDaysField);

        JButton addOrderBtn = new JButton("Place Order in Line");
        addOrderBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addOrderBtn.addActionListener(e -> {
            try {
                String po = poIdField.getText().trim();
                String vendor = vendorIdField.getText().trim();
                String daysStr = deadlineDaysField.getText().trim();
                
                if(po.isEmpty() || vendor.isEmpty() || daysStr.isEmpty()) {
                    throw new Exception("All fields must be filled!");
                }
                
                // check if vendor exists
                if (vendorManager.getVendor(vendor) == null) {
                    throw new Exception("Invalid Supplier ID: '" + vendor + "' does not exist!");
                }

                int days = Integer.parseInt(daysStr);
                PurchaseOrder order = new PurchaseOrder(po, vendor, days);
                procurementQueue.queueOrder(order);
                
                // clear fields
                poIdField.setText("");
                vendorIdField.setText("");
                deadlineDaysField.setText("");
            } catch (NumberFormatException ex) {
                System.out.println("[Error] Deadline days must be a number!");
            } catch (Exception ex) {
                System.out.println("[Error] Invalid input: " + ex.getMessage());
            }
        });
        formPanel.add(addOrderBtn);

        // buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton showVendorsBtn = new JButton("Show All Suppliers");
        showVendorsBtn.addActionListener(e -> vendorManager.enumerateVendors());
        
        JButton processNextBtn = new JButton("Send Delivery Truck");
        processNextBtn.addActionListener(e -> {
            PurchaseOrder order = procurementQueue.processNextOrder();
            if (order != null) {
                // start tracking thread
                ShipmentTracker tracker = new ShipmentTracker("SHP-" + order.getPoId(), deliveryLog, waitingTrucks);
                tracker.trackShipment();
            }
        });

        JButton viewLogsBtn = new JButton("View Arrived Trucks");
        viewLogsBtn.addActionListener(e -> deliveryLog.printAllLogs());

        JButton confirmArrivalBtn = new JButton("Confirm Truck Arrival");
        confirmArrivalBtn.addActionListener(e -> {
            if (waitingTrucks.isEmpty()) {
                System.out.println("No deliveries waiting for confirmation.");
            } else {
                // get the latest truck and log it
                int lastIndex = waitingTrucks.size() - 1;
                String id = waitingTrucks.get(lastIndex);
                waitingTrucks.remove(lastIndex);
                
                deliveryLog.logDelivery("Truck " + id + " arrived safely at timestamp " + System.currentTimeMillis());
                System.out.println("Truck " + id + " arrival confirmed and logged.");
            }
        });

        JButton clearConsoleBtn = new JButton("Clear Console");
        clearConsoleBtn.addActionListener(e -> consoleTextArea.setText(""));

        actionPanel.add(showVendorsBtn);
        actionPanel.add(processNextBtn);
        actionPanel.add(confirmArrivalBtn);
        actionPanel.add(viewLogsBtn);
        actionPanel.add(clearConsoleBtn);

        controlPanel.add(formPanel);
        controlPanel.add(actionPanel);

        // Add controls to window
        add(controlPanel, BorderLayout.SOUTH);

        // Print welcome sequence
        System.out.println("===========================================");
        System.out.println("    Welcome to Store Manager Supply System    ");
        System.out.println("===========================================");
        System.out.println("System fully initialized. Ready for operations.\n");
    }

    // update text area safely
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> {
            consoleTextArea.append(text);
            consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
        });
    }

    // redirect system output to gui
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                updateTextArea(new String(b, off, len));
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    public static void main(String[] args) {
        // set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set LookAndFeel");
        }

        // Start Java Swing Application
        SwingUtilities.invokeLater(() -> {
            SCMSGUI gui = new SCMSGUI();
            gui.setLocationRelativeTo(null); // Center on screen
            gui.setVisible(true);
        });
    }
}
