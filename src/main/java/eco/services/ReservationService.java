package main.java.eco.services;

import main.java.eco.dao.ReservationDAO;
import main.java.eco.models.Reservation;
import main.java.eco.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService() throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        this.reservationDAO = new ReservationDAO(connection);
    }

    public void addReservation(Reservation reservation) throws SQLException {
        reservationDAO.createReservation(reservation);
    }

    public Reservation getReservationById(UUID id) throws SQLException {
        return reservationDAO.getReservationById(id);
    }

    public List<Reservation> getAllReservations() throws SQLException {
        return reservationDAO.getAllReservations();
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        reservationDAO.updateReservation(reservation);
    }

    public void deleteReservation(UUID id) throws SQLException {
        reservationDAO.deleteReservation(id);
    }
}
