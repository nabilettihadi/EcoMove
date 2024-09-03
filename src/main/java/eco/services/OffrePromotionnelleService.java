package main.java.eco.services;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.models.enums.StatutOffre;
import main.java.eco.models.enums.TypeReduction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OffrePromotionnelleService {
    private final Connection connection;

    public OffrePromotionnelleService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addOffre(OffrePromotionnelle offre) throws SQLException {
        String query = "INSERT INTO offres_promotionnelles (id, nom_offre, description, date_debut, date_fin, type_reduction, valeur_reduction, conditions, statut_offre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, offre.getId());
            stmt.setString(2, offre.getNomOffre());
            stmt.setString(3, offre.getDescription());
            stmt.setDate(4, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(5, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setString(6, offre.getTypeReduction().name());
            stmt.setBigDecimal(7, offre.getValeurReduction());
            stmt.setString(8, offre.getConditions());
            stmt.setString(9, offre.getStatutOffre().name());
            stmt.executeUpdate();
        }
    }

    public void updateOffre(UUID id, OffrePromotionnelle offre) throws SQLException {
        String query = "UPDATE offres_promotionnelles SET nom_offre = ?, description = ?, date_debut = ?, date_fin = ?, type_reduction = ?, valeur_reduction = ?, conditions = ?, statut_offre = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, offre.getNomOffre());
            stmt.setString(2, offre.getDescription());
            stmt.setDate(3, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setString(5, offre.getTypeReduction().name());
            stmt.setBigDecimal(6, offre.getValeurReduction());
            stmt.setString(7, offre.getConditions());
            stmt.setString(8, offre.getStatutOffre().name());
            stmt.setObject(9, id);
            stmt.executeUpdate();
        }
    }

    public void deleteOffre(UUID id) throws SQLException {
        String query = "DELETE FROM offres_promotionnelles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    public OffrePromotionnelle getOffre(UUID id) throws SQLException {
        String query = "SELECT * FROM offres_promotionnelles WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new OffrePromotionnelle(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_offre"),
                        rs.getString("description"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        TypeReduction.valueOf(rs.getString("type_reduction")),
                        rs.getBigDecimal("valeur_reduction"),
                        rs.getString("conditions"),
                        StatutOffre.valueOf(rs.getString("statut_offre"))
                );
            }
            return null;
        }
    }

    public List<OffrePromotionnelle> getAllOffres() throws SQLException {
        List<OffrePromotionnelle> offres = new ArrayList<>();
        String query = "SELECT * FROM offres_promotionnelles";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                offres.add(new OffrePromotionnelle(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_offre"),
                        rs.getString("description"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        TypeReduction.valueOf(rs.getString("type_reduction")),
                        rs.getBigDecimal("valeur_reduction"),
                        rs.getString("conditions"),
                        StatutOffre.valueOf(rs.getString("statut_offre"))
                ));
            }
        }
        return offres;
    }
}
