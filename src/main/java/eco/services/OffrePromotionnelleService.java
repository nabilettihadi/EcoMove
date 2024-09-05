package main.java.eco.services;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.models.enums.StatutOffre;
import main.java.eco.models.enums.TypeReduction;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OffrePromotionnelleService {
    private final Connection connection;

    public OffrePromotionnelleService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addOffre(OffrePromotionnelle offre) throws SQLException {
        if (offre.getDateDebut().after(offre.getDateFin())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        String query = "INSERT INTO offres_promotionnelles (id, nom_offre, description, date_debut, date_fin, type_reduction, valeur_reduction, conditions, statut_offre, id_contrat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, offre.getId());
            stmt.setString(2, offre.getNomOffre());
            stmt.setString(3, offre.getDescription());
            stmt.setDate(4, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(5, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setObject(6, offre.getTypeReduction(), java.sql.Types.OTHER);
            stmt.setBigDecimal(7, offre.getValeurReduction());
            stmt.setString(8, offre.getConditions());
            stmt.setObject(9, offre.getStatutOffre().toString().toLowerCase(), java.sql.Types.OTHER);
            stmt.setObject(10, offre.getIdContrat());
            stmt.executeUpdate();
        }
    }


    public void updateOffre(UUID id, OffrePromotionnelle offre) throws SQLException {
        if (offre.getDateDebut().after(offre.getDateFin())) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        String query = "UPDATE offres_promotionnelles SET nom_offre = ?, description = ?, date_debut = ?, date_fin = ?, type_reduction = ?, valeur_reduction = ?, conditions = ?, statut_offre = ?, id_contrat = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, offre.getNomOffre());
            stmt.setString(2, offre.getDescription());
            stmt.setDate(3, new java.sql.Date(offre.getDateDebut().getTime()));
            stmt.setDate(4, new java.sql.Date(offre.getDateFin().getTime()));
            stmt.setObject(5, offre.getTypeReduction(), java.sql.Types.OTHER);
            stmt.setBigDecimal(6, offre.getValeurReduction());
            stmt.setString(7, offre.getConditions());
            stmt.setObject(8, offre.getStatutOffre().toString().toLowerCase(), java.sql.Types.OTHER);
            stmt.setObject(9, offre.getIdContrat());
            stmt.setObject(10, id);
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
            try (ResultSet rs = stmt.executeQuery()) {
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
                            StatutOffre.valueOf(rs.getString("statut_offre").toUpperCase()),
                            (UUID) rs.getObject("id_contrat")
                    );
                }
            }
        }
        return null;
    }

    public List<OffrePromotionnelle> getAllOffres() throws SQLException {
        List<OffrePromotionnelle> offres = new ArrayList<>();
        String query = "SELECT * FROM offres_promotionnelles";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
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
                        StatutOffre.valueOf(rs.getString("statut_offre").toUpperCase()),
                        (UUID) rs.getObject("id_contrat")
                ));
            }
        }
        return offres;
    }
}
