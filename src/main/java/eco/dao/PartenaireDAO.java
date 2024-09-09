package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Partenaire;
import main.java.eco.enums.StatutPartenaire;
import main.java.eco.enums.TypeTransport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartenaireDAO {
    private final Connection connection;

    public PartenaireDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addPartenaire(Partenaire partenaire) throws SQLException {
        String query = "INSERT INTO partenaires (id, nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire, date_creation) VALUES (?, ?, ?, ?::type_transport, ?, ?, ?::statut_partenaire, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, partenaire.getId());
            stmt.setString(2, partenaire.getNomCompagnie());
            stmt.setString(3, partenaire.getContactCommercial());
            stmt.setString(4, partenaire.getTypeTransport().name().toLowerCase());
            stmt.setString(5, partenaire.getZoneGeographique());
            stmt.setString(6, partenaire.getConditionsSpeciales());
            stmt.setString(7, partenaire.getStatutPartenaire().name().toLowerCase());
            stmt.setDate(8, new java.sql.Date(partenaire.getDateCreation().getTime()));
            stmt.executeUpdate();
        }
    }

    public void updatePartenaire(UUID id, Partenaire updatedPartenaire) throws SQLException {
        String query = "UPDATE partenaires SET nom_compagnie = ?, contact_commercial = ?, type_transport = ?::type_transport, zone_geographique = ?, conditions_speciales = ?, statut_partenaire = ?::statut_partenaire, date_creation = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, updatedPartenaire.getNomCompagnie());
            stmt.setString(2, updatedPartenaire.getContactCommercial());
            stmt.setString(3, updatedPartenaire.getTypeTransport().name().toLowerCase());
            stmt.setString(4, updatedPartenaire.getZoneGeographique());
            stmt.setString(5, updatedPartenaire.getConditionsSpeciales());
            stmt.setString(6, updatedPartenaire.getStatutPartenaire().name().toLowerCase());
            stmt.setDate(7, new java.sql.Date(updatedPartenaire.getDateCreation().getTime()));
            stmt.setObject(8, id);
            stmt.executeUpdate();
        }
    }

    public void deletePartenaire(UUID id) throws SQLException {
        String query = "DELETE FROM partenaires WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    public Partenaire getPartenaire(UUID id) throws SQLException {
        String query = "SELECT * FROM partenaires WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Partenaire(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_compagnie"),
                        rs.getString("contact_commercial"),
                        TypeTransport.valueOf(rs.getString("type_transport").toUpperCase()),
                        rs.getString("zone_geographique"),
                        rs.getString("conditions_speciales"),
                        StatutPartenaire.valueOf(rs.getString("statut_partenaire").toUpperCase()),
                        rs.getDate("date_creation")
                );
            }
            return null;
        }
    }

    public List<Partenaire> getAllPartenaires() throws SQLException {
        List<Partenaire> partenaires = new ArrayList<>();
        String query = "SELECT * FROM partenaires";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                partenaires.add(new Partenaire(
                        (UUID) rs.getObject("id"),
                        rs.getString("nom_compagnie"),
                        rs.getString("contact_commercial"),
                        TypeTransport.valueOf(rs.getString("type_transport").toUpperCase()),
                        rs.getString("zone_geographique"),
                        rs.getString("conditions_speciales"),
                        StatutPartenaire.valueOf(rs.getString("statut_partenaire").toUpperCase()),
                        rs.getDate("date_creation")
                ));
            }
        }
        return partenaires;
    }
}
