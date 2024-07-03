package config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {
    private static  Connection connection = null;

    static {
        try {
            // Définir les propriétés de connexion dans une chaîne
            String connectionProperties = "db.driver=com.mysql.cj.jdbc.Driver\n"
                    + "db.url=jdbc:mysql://localhost:3306/GTAL\n"
                    + "db.username=root\n"
                    + "db.password=";

            // Créer un InputStream à partir de la chaîne
            InputStream input = new ByteArrayInputStream(connectionProperties.getBytes(StandardCharsets.UTF_8));

            Properties prop = new Properties();
            prop.load(input);

            String driver = prop.getProperty("db.driver");
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");

            Class.forName(driver);
            try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}