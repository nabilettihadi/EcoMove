package main.java.eco.dao;

import main.java.eco.model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (client_id, ville_depart, ville_destination, date_depart, transporteur, horaire, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reservation.getClientId());
            stmt.setString(2, reservation.getVilleDepart());
            stmt.setString(3, reservation.getVilleDestination());
            stmt.setDate(4, Date.valueOf(reservation.getDateDepart()));
            stmt.setString(5, reservation.getTransporteur());
            stmt.setString(6, reservation.getHoraire());
            stmt.setDouble(7, reservation.getPrix());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reservation.setId(rs.getInt(1));
            }
        }
    }

    public List<Reservation> getReservationsByClientId(int clientId) throws SQLException {
        String query = "SELECT * FROM reservations WHERE client_id = ?";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
        }
        return reservations;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation reservation = mapRowToReservation(rs);
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setClientId(rs.getInt("client_id"));
        reservation.setVilleDepart(rs.getString("ville_depart"));
        reservation.setVilleDestination(rs.getString("ville_destination"));
        reservation.setDateDepart(rs.getDate("date_depart").toLocalDate());
        reservation.setTransporteur(rs.getString("transporteur"));
        reservation.setHoraire(rs.getString("horaire"));
        reservation.setPrix(rs.getDouble("prix"));
        return reservation;
    }
}
