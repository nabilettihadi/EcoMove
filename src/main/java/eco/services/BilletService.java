package main.java.eco.services;

import main.java.eco.dao.BilletDAO;
import main.java.eco.models.Billet;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BilletService {
    private final BilletDAO billetDAO;

    public BilletService() throws SQLException {
        this.billetDAO = new BilletDAO();
    }

    public void createBillet(Billet billet) {
        try {
            billetDAO.addBillet(billet);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du billet: " + e.getMessage());
        }
    }

    public void updateBillet(Billet billet) {
        try {
            billetDAO.updateBillet(billet);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du billet: " + e.getMessage());
        }
    }

    public void deleteBillet(UUID id) {
        try {
            billetDAO.deleteBillet(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du billet: " + e.getMessage());
        }
    }

    public Billet getBilletById(UUID id) {
        try {
            return billetDAO.getBillet(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du billet: " + e.getMessage());
            return null;
        }
    }

    public List<Billet> getAllBillets() {
        try {
            return billetDAO.getAllBillets();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des billets: " + e.getMessage());
            return null;
        }
    }
}
