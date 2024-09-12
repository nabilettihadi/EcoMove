package main.java.eco.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.enums.TypeTransport;
import main.java.eco.models.Trajet;

public class TrajetDAO {
    private Connection connection;

    public TrajetDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // CREATE
    public void addTrajet(Trajet trajet) throws SQLException {
        String query = "INSERT INTO trajet (ville_depart_id, ville_arrivee_id, date_depart, date_arrivee, description, type_transport) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trajet.getVilleDepartId());
            pstmt.setInt(2, trajet.getVilleArriveeId());
            pstmt.setTimestamp(3, Timestamp.valueOf(trajet.getDateDepart()));
            pstmt.setTimestamp(4, Timestamp.valueOf(trajet.getDateArrivee()));
            pstmt.setString(5, trajet.getDescription());
            pstmt.setObject(6, trajet.getTypeTransport().name(), Types.OTHER);
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
                    TypeTransport typeTransport = TypeTransport.valueOf(rs.getString("type_transport"));
                    return new Trajet(
                            rs.getInt("id"),
                            rs.getInt("ville_depart_id"),
                            rs.getInt("ville_arrivee_id"),
                            rs.getTimestamp("date_depart").toLocalDateTime(),
                            rs.getTimestamp("date_arrivee").toLocalDateTime(),
                            rs.getString("description"),
                            typeTransport
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void updateTrajet(Trajet trajet) throws SQLException {
        String query = "UPDATE trajet SET ville_depart_id = ?, ville_arrivee_id = ?, date_depart = ?, date_arrivee = ?, description = ?, type_transport = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, trajet.getVilleDepartId());
            pstmt.setInt(2, trajet.getVilleArriveeId());
            pstmt.setTimestamp(3, Timestamp.valueOf(trajet.getDateDepart()));
            pstmt.setTimestamp(4, Timestamp.valueOf(trajet.getDateArrivee()));
            pstmt.setString(5, trajet.getDescription());
            pstmt.setObject(6, trajet.getTypeTransport().name(), Types.OTHER);
            pstmt.setInt(7, trajet.getId());
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

    // GET ALL
    public List<Trajet> getAllTrajets() throws SQLException {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM trajet";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                TypeTransport typeTransport = TypeTransport.valueOf(rs.getString("type_transport"));
                Trajet trajet = new Trajet(
                        rs.getInt("id"),
                        rs.getInt("ville_depart_id"),
                        rs.getInt("ville_arrivee_id"),
                        rs.getTimestamp("date_depart").toLocalDateTime(),
                        rs.getTimestamp("date_arrivee").toLocalDateTime(),
                        rs.getString("description"),
                        typeTransport
                );
                trajets.add(trajet);
            }
        }
        return trajets;
    }

    // SEARCH
    public List<Trajet> findTrajets(int villeDepartId, int villeArriveeId) throws SQLException {
        List<Trajet> trajets = new ArrayList<>();
        String query = "SELECT * FROM trajet WHERE ville_depart_id = ? AND ville_arrivee_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, villeDepartId);
            pstmt.setInt(2, villeArriveeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TypeTransport typeTransport = TypeTransport.valueOf(rs.getString("type_transport").toUpperCase());
                    Trajet trajet = new Trajet(
                            rs.getInt("id"),
                            rs.getInt("ville_depart_id"),
                            rs.getInt("ville_arrivee_id"),
                            rs.getTimestamp("date_depart").toLocalDateTime(),
                            rs.getTimestamp("date_arrivee").toLocalDateTime(),
                            rs.getString("description"),
                            typeTransport
                    );
                    trajets.add(trajet);
                }
            }
        }
        return trajets;
    }

}
