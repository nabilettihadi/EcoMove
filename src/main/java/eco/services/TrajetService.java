package main.java.eco.services;

import main.java.eco.dao.TrajetDAO;
import main.java.eco.models.Trajet;

import java.sql.SQLException;
import java.util.*;

public class TrajetService {
    private TrajetDAO trajetDAO;

    public TrajetService() throws SQLException {
        this.trajetDAO = new TrajetDAO();
    }

    public void addTrajet(Trajet trajet) throws SQLException {
        trajetDAO.addTrajet(trajet);
    }

    public Trajet getTrajetById(int id) throws SQLException {
        return trajetDAO.getTrajetById(id);
    }

    public void updateTrajet(Trajet trajet) throws SQLException {
        trajetDAO.updateTrajet(trajet);
    }

    public void deleteTrajet(int id) throws SQLException {
        trajetDAO.deleteTrajet(id);
    }

    public List<Trajet> getAllTrajets() throws SQLException {
        return trajetDAO.getAllTrajets();
    }

    public List<List<Trajet>> findTrajets(int villeDepartId, int villeArriveeId) throws SQLException {
        List<Trajet> allTrajets = trajetDAO.getAllTrajets();

        List<List<Trajet>> chemins = new ArrayList<>();
        findPaths(villeDepartId, villeArriveeId, allTrajets, new ArrayList<>(), chemins);

        return chemins;
    }

    private void findPaths(int currentCityId, int destinationCityId, List<Trajet> trajets,
                           List<Trajet> currentPath, List<List<Trajet>> chemins) {
        if (currentCityId == destinationCityId) {
            chemins.add(new ArrayList<>(currentPath));
            return;
        }

        for (Trajet trajet : trajets) {
            if (trajet.getVilleDepartId() == currentCityId && !currentPath.contains(trajet)) {
                currentPath.add(trajet);
                findPaths(trajet.getVilleArriveeId(), destinationCityId, trajets, currentPath, chemins);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }
}
