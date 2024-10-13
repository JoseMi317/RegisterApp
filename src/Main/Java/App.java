package Main.Java;
import java.sql.Connection;
import java.sql.SQLException;
import Main.Java.DataBase.DataBaseconnector;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = DataBaseconnector.getConnection();
            System.out.println("Conexión exitosa a la base de datos");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}