package Frontend.FormPage;

import Backend.Class.GetData.GetData;
import Frontend.UserPage.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class FormPage extends JFrame {
    private Image backgroundImage;

    // Campos del formulario (referencias para acceder a los datos)
    private JTextField nombreField;
    private JTextField apellidoPaternoField;
    private JTextField apellidoMaternoField;
    private JTextField fechaNacimientoField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JTextField correo1Field;
    private JTextField correo2Field;
    private JTextField correo3Field;
    private JTextField departamentoField;
    private JTextField municipioField;
    private JTextField direccionField;
    private JTextField codigoPostalField;
    private JComboBox<String> identificacionTipoComboBox;
    private JTextField identificacionNumeroField;
    private JTextField lugarTrabajoField;
    private JTextField fechaInicioLaboresField;
    private JTextField sueldoField;

    public FormPage() {
        super("FORMULARIO");
        setSize(800, 400); // Ventana más pequeña
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cargar la imagen de fondo
        String imagePath = "img\\backbg.png"; // Cambia esto a la ruta correcta
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

        // Panel de formulario dividido
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 2, 10, 10)); // Dos columnas
        formPanel.setOpaque(false); // Hacer que el panel sea transparente

        // Crear paneles para cada lado del formulario
        JPanel leftPanel = createFormSection("INFORMACION PERSONAL", new String[]{
            "Nombre:", "Apellido Paterno:", "Apellido Materno:", "Fecha de Nacimiento:",
            "Sexo:", "Correo 1:", "Correo 2:", "Correo 3:"
        });

        JPanel rightPanel = createFormSection("DATOS ADICIONALES", new String[]{
            "Departamento:", "Municipio:", "Dirección:", "Código Postal:",
            "Tipo de Identificación:", "Número de Identificación:", "Lugar de Trabajo:",
            "Fecha de Inicio de Labores:", "Sueldo:"
        });

        // Agregar ambos paneles al panel del formulario
        formPanel.add(leftPanel);
        formPanel.add(rightPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER); // Agregar al centro

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Enviar");
        JButton cancelButton = new JButton("Cancelar");

        // Acción al enviar
        submitButton.addActionListener(e -> {
        // Recoger datos del formulario
        String nombre = nombreField.getText();
        String apellidoPaterno = apellidoPaternoField.getText();
        String apellidoMaterno = apellidoMaternoField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();
        String sexo = maleCheckBox.isSelected() ? "M" : (femaleCheckBox.isSelected() ? "F" : "");
        String correo1 = correo1Field.getText();
        String correo2 = correo2Field.getText();
        String correo3 = correo3Field.getText();
        String departamento = departamentoField.getText();
        String municipio = municipioField.getText();
        String direccion = direccionField.getText();
        String codigoPostal = codigoPostalField.getText();
        String identificacionTipo = (String) identificacionTipoComboBox.getSelectedItem();
        String identificacionNumero = identificacionNumeroField.getText();
        String lugarTrabajo = lugarTrabajoField.getText();
        String fechaInicioLabores = fechaInicioLaboresField.getText();
        double sueldo = Double.parseDouble(sueldoField.getText());

        // Validar fecha
        if (isValidDate(fechaNacimiento) && isValidDate(fechaInicioLabores)) {
            // Crear instancia de GetData y guardar los datos
            GetData getData = new GetData();
            int clientId = getData.insertClientAndGetId(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento,
                    sexo, correo1, correo2, correo3, departamento,
                    municipio, direccion, codigoPostal, identificacionTipo,
                    identificacionNumero, lugarTrabajo, fechaInicioLabores, sueldo);
    
            if (clientId > 0) {
                JOptionPane.showMessageDialog(this, "Datos enviados correctamente. " );
                abrirVentanaCrearUsuario(clientId);
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Fecha(s) inválida(s). Use el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    // Agregar panel de botones al panel principal
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormSection(String title, String[] labels) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(labels.length + 1, 2, 10, 10)); // Dos columnas
        panel.setOpaque(false); // Hacer que el panel sea transparente
        panel.setBorder(BorderFactory.createTitledBorder(title)); // Título del panel

        // Agregar etiquetas y campos de texto
        for (String label : labels) {
            JLabel labelComponent = new JLabel(label, JLabel.CENTER); // Etiqueta centrada
            labelComponent.setForeground(Color.white); // Color del texto
            labelComponent.setFont(new Font("AvantGarde", Font.BOLD, 16)); // Fuente personalizada
            panel.add(labelComponent); // Añadir la etiqueta al panel

            switch (label) {
                case "Sexo:" -> {
                    // Checkboxes para el sexo
                    JPanel sexPanel = new JPanel();
                    maleCheckBox = new JCheckBox("M");
                    femaleCheckBox = new JCheckBox("F");
                    maleCheckBox.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
                    femaleCheckBox.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
                    maleCheckBox.setContentAreaFilled(false);
                    femaleCheckBox.setContentAreaFilled(false);
                    maleCheckBox.addActionListener(e -> femaleCheckBox.setSelected(!maleCheckBox.isSelected()));
                    femaleCheckBox.addActionListener(e -> maleCheckBox.setSelected(!femaleCheckBox.isSelected()));
                    sexPanel.add(maleCheckBox);
                    sexPanel.add(femaleCheckBox);
                    panel.add(sexPanel);
                }
                case "Tipo de Identificación:" -> {
                    identificacionTipoComboBox = new JComboBox<>(new String[]{"DPI", "Otros"});
                    panel.add(identificacionTipoComboBox);
                }
                case "Fecha de Nacimiento:", "Fecha de Inicio de Labores:" -> {
                    JTextField dateField = new JTextField(10);
                    dateField.setText("YYYY-MM-DD");
                    dateField.addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (dateField.getText().equals("YYYY-MM-DD")) {
                                dateField.setText("");
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (dateField.getText().isEmpty()) {
                                dateField.setText("YYYY-MM-DD");
                            }
                        }
                    });
                    panel.add(dateField);

                    if (label.equals("Fecha de Nacimiento:")) {
                        fechaNacimientoField = dateField;
                    } else {
                        fechaInicioLaboresField = dateField;
                    }
                }
                default -> {
                    JTextField textField = new JTextField(10);
                    panel.add(textField);
                    assignFieldReference(label, textField);
                }
            }
        }

        return panel;
    }

    private void assignFieldReference(String label, JTextField textField) {
        switch (label) {
            case "Nombre:" -> nombreField = textField;
            case "Apellido Paterno:" -> apellidoPaternoField = textField;
            case "Apellido Materno:" -> apellidoMaternoField = textField;
            case "Correo 1:" -> correo1Field = textField;
            case "Correo 2:" -> correo2Field = textField;
            case "Correo 3:" -> correo3Field = textField;
            case "Departamento:" -> departamentoField = textField;
            case "Municipio:" -> municipioField = textField;
            case "Dirección:" -> direccionField = textField;
            case "Código Postal:" -> codigoPostalField = textField;
            case "Número de Identificación:" -> identificacionNumeroField = textField;
            case "Lugar de Trabajo:" -> lugarTrabajoField = textField;
            case "Sueldo:" -> sueldoField = textField;
        }
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void abrirVentanaCrearUsuario(int clientId) {
        UserPage nuevaVentana = new UserPage(clientId); // Pasar el ID al constructor
        nuevaVentana.setVisible(true);
        this.dispose(); // Cierra la ventana actual si no la necesitas más.
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPage().setVisible(true));
    }
}
