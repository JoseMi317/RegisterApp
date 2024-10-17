package Backend.Class.CreateUser;

import Main.Java.DataBase.*;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUser {
    public void saveUser(int clienteId, String numeroCuenta, String tipoCuenta, String pinHash, 
        boolean alertasSms, boolean alertasEmail, boolean accesoBancaOnline, 
        boolean chequera, String nombreUsuario, String passwordHash, String Saldo) {
        
        // Realizar la inserción en la base de datos
        String sql = "INSERT INTO Cuentas (cliente_id, numero_cuenta, tipo_cuenta, pin_hash, "
            + "alertas_sms, alertas_email, acceso_banca_online, chequera, "
            + "nombre_usuario, contraseña_hash, saldo) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseconnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clienteId);             // 1. cliente_id
            preparedStatement.setString(2, numeroCuenta);        // 2. numero_cuenta
            preparedStatement.setString(3, tipoCuenta);          // 3. tipo_cuenta
            preparedStatement.setString(4, pinHash);             // 4. password del usuario
            preparedStatement.setBoolean(5, alertasSms);         // 5. alertas_sms = 0
            preparedStatement.setBoolean(6, alertasEmail);       // 6. alertas_email = 0
            preparedStatement.setBoolean(7, accesoBancaOnline);  // 7. acceso_banca_online = 1
            preparedStatement.setBoolean(8, chequera);           // 8. chequera = 0
            preparedStatement.setString(9, nombreUsuario);       // 9. nombre_usuario
            preparedStatement.setString(10, passwordHash);       // 10. contraseña_hash (ya viene hasheado)
            preparedStatement.setString(11, Saldo);       // 11. saldo de la cuenta

            

            preparedStatement.executeUpdate();
            System.out.println("Usuario guardado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }


    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el hash de la contraseña", e);
        }
    }
}
