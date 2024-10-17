package Backend.Class.GetData;

import Main.Java.DataBase.DataBaseconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetData {
    public int insertClientAndGetId(String nombre, String apellidoPaterno, String apellidoMaterno,
                                     String fechaNacimiento, String sexo, String correo1,
                                     String correo2, String correo3, String departamento,
                                     String municipio, String direccion, String codigoPostal,
                                     String identificacionTipo, String identificacionNumero,
                                     String lugarTrabajo, String fechaInicioLabores, 
                                     double sueldo) {
        String sql = "INSERT INTO Clientes (nombre, apellido_paterno, apellido_materno, fecha_nacimiento, "
                     + "sexo, correo1, correo2, correo3, departamento, municipio, "
                     + "direccion, codigo_postal, identificacion_tipo, identificacion_numero, "
                     + "lugar_trabajo, fecha_inicio_labores, sueldo) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int clientId = -1;

        // Cambia esto para que use la variable sql en lugar de una duplicada
        String insertSQL = sql; 
        
        // Inicializa la conexión
        try (Connection connection = DataBaseconnector.getConnection()) {
            // Obtener el ID máximo actual
            PreparedStatement maxIdStmt = connection.prepareStatement("SELECT MAX(id) FROM Clientes");
            ResultSet rs = maxIdStmt.executeQuery();
            if (rs.next()) {
                clientId = rs.getInt(1) + 1; // Incrementar el ID máximo encontrado
            }

            // Inserción del nuevo cliente
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            insertStmt.setString(1, nombre);
            insertStmt.setString(2, apellidoPaterno);
            insertStmt.setString(3, apellidoMaterno);
            insertStmt.setString(4, fechaNacimiento);
            insertStmt.setString(5, sexo);
            insertStmt.setString(6, correo1);
            insertStmt.setString(7, correo2);
            insertStmt.setString(8, correo3);
            insertStmt.setString(9, departamento);
            insertStmt.setString(10, municipio);
            insertStmt.setString(11, direccion);
            insertStmt.setString(12, codigoPostal);
            insertStmt.setString(13, identificacionTipo);
            insertStmt.setString(14, identificacionNumero);
            insertStmt.setString(15, lugarTrabajo);
            insertStmt.setString(16, fechaInicioLabores);
            insertStmt.setDouble(17, sueldo);

            insertStmt.executeUpdate(); // Ejecutar inserción
            return clientId; // Devolver el nuevo ID
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retornar -1 en caso de error
        }
    }
}
