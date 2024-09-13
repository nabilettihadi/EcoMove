package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.enums.StatutReservation;
import main.java.eco.models.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationDAO {
    private final Connection connection;

    public ReservationDAO(Connection connection) throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void createReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (id, client_id, billet_id, date_reservation, statut_reservation) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, reservation.getId());
            stmt.setObject(2, reservation.getClient().getId());
            stmt.setObject(3, reservation.getBillet().getId());
            stmt.setDate(4, Date.valueOf(reservation.getDateReservation()));
            stmt.setObject(5, reservation.getStatutReservation().name().toUpperCase(), Types.OTHER); // Storing as String
            stmt.executeUpdate();
        }
    }

    public Reservation getReservationById(UUID id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToReservation(rs);
                }
            }
        }
        return null;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
        }
        return reservations;
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET client_id = ?, billet_id = ?, date_reservation = ?, statut_reservation = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, reservation.getClient().getId());
            stmt.setObject(2, reservation.getBillet().getId());
            stmt.setDate(3, Date.valueOf(reservation.getDateReservation()));
            stmt.setObject(4, reservation.getStatutReservation().name().toUpperCase(), Types.OTHER); // Storing as String
            stmt.setObject(5, reservation.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteReservation(UUID id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId((UUID) rs.getObject("id"));
        reservation.setClient(new ClientDAO().getClient((UUID) rs.getObject("client_id")));
        reservation.setBillet(new BilletDAO().getBillet((UUID) rs.getObject("billet_id")));
        reservation.setDateReservation(rs.getDate("date_reservation").toLocalDate());
        reservation.setStatutReservation(StatutReservation.valueOf((String) rs.getObject("statut_reservation")));
        return reservation;
    }
}