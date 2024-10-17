package Frontend.MainPage;

import Frontend.FormPage.FormPage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage extends JFrame {
    private Image backgroundImage;

    public MainPage() {
        super("Bienvenido a la Aplicación");
        setSize(600, 300); // Tamaño de la ventana
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Cargar la imagen de fondo
        String imagePath = "img/backbg.png"; // Cambia esto a la ruta correcta
        backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);

        // Panel principal
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Título
        JLabel titleLabel = new JLabel("¡Bienvenido a Nuestra Aplicación De Registro!", JLabel.CENTER);
        titleLabel.setFont(new Font("AvantGarde", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK); // Color del texto
        mainPanel.add(titleLabel, BorderLayout.CENTER); // Añadir título al centro

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Empieza a Registrarte");
        startButton.setFont(new Font("AvantGarde", Font.BOLD, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la página de formulario
                new FormPage().setVisible(true);
                dispose(); // Cierra la ventana principal
            }
        });
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Añadir panel de botones al sur
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainPage().setVisible(true);
        });
    }
}
