package main.java.eco.services;

import main.java.eco.dao.TrajetDAO;
import main.java.eco.models.Trajet;

import java.sql.SQLException;
import java.util.List;

public class TrajetService {
    private final TrajetDAO trajetDAO;

    public TrajetService() throws SQLException {
        this.trajetDAO = new TrajetDAO();
    }

    public void addTrajet(Trajet trajet) throws SQLException {
        trajetDAO.addTrajet(trajet);
    }

    public void updateTrajet(Trajet trajet) throws SQLException {
        trajetDAO.updateTrajet(trajet);
    }

    public void deleteTrajet(int id) throws SQLException {
        trajetDAO.deleteTrajet(id);
    }

    public Trajet getTrajetById(int id) throws SQLException {
        return trajetDAO.getTrajetById(id);
    }

    public List<Trajet> getAllTrajets() throws SQLException {
        return trajetDAO.getAllTrajets();
    }

    public List<Trajet> findTrajets(int villeDepartId, int villeArriveeId) throws SQLException {
        return trajetDAO.findTrajets(villeDepartId, villeArriveeId);
    }
}
