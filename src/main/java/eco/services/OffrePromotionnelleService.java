package main.java.eco.services;

import main.java.eco.dao.OffrePromotionnelleDAO;
import main.java.eco.models.OffrePromotionnelle;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OffrePromotionnelleService {
    private final OffrePromotionnelleDAO offreDAO;

    public OffrePromotionnelleService() throws SQLException {
        this.offreDAO = new OffrePromotionnelleDAO();
    }

    public void addOffrePromotionnelle(OffrePromotionnelle offre) {
        try {
            offreDAO.addOffrePromotionnelle(offre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'offre promotionnelle: " + e.getMessage());
        }
    }

    public void updateOffrePromotionnelle(OffrePromotionnelle offre) {
        try {
            offreDAO.updateOffrePromotionnelle(offre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'offre promotionnelle: " + e.getMessage());
        }
    }

    public void deleteOffrePromotionnelle(UUID id) {
        try {
            offreDAO.deleteOffrePromotionnelle(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'offre promotionnelle: " + e.getMessage());
        }
    }

    public OffrePromotionnelle getOffrePromotionnelle(UUID id) {
        try {
            return offreDAO.getOffrePromotionnelle(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'offre promotionnelle: " + e.getMessage());
            return null;
        }
    }

    public List<OffrePromotionnelle> getAllOffresPromotionnelles() {
        try {
            return offreDAO.getAllOffresPromotionnelles();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des offres promotionnelles: " + e.getMessage());
            return null;
        }
    }
}
