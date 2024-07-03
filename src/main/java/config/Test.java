package config;
import java.sql.Connection;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Connection connection = DatabaseConfig.getConnection();
            if (connection != null) {
                System.out.println("Connexion à la base de données établie avec succès !");
            } else {
                System.out.println("Échec de la connexion à la base de données.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
