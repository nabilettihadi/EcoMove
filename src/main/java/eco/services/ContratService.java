package main.java.eco.services;

import main.java.eco.dao.ContratDAO;
import main.java.eco.dao.PartenaireDAO;
import main.java.eco.models.Contrat;
import main.java.eco.models.Partenaire;
import main.java.eco.enums.StatutContrat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class ContratService {

    private final ContratDAO contratDAO;
    private final PartenaireDAO partenaireDAO;

    public ContratService() throws SQLException {
        this.contratDAO = new ContratDAO();
        this.partenaireDAO = new PartenaireDAO();
    }

    public void addContrat(UUID id, Date dateDebut, Date dateFin, BigDecimal tarifSpecial, String conditionsAccord, boolean renouvelable, StatutContrat statutContrat, UUID partenaireId) throws SQLException {
        // Validate input
        if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        // Fetch partenaire from PartenaireDAO
        Partenaire partenaire = partenaireDAO.getPartenaire(partenaireId);
        if (partenaire == null) {
            throw new IllegalArgumentException("Partenaire non trouvé avec ID : " + partenaireId);
        }

        Contrat contrat = new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat, partenaire);
        contratDAO.addContrat(contrat);
    }

    public void updateContrat(UUID id, Date dateDebut, Date dateFin, BigDecimal tarifSpecial, String conditionsAccord, boolean renouvelable, StatutContrat statutContrat, UUID partenaireId) throws SQLException {
        // Validate input
        if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        // Fetch partenaire from PartenaireDAO
        Partenaire partenaire = partenaireDAO.getPartenaire(partenaireId);
        if (partenaire == null) {
            throw new IllegalArgumentException("Partenaire non trouvé avec ID : " + partenaireId);
        }

        Contrat contrat = new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat, partenaire);
        contratDAO.updateContrat(contrat);
    }

    public void deleteContrat(UUID id) throws SQLException {
        contratDAO.deleteContrat(id);
    }

    public Contrat getContrat(UUID id) throws SQLException {
        return contratDAO.getContrat(id);
    }

    public List<Contrat> getAllContrats() throws SQLException {
        return contratDAO.getAllContrats();
    }

    // Additional service methods as needed
}
