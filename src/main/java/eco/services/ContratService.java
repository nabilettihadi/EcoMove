package main.java.eco.services;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Contrat;
import main.java.eco.models.enums.StatutContrat;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContratService {
    private final Connection connection;

    public ContratService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addContrat(Contrat contrat) throws SQLException {
        String query = "INSERT INTO contrats (id, date_debut, date_fin, tarif_special, conditions_accord, renouvelable, statut_contrat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, contrat.getId()); // UUID
            stmt.setDate(2, new java.sql.Date(contrat.getDateDebut().getTime())); // Date
            stmt.setDate(3, new java.sql.Date(contrat.getDateFin().getTime())); // Date
            stmt.setBigDecimal(4, contrat.getTarifSpecial()); // BigDecimal
            stmt.setString(5, contrat.getConditionsAccord()); // String
            stmt.setBoolean(6, contrat.isRenouvelable()); // Boolean
            stmt.setObject(7, contrat.getStatutContrat().name(), Types.OTHER); // Enum
            stmt.executeUpdate();
        }
    }

    public void updateContrat(Contrat contrat) throws SQLException {
        String query = "UPDATE contrats SET date_debut = ?, date_fin = ?, tarif_special = ?, conditions_accord = ?, renouvelable = ?, statut_contrat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(contrat.getDateDebut().getTime())); // Date
            stmt.setDate(2, new java.sql.Date(contrat.getDateFin().getTime())); // Date
            stmt.setBigDecimal(3, contrat.getTarifSpecial()); // BigDecimal
            stmt.setString(4, contrat.getConditionsAccord()); // String
            stmt.setBoolean(5, contrat.isRenouvelable()); // Boolean
            stmt.setObject(6, contrat.getStatutContrat().name(), Types.OTHER); // Enum
            stmt.setObject(7, contrat.getId()); // UUID
            stmt.executeUpdate();
        }
    }

    public void deleteContrat(UUID id) throws SQLException {
        String query = "DELETE FROM contrats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    public Contrat getContrat(UUID id) throws SQLException {
        String query = "SELECT * FROM contrats WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contrat(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getBigDecimal("tarif_special"),
                        rs.getString("conditions_accord"),
                        rs.getBoolean("renouvelable"),
                        StatutContrat.valueOf(rs.getString("statut_contrat"))
                );
            }
            return null;
        }
    }

    public List<Contrat> getAllContrats() throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String query = "SELECT * FROM contrats";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contrats.add(new Contrat(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getBigDecimal("tarif_special"),
                        rs.getString("conditions_accord"),
                        rs.getBoolean("renouvelable"),
                        StatutContrat.valueOf(rs.getString("statut_contrat"))
                ));
            }
        }
        return contrats;
    }
}
