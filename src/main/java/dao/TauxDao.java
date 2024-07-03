package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConfig;
import model.TauxModel;

public class TauxDao {
	private Connection connection;
	public TauxDao() {
		// TODO Auto-generated constructor stub
		this.connection = DatabaseConfig.getConnection();
	}
	public List<TauxModel> getAllTaux() {
        List<TauxModel> TauxModels = new ArrayList<>();
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TAUX");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TauxModel TauxModel = new TauxModel();
                TauxModel.setIdtaux(rs.getString("idtaux"));
                TauxModel.setMontant1(rs.getInt("montant1"));
                TauxModel.setMontant2(rs.getInt("montant2"));
                TauxModels.add(TauxModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TauxModels;
    }

	public TauxModel getTauxById(String idtaux) {
	    TauxModel TauxModel = new TauxModel(); 
	    try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TAUX WHERE idtaux = ?")) {
	        stmt.setString(1, idtaux);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            TauxModel.setIdtaux(rs.getString("idtaux"));
	            TauxModel.setMontant1(rs.getInt("montant1"));
	            TauxModel.setMontant2(rs.getInt("montant2"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return TauxModel;
	}
	
	public void createTaux(TauxModel TauxModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO TAUX (idtaux, montant1, montant2) VALUES (?, ?, ?)")) {
            stmt.setString(1, TauxModel.getIdtaux());
            stmt.setInt(2, TauxModel.getMontant1());
            stmt.setInt(3, TauxModel.getMontant2());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void updateTaux(String idtaux, TauxModel TauxModel) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE TAUX SET montant1 = ?, montant2 = ? WHERE idtaux = ?")) {
            stmt.setInt(1, TauxModel.getMontant1());
            stmt.setInt(2, TauxModel.getMontant2());
            stmt.setString(3, idtaux);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void deleteTaux(String idtaux ) {
        // Bloc try-with-resources pour garantir la fermeture correcte des ressources
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM TAUX WHERE idtaux = ?")) {
            stmt.setString(1, idtaux);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
