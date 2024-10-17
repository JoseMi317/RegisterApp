package Frontend.UserPage;

import Backend.Class.CreateUser.CreateUser;
import Main.Java.DataBase.DataBaseconnector;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.*;

public class UserPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField initialBalanceField; // Campo para el saldo inicial
    private ButtonGroup accountTypeGroup;
    private Image backgroundImage;
    private int clientId;

    public UserPage(int clientId) {
        super("Asignar Usuario y Contraseña");
        setSize(800, 400); // Ajustar el tamaño para más espacio
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.clientId = clientId;
        
        // Cargar la imagen de fondo
        String imagePath = "img/backbg.png"; // Cambia esto a la ruta correcta
        backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
        
        // Añadir un panel personalizado que pintará el fondo
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo para el nombre de usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre de Usuario:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField = new JTextField(15), gbc); // Cambiar el tamaño del campo

        // Etiqueta y campo para la contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField = new JPasswordField(15), gbc); // Cambiar el tamaño del campo

        // Etiqueta y campo para el saldo inicial
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Saldo Inicial:"), gbc);

        gbc.gridx = 1;
        panel.add(initialBalanceField = new JTextField(15), gbc); // Campo para saldo inicial

        // Tipo de cuenta
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Tipo de Cuenta:"), gbc);

        gbc.gridx = 1;
        accountTypeGroup = new ButtonGroup();
        JRadioButton savingsAccount = new JRadioButton("Ahorro");
        JRadioButton checkingAccount = new JRadioButton("Monetaria");
        accountTypeGroup.add(savingsAccount);
        accountTypeGroup.add(checkingAccount);
        panel.add(savingsAccount, gbc);
        
        gbc.gridx = 2;
        panel.add(checkingAccount, gbc);

        // Botón de enviar
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton submitButton = new JButton("Enviar");
        panel.add(submitButton, gbc); 

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para enviar datos y continuar
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String initialBalance = initialBalanceField.getText();
                String accountType = savingsAccount.isSelected() ? "Ahorro" : "Monetaria";
        
                // Validar campos
                if (username.isEmpty() || password.isEmpty() || initialBalance.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos.");
                } else {

                    CreateUser createUser = new CreateUser();
                    String numeroCuenta = generateUniqueAccountNumber();
        
                    String passwordHash = createUser.hashPassword(password);
        
                    boolean alertasSms = false;
                    boolean alertasEmail = false; 
                    boolean accesoBancaOnline = true;
                    boolean chequera = false; 
        
                    createUser.saveUser(clientId, numeroCuenta, accountType, password,
                        alertasSms, alertasEmail, accesoBancaOnline, 
                        chequera, username, passwordHash, initialBalance);
        
                    JOptionPane.showMessageDialog(null, "Datos guardados con éxito.");
                    new ThankYouPage().setVisible(true); // Mostrar la página de agradecimiento
                    dispose(); // Cerrar el frame actual
                }
            }
        });
        

        // Botón de cancelar
        gbc.gridx = 1;
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cancelar?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cerrar el programa
                }
            }
        });
        panel.add(cancelButton, gbc);
        
        add(panel); // Añadir el panel al frame
    }

    // Clase interna para el panel de fondo
    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Método para generar un número de cuenta único
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = String.valueOf(random.nextInt(5000) + 1); // Generar número entre 1 y 5000
        } while (!isAccountNumberUnique(accountNumber));
        return accountNumber;
    }

    // Método para comprobar si el número de cuenta es único
    private boolean isAccountNumberUnique(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM Cuentas WHERE numero_cuenta = ?";
        try (Connection connection = DataBaseconnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Retorna true si no hay coincidencias, es único
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la unicidad del número de cuenta: " + e.getMessage());
        }
        return false; // Retorna false si hubo un error
    }

    private class ThankYouPage extends JFrame {
        public ThankYouPage() {
            super("Registro Exitoso");
            setSize(400, 200);
            setLocationRelativeTo(null); // Centrar en la pantalla
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            
            // Configurar panel para el mensaje
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new GridLayout(3, 1)); // Disposición vertical
    
            // Crear la fuente (ajusta el nombre de la fuente y el tamaño según tus preferencias)
            Font font = new Font("Arial", Font.PLAIN, 16); // Ejemplo: Arial, tamaño 16
            
            // Mensajes
            JLabel thankYouLabel = new JLabel("Gracias por registrarte!", SwingConstants.CENTER);
            thankYouLabel.setFont(font); // Aplicar la fuente
            messagePanel.add(thankYouLabel);
            
            JLabel successLabel = new JLabel("Todo salió correcto", SwingConstants.CENTER);
            successLabel.setFont(font); // Aplicar la fuente
            messagePanel.add(successLabel);
            
            JLabel atmLabel = new JLabel("Ahora ya puedes usar nuestro ATM", SwingConstants.CENTER);
            atmLabel.setFont(font); // Aplicar la fuente
            messagePanel.add(atmLabel);
    
            add(messagePanel); // Añadir el panel al frame
        }
    }
}
