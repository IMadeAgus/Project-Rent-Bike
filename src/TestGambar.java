import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Config.Koneksi;
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author wedapradnyana27
 */

public class TestGambar {

    private Connection conn;

    public TestGambar() {
        conn = Koneksi.getConnection(); // Make sure the class name and getConnection method match your implementation
        createAndShowUI();
    }

    private DefaultTableModel getDataTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Gambar");

        try {
            String sql = "SELECT * FROM tbmotor";
            try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idMotor");
                    String nama = rs.getString("NamaMotor");
                    int harga = rs.getInt("Harga");
                    int jumlah = rs.getInt("Jumlah");

                    // Adding data to the table model
                    Object[] rowData = {id, nama, harga, jumlah, getImageIcon(rs.getBytes("Image"))};
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    private ImageIcon getImageIcon(byte[] imageData) {
        if (imageData != null) {
            return new ImageIcon(imageData);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestGambar::new);
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame("List Motor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Navbar
        JPanel navbar = new JPanel();
        navbar.add(new JLabel("Navbar Content"));

        // Main Panel (Card Layout)
        JPanel mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        DefaultTableModel model = getDataTableModel();
        List<CardPanel> cardPanels = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            String nama = model.getValueAt(row, 1).toString();
            int harga = (int) model.getValueAt(row, 2);
            int jumlah = (int) model.getValueAt(row, 3);
            ImageIcon imageIcon = (ImageIcon) model.getValueAt(row, 4);

            CardPanel cardPanel = new CardPanel(nama, harga, jumlah, imageIcon);
            cardPanels.add(cardPanel);
            mainPanel.add(cardPanel);
        }

        // Footer
        JPanel footer = new JPanel();
        footer.add(new JLabel("Footer Content"));

        // Combine everything into the frame
        frame.setLayout(new BorderLayout());
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


   static class CardPanel extends JPanel {
    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 100;

    public CardPanel(String nama, int harga, int jumlah, ImageIcon imageIcon) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        
        Image image = imageIcon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);

        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel nameLabel = new JLabel("Nama: " + nama);
        JLabel hargaLabel = new JLabel("Harga: " + harga);
        JLabel jumlahLabel = new JLabel("Jumlah: " + jumlah);

        JPanel textPanel = new JPanel(new GridLayout(3, 1));
        textPanel.add(nameLabel);
        textPanel.add(hargaLabel);
        textPanel.add(jumlahLabel);

        JButton pesanButton = new JButton("Pesan");
        pesanButton.addActionListener(e -> {
            // Handle the action when the "Pesan" button is clicked
            // You can implement your logic for handling the button click here
            JOptionPane.showMessageDialog(this, "Pesan button clicked for " + nama);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pesanButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(imageLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    
    
}

}


