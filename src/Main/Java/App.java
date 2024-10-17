package Main.Java;

import Frontend.MainPage.MainPage;
import Main.Java.DataBase.DataBaseconnector; // Asegúrate de importar tu clase MainPage
import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = DataBaseconnector.getConnection();
            System.out.println("Conexión exitosa a la base de datos");

            // Mostrar la página principal
            java.awt.EventQueue.invokeLater(() -> {
                new MainPage().setVisible(true); // Llamar a MainPage
            });

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
