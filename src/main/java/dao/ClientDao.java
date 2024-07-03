package dao;

import model.ClientModel;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConfig;

public class ClientDao {
	 private static Connection connection;
	 
	public ClientDao() {
		// TODO Auto-generated constructor stub
		ClientDao.connection = DatabaseConfig.getConnection();
	}
	
	public List<ClientModel> getAllClients() {
        List<ClientModel> clientModels = new ArrayList<>();
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CLIENT");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ClientModel clientModel = new ClientModel();
                clientModel.setNumtel(rs.getString("numtel"));
                clientModel.setNom(rs.getString("nom"));
                clientModel.setSexe(rs.getString("sexe"));
                clientModel.setPays(rs.getString("pays"));
                clientModel.setSolde(rs.getInt("solde"));
                clientModel.setMail(rs.getString("mail"));
                clientModels.add(clientModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientModels;
    }
	public List<String> getAllPhoneNumbers() {
	    List<String> phoneNumbers = new ArrayList<>();
	    try (PreparedStatement stmt = connection.prepareStatement("SELECT numtel FROM CLIENT");
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            phoneNumbers.add(rs.getString("numtel"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return phoneNumbers;
	}

	 public static String getClientName(String numtel) throws SQLException {
	        String name = numtel; // Par défaut, on retourne le numéro si on ne trouve pas le nom
	        String query = "SELECT nom FROM CLIENT WHERE numtel = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, numtel);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                name = rs.getString("nom");
	            }
	        }
	        return name;
	    }
	public ClientModel getClientById(String numtel) {
	    ClientModel clientModel = new ClientModel(); // Initialiser un client vide
	    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CLIENT WHERE numtel = ?")) {
	        stmt.setString(1, numtel);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            clientModel.setNumtel(rs.getString("numtel"));
	            clientModel.setNom(rs.getString("nom"));
	            clientModel.setSexe(rs.getString("sexe"));
	            clientModel.setPays(rs.getString("pays"));
	            clientModel.setSolde(rs.getInt("solde"));
	            clientModel.setMail(rs.getString("mail"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clientModel;
	}
	public List<ClientModel> searchClients(String value) {
	    List<ClientModel> clients = new ArrayList<>();
	    String query = "SELECT * FROM CLIENT WHERE numtel LIKE ? OR nom LIKE ? OR sexe LIKE ? OR pays LIKE ? OR mail LIKE ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        String searchValue = "%" + value + "%";
	        for (int i = 1; i <= 5; i++) {
	            stmt.setString(i, searchValue);
	        }
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            ClientModel client = new ClientModel();
	            client.setNumtel(rs.getString("numtel"));
	            client.setNom(rs.getString("nom"));
	            client.setSexe(rs.getString("sexe"));
	            client.setPays(rs.getString("pays"));
	            client.setSolde(rs.getInt("solde"));
	            client.setMail(rs.getString("mail"));
	            clients.add(client);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clients;
	}

	
	public void createClient(ClientModel clientModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO CLIENT (numtel, nom, sexe, pays, solde, mail) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, clientModel.getNumtel());
            stmt.setString(2, clientModel.getNom());
            stmt.setString(3, clientModel.getSexe());
            stmt.setString(4, clientModel.getPays());
            stmt.setInt(5, clientModel.getSolde());
            stmt.setString(6, clientModel.getMail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void updateClient(String numtel, ClientModel clientModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET nom = ?, sexe = ?, pays = ?, solde = ?, mail = ? WHERE numtel = ?")) {
            stmt.setString(1, clientModel.getNom());
            stmt.setString(2, clientModel.getSexe());
            stmt.setString(3, clientModel.getPays());
            stmt.setInt(4, clientModel.getSolde());
            stmt.setString(5, clientModel.getMail());
            stmt.setString(6, numtel);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void deleteClient(String numtel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM CLIENT WHERE numtel = ?")) {
            stmt.setString(1, numtel);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public ClientModel getClientInfoByNumtel(String numtel) {
	    ClientModel clientModel = new ClientModel();
	    try (PreparedStatement stmt = connection.prepareStatement("SELECT nom, mail FROM CLIENT WHERE numtel = ?")) {
	        stmt.setString(1, numtel);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            clientModel.setNom(rs.getString("nom"));
	            clientModel.setMail(rs.getString("mail"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clientModel;
	}
	public static ClientModel getClientInfo(String numtel) throws SQLException {
	    ClientModel client = null;
	    String query = "SELECT * FROM CLIENT WHERE numtel = ?";
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, numtel);
	        
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            client = new ClientModel();
	            client.setNumtel(rs.getString("numtel"));
	            client.setNom(rs.getString("nom"));
	            client.setSexe(rs.getString("sexe"));
	            client.setPays(rs.getString("pays"));
	            client.setSolde(rs.getInt("solde"));
	            client.setMail(rs.getString("mail"));
	        }
	    }
	    return client;
	}
	public void updateBalances(String numEnvoyeur, String numRecepteur, int montant) throws SQLException {
	    try {
	        connection.setAutoCommit(false); // Démarrer une transaction

	        // Mettre à jour le solde de l'envoyeur
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde - ? WHERE numtel = ?")) {
	            stmt.setInt(1, montant);
	            stmt.setString(2, numEnvoyeur);
	            stmt.executeUpdate();
	        }

	        // Mettre à jour le solde du récepteur
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde + ? WHERE numtel = ?")) {
	            stmt.setInt(1, montant);
	            stmt.setString(2, numRecepteur);
	            stmt.executeUpdate();
	        }

	        connection.commit(); // Committer la transaction
	    } catch (SQLException e) {
	        connection.rollback(); // Annuler la transaction en cas d'erreur
	        throw e;
	    } finally {
	        connection.setAutoCommit(true); // Réinitialiser le mode auto-commit
	    }
	}
	public void updateBalancesOnCancellation(String numEnvoyeur, String numRecepteur, int montant) throws SQLException {
	    try {
	        connection.setAutoCommit(false); // Démarrer une transaction

	        // Rétablir les soldes des clients
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde + ? WHERE numtel = ?")) {
	            stmt.setInt(1, montant);
	            stmt.setString(2, numRecepteur);
	            stmt.executeUpdate();
	        }
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde - ? WHERE numtel = ?")) {
	            stmt.setInt(1, montant);
	            stmt.setString(2, numEnvoyeur);
	            stmt.executeUpdate();
	        }

	        connection.commit(); // Committer la transaction
	    } catch (SQLException e) {
	        connection.rollback(); // Annuler la transaction en cas d'erreur
	        throw e;
	    } finally {
	        connection.setAutoCommit(true); // Réinitialiser le mode auto-commit
	    }
	}
	public void updateBalancesOnIncrease(String numEnvoyeur, String numRecepteur, int increaseAmount) throws SQLException {
	    try {
	        connection.setAutoCommit(false); // Démarrer une transaction

	        // Augmenter le solde de l'envoyeur et diminuer celui du récepteur
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde - ? WHERE numtel = ?")) {
	            stmt.setInt(1, increaseAmount);
	            stmt.setString(2, numEnvoyeur);
	            stmt.executeUpdate();
	        }
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde + ? WHERE numtel = ?")) {
	            stmt.setInt(1, increaseAmount);
	            stmt.setString(2, numRecepteur);
	            stmt.executeUpdate();
	        }

	        connection.commit(); // Committer la transaction
	    } catch (SQLException e) {
	        connection.rollback(); // Annuler la transaction en cas d'erreur
	        throw e;
	    } finally {
	        connection.setAutoCommit(true); // Réinitialiser le mode auto-commit
	    }
	}

	public void updateBalancesOnDecrease(String numEnvoyeur, String numRecepteur, int decreaseAmount) throws SQLException {
	    try {
	        connection.setAutoCommit(false); // Démarrer une transaction

	        // Diminuer le solde du récepteur et augmenter celui de l'envoyeur
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde + ? WHERE numtel = ?")) {
	            stmt.setInt(1, decreaseAmount);
	            stmt.setString(2, numEnvoyeur);
	            stmt.executeUpdate();
	        }
	        try (PreparedStatement stmt = connection.prepareStatement("UPDATE CLIENT SET solde = solde - ? WHERE numtel = ?")) {
	            stmt.setInt(1, decreaseAmount);
	            stmt.setString(2, numRecepteur);
	            stmt.executeUpdate();
	        }

	        connection.commit(); // Committer la transaction
	    } catch (SQLException e) {
	        connection.rollback(); // Annuler la transaction en cas d'erreur
	        throw e;
	    } finally {
	        connection.setAutoCommit(true); // Réinitialiser le mode auto-commit
	    }
	}

}


