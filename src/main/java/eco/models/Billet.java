// Billet.java
package main.java.eco.models;

import main.java.eco.enums.StatutBillet;
import main.java.eco.enums.TypeTransport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Billet {
    private UUID id;
    private Contrat contrat;
    private TypeTransport typeTransport;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private Date dateVente;
    private StatutBillet statutBillet;
    private Trajet trajet;

    public Billet(UUID id, Contrat contrat, TypeTransport typeTransport, BigDecimal prixAchat, BigDecimal prixVente,
                  Date dateVente, StatutBillet statutBillet, Trajet trajet) {
        this.id = id;
        this.contrat = contrat;
        this.typeTransport = typeTransport;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.dateVente = dateVente;
        this.statutBillet = statutBillet;
        this.trajet = trajet;
    }

    // Getters et setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public StatutBillet getStatutBillet() {
        return statutBillet;
    }

    public void setStatutBillet(StatutBillet statutBillet) {
        this.statutBillet = statutBillet;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", contrat=" + contrat +
                ", typeTransport=" + typeTransport +
                ", prixAchat=" + prixAchat +
                ", prixVente=" + prixVente +
                ", dateVente=" + dateVente +
                ", statutBillet=" + statutBillet +
                ", trajet=" + trajet +
                '}';
    }
}
