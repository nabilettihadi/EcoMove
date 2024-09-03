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
        String query = "INSERT INTO billets (id, type_transport, prix_achat, prix_vente, date_vente, statut_billet) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, billet.getId());
            stmt.setString(2, billet.getTypeTransport().name());
            stmt.setBigDecimal(3, billet.getPrixAchat());
            stmt.setBigDecimal(4, billet.getPrixVente());
            stmt.setDate(5, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(6, billet.getStatutBillet().name());
            stmt.executeUpdate();
        }
    }

    public void updateBillet(Billet billet) throws SQLException {
        String query = "UPDATE billets SET type_transport = ?, prix_achat = ?, prix_vente = ?, date_vente = ?, statut_billet = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, billet.getTypeTransport().name());
            stmt.setBigDecimal(2, billet.getPrixAchat());
            stmt.setBigDecimal(3, billet.getPrixVente());
            stmt.setDate(4, new java.sql.Date(billet.getDateVente().getTime()));
            stmt.setString(5, billet.getStatutBillet().name());
            stmt.setObject(6, billet.getId());
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
                        TypeTransport.valueOf(rs.getString("type_transport")),
                        rs.getBigDecimal("prix_achat"),
                        rs.getBigDecimal("prix_vente"),
                        rs.getDate("date_vente"),
                        StatutBillet.valueOf(rs.getString("statut_billet"))
                ));
            }
        }
        return billets;
    }
}
