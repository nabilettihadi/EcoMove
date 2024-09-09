package main.java.eco.services;

import main.java.eco.dao.PartenaireDAO;
import main.java.eco.models.Partenaire;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PartenaireService {
    private final PartenaireDAO partenaireDAO;

    public PartenaireService() throws SQLException {
        this.partenaireDAO = new PartenaireDAO();
    }

    public void createPartenaire(Partenaire partenaire) {
        try {
            partenaireDAO.addPartenaire(partenaire);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du partenaire: " + e.getMessage());
        }
    }

    public void updatePartenaire(UUID id, Partenaire partenaire) {
        try {
            partenaireDAO.updatePartenaire(id, partenaire);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du partenaire: " + e.getMessage());
        }
    }

    public void deletePartenaire(UUID id) {
        try {
            partenaireDAO.deletePartenaire(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du partenaire: " + e.getMessage());
        }
    }

    public Partenaire getPartenaireById(UUID id) {
        try {
            return partenaireDAO.getPartenaire(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du partenaire: " + e.getMessage());
            return null;
        }
    }

    public List<Partenaire> getAllPartenaires() {
        try {
            return partenaireDAO.getAllPartenaires();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des partenaires: " + e.getMessage());
            return null;
        }
    }
}
