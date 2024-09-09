package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.models.Contrat;
import main.java.eco.enums.StatutOffre;
import main.java.eco.enums.TypeReduction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OffrePromotionnelleDAO {
    private final Connection connection;
    private final ContratDAO contratDAO;

    public OffrePromotionnelleDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.contratDAO = new ContratDAO();
    }

    public void addOffrePromotionnelle(OffrePromotionnelle offre) throws SQLException {
        String query = "INSERT INTO offres_promotionnelles (id, nom_offre, description, date_debut, date_fin, type_reduction, valeur_reduction, conditions, statut_offre, id_contrat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, offre.getId());
            stmt.setString(2, offre.getNomOffre());
            stmt.setString(3, offre.getDescription());
            stmt.setDate(4, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(5, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setObject(6, offre.getTypeReduction().name(), Types.OTHER);
            stmt.setBigDecimal(7, offre.getValeurReduction());
            stmt.setString(8, offre.getConditions());
            stmt.setObject(9, offre.getStatutOffre().name().toLowerCase(), Types.OTHER);
            stmt.setObject(10, offre.getContrat().getId());
            stmt.executeUpdate();
        }
    }

    public void updateOffrePromotionnelle(OffrePromotionnelle offre) throws SQLException {
        String query = "UPDATE offres_promotionnelles SET nom_offre = ?, description = ?, date_debut = ?, date_fin = ?, type_reduction = ?, valeur_reduction = ?, conditions = ?, statut_offre = ?, id_contrat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, offre.getNomOffre());
            stmt.setString(2, offre.getDescription());
            stmt.setDate(3, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setObject(5, offre.getTypeReduction().name(), Types.OTHER);
            stmt.setBigDecimal(6, offre.getValeurReduction());
            stmt.setString(7, offre.getConditions());
            stmt.setObject(8, offre.getStatutOffre().name().toLowerCase(), Types.OTHER);
            stmt.setObject(9, offre.getContrat().getId());
            stmt.setObject(10, offre.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteOffrePromotionnelle(UUID id) throws SQLException {
        String query = "DELETE FROM offres_promotionnelles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    public OffrePromotionnelle getOffrePromotionnelle(UUID id) throws SQLException {
        String query = "SELECT * FROM offres_promotionnelles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID contratId = rs.getObject("id_contrat", UUID.class);
                Contrat contrat = contratDAO.getContrat(contratId);
                return new OffrePromotionnelle(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_offre"),
                        rs.getString("description"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        TypeReduction.valueOf(rs.getString("type_reduction").toUpperCase()),
                        rs.getBigDecimal("valeur_reduction"),
                        rs.getString("conditions"),
                        StatutOffre.valueOf(rs.getString("statut_offre").toUpperCase()),
                        contrat
                );
            }
            return null;
        }
    }

    public List<OffrePromotionnelle> getAllOffresPromotionnelles() throws SQLException {
        List<OffrePromotionnelle> offres = new ArrayList<>();
        String query = "SELECT * FROM offres_promotionnelles";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UUID contratId = rs.getObject("id_contrat", UUID.class);
                Contrat contrat = contratDAO.getContrat(contratId);
                offres.add(new OffrePromotionnelle(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_offre"),
                        rs.getString("description"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        TypeReduction.valueOf(rs.getString("type_reduction").toUpperCase()),
                        rs.getBigDecimal("valeur_reduction"),
                        rs.getString("conditions"),
                        StatutOffre.valueOf(rs.getString("statut_offre").toUpperCase()),
                        contrat
                ));
            }
        }
        return offres;
    }
}
