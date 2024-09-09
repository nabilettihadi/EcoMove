package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Contrat;
import main.java.eco.models.Partenaire;
import main.java.eco.enums.StatutContrat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContratDAO {
    private final Connection connection;

    public ContratDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addContrat(Contrat contrat) throws SQLException {
        if (contrat.getDateDebut().after(contrat.getDateFin())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        String query = "INSERT INTO contrats (id, date_debut, date_fin, tarif_special, conditions_accord, renouvelable, statut_contrat, id_partenaire) VALUES (?, ?, ?, ?, ?, ?, ?::statut_contrat, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, contrat.getId());
            stmt.setDate(2, new Date(contrat.getDateDebut().getTime()));
            stmt.setDate(3, new Date(contrat.getDateFin().getTime()));
            stmt.setBigDecimal(4, contrat.getTarifSpecial());
            stmt.setString(5, contrat.getConditionsAccord());
            stmt.setBoolean(6, contrat.isRenouvelable());
            stmt.setString(7, contrat.getStatutContrat().name().toLowerCase());
            stmt.setObject(8, contrat.getPartenaire().getId()); // Utilisation de partenaireId
            stmt.executeUpdate();
        }
    }

    public void updateContrat(Contrat contrat) throws SQLException {
        if (contrat.getDateDebut().after(contrat.getDateFin())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        String query = "UPDATE contrats SET date_debut = ?, date_fin = ?, tarif_special = ?, conditions_accord = ?, renouvelable = ?, statut_contrat = ?::statut_contrat, id_partenaire = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, new Date(contrat.getDateDebut().getTime()));
            stmt.setDate(2, new Date(contrat.getDateFin().getTime()));
            stmt.setBigDecimal(3, contrat.getTarifSpecial());
            stmt.setString(4, contrat.getConditionsAccord());
            stmt.setBoolean(5, contrat.isRenouvelable());
            stmt.setString(6, contrat.getStatutContrat().name().toLowerCase());
            stmt.setObject(7, contrat.getPartenaire().getId()); // Utilisation de partenaireId
            stmt.setObject(8, contrat.getId());
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
                // Retrieve the Partenaire object using PartenaireDAO
                PartenaireDAO partenaireDAO = new PartenaireDAO();
                Partenaire partenaire = partenaireDAO.getPartenaire((UUID) rs.getObject("id_partenaire"));

                return new Contrat(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getBigDecimal("tarif_special"),
                        rs.getString("conditions_accord"),
                        rs.getBoolean("renouvelable"),
                        StatutContrat.valueOf(rs.getString("statut_contrat").toUpperCase()),
                        partenaire // Utilisation de l'objet Partenaire
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
                // Retrieve the Partenaire object using PartenaireDAO
                PartenaireDAO partenaireDAO = new PartenaireDAO();
                Partenaire partenaire = partenaireDAO.getPartenaire((UUID) rs.getObject("id_partenaire"));

                contrats.add(new Contrat(
                        (UUID) rs.getObject("id"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getBigDecimal("tarif_special"),
                        rs.getString("conditions_accord"),
                        rs.getBoolean("renouvelable"),
                        StatutContrat.valueOf(rs.getString("statut_contrat").toUpperCase()),
                        partenaire // Utilisation de l'objet Partenaire
                ));
            }
        }
        return contrats;
    }
}
