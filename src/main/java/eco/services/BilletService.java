package main.java.eco.services;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Billet;
import main.java.eco.models.enums.StatutBillet;
import main.java.eco.models.enums.TypeTransport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BilletService {
    private final Connection connection;

    public BilletService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addBillet(Billet billet) throws SQLException {
        String query = "INSERT INTO billets (id, id_contrat, type_transport, prix_achat, prix_vente, date_vente, statut_billet) VALUES (?, ?, CAST(? AS type_transport), ?, ?, ?, CAST(? AS statut_billet))";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, billet.getId());
            stmt.setObject(2, billet.getIdContrat());
            stmt.setString(3, billet.getTypeTransport().toString().toLowerCase());
            stmt.setBigDecimal(4, billet.getPrixAchat());
            stmt.setBigDecimal(5, billet.getPrixVente());
            stmt.setDate(6, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(7, billet.getStatutBillet().toString().toLowerCase());
            stmt.executeUpdate();
        }
    }

    public void updateBillet(Billet billet) throws SQLException {
        String query = "UPDATE billets SET id_contrat = ?, type_transport = CAST(? AS type_transport), prix_achat = ?, prix_vente = ?, date_vente = ?, statut_billet = CAST(? AS statut_billet) WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, billet.getIdContrat());
            stmt.setString(2, billet.getTypeTransport().name().toLowerCase());
            stmt.setBigDecimal(3, billet.getPrixAchat());
            stmt.setBigDecimal(4, billet.getPrixVente());
            stmt.setDate(5, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(6, billet.getStatutBillet().name().toLowerCase());
            stmt.setObject(7, billet.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBillet(UUID id) throws SQLException {
        String query = "DELETE FROM billets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    public Billet getBillet(UUID id) throws SQLException {
        String query = "SELECT * FROM billets WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Billet(
                        (UUID) rs.getObject("id"),
                        (UUID) rs.getObject("id_contrat"),
                        TypeTransport.valueOf(rs.getString("type_transport")),
                        rs.getBigDecimal("prix_achat"),
                        rs.getBigDecimal("prix_vente"),
                        rs.getDate("date_vente"),
                        StatutBillet.valueOf(rs.getString("statut_billet"))
                );
            }
            return null;
        }
    }

    public List<Billet> getAllBillets() throws SQLException {
        List<Billet> billets = new ArrayList<>();
        String query = "SELECT * FROM billets";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                billets.add(new Billet(
                        (UUID) rs.getObject("id"),
                        (UUID) rs.getObject("id_contrat"),
                        TypeTransport.valueOf(rs.getString("type_transport").toUpperCase()),
                        rs.getBigDecimal("prix_achat"),
                        rs.getBigDecimal("prix_vente"),
                        rs.getDate("date_vente"),
                        StatutBillet.valueOf(rs.getString("statut_billet").toUpperCase())
                ));
            }
        }
        return billets;
    }
}
