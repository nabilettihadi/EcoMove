package main.java.eco.models;

import main.java.eco.enums.StatutContrat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

public class Contrat {
    private UUID id;
    private Date dateDebut;
    private Date dateFin;
    private BigDecimal tarifSpecial;
    private String conditionsAccord;
    private boolean renouvelable;
    private StatutContrat statutContrat;
    private Partenaire partenaire;

    public Contrat(UUID id, Date dateDebut, Date dateFin, BigDecimal tarifSpecial,
                   String conditionsAccord, boolean renouvelable,
                   StatutContrat statutContrat, Partenaire partenaire) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.tarifSpecial = tarifSpecial;
        this.conditionsAccord = conditionsAccord;
        this.renouvelable = renouvelable;
        this.statutContrat = statutContrat;
        this.partenaire = partenaire;
    }

    // Getters et Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public BigDecimal getTarifSpecial() {
        return tarifSpecial;
    }

    public void setTarifSpecial(BigDecimal tarifSpecial) {
        this.tarifSpecial = tarifSpecial;
    }

    public String getConditionsAccord() {
        return conditionsAccord;
    }

    public void setConditionsAccord(String conditionsAccord) {
        this.conditionsAccord = conditionsAccord;
    }

    public boolean isRenouvelable() {
        return renouvelable;
    }

    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }

    public StatutContrat getStatutContrat() {
        return statutContrat;
    }

    public void setStatutContrat(StatutContrat statutContrat) {
        this.statutContrat = statutContrat;
    }

    public Partenaire getPartenaire() {
        return partenaire; // Retourne l'objet Partenaire
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", tarifSpecial=" + tarifSpecial +
                ", conditionsAccord='" + conditionsAccord + '\'' +
                ", renouvelable=" + renouvelable +
                ", statutContrat=" + statutContrat +
                ", partenaire=" + partenaire +
                '}';
    }
}
