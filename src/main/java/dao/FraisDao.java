package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConfig;
import model.FraisModel;

public class FraisDao {
	private Connection connection;
	public FraisDao() {
		// TODO Auto-generated constructor stub
		this.connection = DatabaseConfig.getConnection();
	}
	public List<FraisModel> getAllFrais() {
        List<FraisModel> FraisModels = new ArrayList<>();
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM FRAIS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FraisModel FraisModel = new FraisModel();
                FraisModel.setIdfrais(rs.getString("idfrais"));
                FraisModel.setMontant1(rs.getInt("montant1"));
                FraisModel.setMontant2(rs.getInt("montant2"));
                FraisModel.setFrais(rs.getInt("frais"));
                FraisModels.add(FraisModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FraisModels;
    }

	public FraisModel getFraisById(String idfrais) {
	    FraisModel FraisModel = new FraisModel(); 
	    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM FRAIS WHERE idfrais = ?")) {
	        stmt.setString(1, idfrais);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            FraisModel.setIdfrais(rs.getString("idfrais"));
	            FraisModel.setMontant1(rs.getInt("montant1"));
	            FraisModel.setMontant2(rs.getInt("montant2"));
	            FraisModel.setFrais(rs.getInt("frais"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return FraisModel;
	}
	
	public void createFrais(FraisModel FraisModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO FRAIS (idfrais, montant1, montant2, frais) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, FraisModel.getIdfrais());
            stmt.setInt(2, FraisModel.getMontant1());
            stmt.setInt(3, FraisModel.getMontant2());
            stmt.setInt(4, FraisModel.getFrais());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void updateFrais(String idfrais, FraisModel FraisModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE FRAIS SET montant1 = ?, montant2 = ?, frais = ? WHERE idfrais = ?")) {
            stmt.setInt(1, FraisModel.getMontant1());
            stmt.setInt(2, FraisModel.getMontant2());
            stmt.setInt(3, FraisModel.getFrais());
            stmt.setString(4, idfrais);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void deleteFrais(String idfrais ) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM FRAIS WHERE idfrais = ?")) {
            stmt.setString(1, idfrais);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
