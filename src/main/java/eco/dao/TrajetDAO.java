package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Trajet;
import main.java.eco.enums.TypeTransport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrajetDAO {
    private Connection connection;

    public TrajetDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // CREATE
    public void addTrajet(Trajet trajet) throws SQLException {
        String query = "INSERT INTO trajet (ville_depart_id, ville_arrivee_id, date_depart, date_arrivee, transporteur) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trajet.getVilleDepartId());
            pstmt.setInt(2, trajet.getVilleArriveeId());
            pstmt.setTimestamp(3, Timestamp.valueOf(trajet.getDateDepart()));
            pstmt.setTimestamp(4, Timestamp.valueOf(trajet.getDateArrivee()));
            pstmt.setObject(5, trajet.getTransporteur().name().toLowerCase(), Types.OTHER);
            pstmt.executeUpdate();
        }
    }

    // READ
    public Trajet getTrajetById(int id) throws SQLException {
        String query = "SELECT * FROM trajet WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TypeTransport typeTransport = TypeTransport.valueOf(rs.getString("transporteur").toUpperCase());
                    return new Trajet(
                            rs.getInt("id"),
                            rs.getInt("ville_depart_id"),
                            rs.getInt("ville_arrivee_id"),
                            rs.getTimestamp("date_depart").toLocalDateTime(),
                            rs.getTimestamp("date_arrivee").toLocalDateTime(),
                            typeTransport
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateTrajet(Trajet trajet) throws SQLException {
        String query = "UPDATE trajet SET ville_depart_id = ?, ville_arrivee_id = ?, date_depart = ?, date_arrivee = ?, transporteur = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trajet.getVilleDepartId());
            pstmt.setInt(2, trajet.getVilleArriveeId());
            pstmt.setTimestamp(3, Timestamp.valueOf(trajet.getDateDepart()));
            pstmt.setTimestamp(4, Timestamp.valueOf(trajet.getDateArrivee()));
            pstmt.setObject(5, trajet.getTransporteur().name().toLowerCase(), Types.OTHER);
            pstmt.setInt(6, trajet.getId());
            pstmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteTrajet(int id) throws SQLException {
        String query = "DELETE FROM trajet WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // LIST
    public List<Trajet> getAllTrajets() throws SQLException {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM trajet";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                TypeTransport typeTransport = TypeTransport.valueOf(rs.getString("transporteur").toUpperCase());
                Trajet trajet = new Trajet(
                        rs.getInt("id"),
                        rs.getInt("ville_depart_id"),
                        rs.getInt("ville_arrivee_id"),
                        rs.getTimestamp("date_depart").toLocalDateTime(),
                        rs.getTimestamp("date_arrivee").toLocalDateTime(),
                        typeTransport
                );
                trajets.add(trajet);
            }
        }
        return trajets;
    }
}
