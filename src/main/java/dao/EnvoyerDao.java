package dao;

import model.EnvoyerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import config.DatabaseConfig;

public class EnvoyerDao {
    private Connection connection;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public EnvoyerDao() {
        this.connection = DatabaseConfig.getConnection();
    }

    public List<EnvoyerModel> getAllEnvoyer() throws SQLException {
        List<EnvoyerModel> envoyerModels = new ArrayList<>();
        String sql = "SELECT * FROM ENVOYER";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                envoyerModels.add(mapResultSetToEnvoyerModel(rs));
            }
        }
        return envoyerModels;
    }

    public EnvoyerModel getEnvoyerById(String idEnv) throws SQLException {
        String sql = "SELECT * FROM ENVOYER WHERE idEnv = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, idEnv);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEnvoyerModel(rs);
                }
            }
        }
        return null;
    }
    public List<EnvoyerModel> searchEnvoyerByDate(String dateSearch) throws SQLException {
        List<EnvoyerModel> envoyerModels = new ArrayList<>();
        String sql = "SELECT * FROM ENVOYER WHERE date LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dateSearch + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    envoyerModels.add(mapResultSetToEnvoyerModel(rs));
                }
            }
        }
        return envoyerModels;
    }
    public void createEnvoyer(EnvoyerModel envoyerModel) throws SQLException {
    	
        String sql = "INSERT INTO ENVOYER (idEnv, numEnvoyeur, numRecepteur, montant, date, raison) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setStatementParameters(stmt, envoyerModel);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Envoyer failed, no rows affected.");
            }
        }
    }

    public void updateEnvoyer(String idEnv, EnvoyerModel envoyer) throws SQLException, ParseException {
        String sql = "UPDATE ENVOYER SET numEnvoyeur = ?, numRecepteur = ?, montant = ?, date = ?, raison = ? WHERE idEnv = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setStatementParameters(pstmt, envoyer);
            pstmt.setString(1, envoyer.getNumEnvoyeur());
            pstmt.setString(2, envoyer.getNumRecepteur());
            pstmt.setDouble(3, envoyer.getMontant());
            
            // Convertir la date String en java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(envoyer.getDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(4, sqlDate);
            
            pstmt.setString(5, envoyer.getRaison());
            pstmt.setString(6, idEnv);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating Envoyer failed, no rows affected.");
            }
        }
    }

    public void deleteEnvoyer(String idEnv) throws SQLException {
        String sql = "DELETE FROM ENVOYER WHERE idEnv = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, idEnv);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting Envoyer failed, no rows affected.");
            }
        }
    }

    private EnvoyerModel mapResultSetToEnvoyerModel(ResultSet rs) throws SQLException {
        EnvoyerModel model = new EnvoyerModel();
        model.setIdEnv(rs.getString("idEnv"));
        model.setNumEnvoyeur(rs.getString("numEnvoyeur"));
        model.setNumRecepteur(rs.getString("numRecepteur"));
        model.setMontant(rs.getInt("montant"));
        model.setDate(DATE_FORMAT.format(rs.getDate("date")));
        model.setRaison(rs.getString("raison"));
        return model;
    }

    private void setStatementParameters(PreparedStatement stmt, EnvoyerModel model) throws SQLException {
        stmt.setString(1, model.getIdEnv());
        stmt.setString(2, model.getNumEnvoyeur());
        stmt.setString(3, model.getNumRecepteur());
        stmt.setInt(4, model.getMontant());
        try {
            stmt.setDate(5, new java.sql.Date(DATE_FORMAT.parse(model.getDate()).getTime()));
        } catch (Exception e) {
            throw new SQLException("Invalid date format", e);
        }
        stmt.setString(6, model.getRaison());
    }
    public List<EnvoyerModel> getOperationsByClientAndMonth(String numtel, int month, int year) throws SQLException {
        List<EnvoyerModel> operations = new ArrayList<>();
        String query = "SELECT * FROM ENVOYER WHERE numEnvoyeur = ? AND MONTH(date) = ? AND YEAR(date) = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, numtel);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EnvoyerModel operation = new EnvoyerModel();
                    operation.setIdEnv(rs.getString("idEnv"));
                    operation.setNumEnvoyeur(rs.getString("numEnvoyeur"));
                    operation.setNumRecepteur(rs.getString("numRecepteur"));
                    operation.setMontant(rs.getInt("montant"));
                    
                    // Convertir la date SQL en format dd/MM/yyyy
                    java.sql.Date sqlDate = rs.getDate("date");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = sdf.format(sqlDate);
                    operation.setDate(formattedDate);
                    
                    operation.setRaison(rs.getString("raison"));
                    operations.add(operation);
                }
            }
        }
        return operations;
    }
}