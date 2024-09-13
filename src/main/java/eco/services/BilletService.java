package main.java.eco.services;

import main.java.eco.dao.BilletDAO;
import main.java.eco.models.Billet;
import main.java.eco.models.Trajet;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BilletService {

    private final BilletDAO billetDAO;

    public BilletService() throws SQLException {
        this.billetDAO = new BilletDAO();
    }

    public void addBillet(Billet billet) throws SQLException {
        billetDAO.addBillet(billet);
    }

    public void updateBillet(Billet billet) throws SQLException {
        billetDAO.updateBillet(billet);
    }

    public void deleteBillet(UUID id) throws SQLException {
        billetDAO.deleteBillet(id);
    }

    public Billet getBillet(UUID id) throws SQLException {
        return billetDAO.getBillet(id);
    }

    public List<Billet> getAllBillets() throws SQLException {
        return billetDAO.getAllBillets();
    }


    public List<Billet> findBilletsByTrajet(int trajetId) throws SQLException {
        return billetDAO.findBilletsByTrajet(trajetId);
    }
}
