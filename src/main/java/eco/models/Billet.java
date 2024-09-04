package main.java.eco.models;

import main.java.eco.models.enums.StatutBillet;
import main.java.eco.models.enums.TypeTransport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Billet {
    private UUID id;
    private UUID idContrat; // Ajout de l'attribut idContrat
    private TypeTransport typeTransport;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private Date dateVente;
    private StatutBillet statutBillet;

    public Billet(UUID id, UUID idContrat, TypeTransport typeTransport, BigDecimal prixAchat, BigDecimal prixVente,
                  Date dateVente, StatutBillet statutBillet) {
        this.id = id;
        this.idContrat = idContrat;
        this.typeTransport = typeTransport;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.dateVente = dateVente;
        this.statutBillet = statutBillet;
    }

    // Getters et setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(UUID idContrat) {
        this.idContrat = idContrat;
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
}
