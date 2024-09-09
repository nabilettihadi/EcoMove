package main.java.eco.models;

import main.java.eco.enums.TypeTransport;
import main.java.eco.enums.StatutPartenaire;

import java.util.Date;
import java.util.UUID;

public class Partenaire {
    private final UUID id;
    private String nomCompagnie;
    private String contactCommercial;
    private TypeTransport typeTransport;
    private String zoneGeographique;
    private String conditionsSpeciales;
    private StatutPartenaire statutPartenaire;
    private Date dateCreation;

    public Partenaire(UUID id, String nomCompagnie, String contactCommercial, TypeTransport typeTransport,
                      String zoneGeographique, String conditionsSpeciales, StatutPartenaire statutPartenaire, Date dateCreation) {
        this.id = id;
        this.nomCompagnie = nomCompagnie;
        this.contactCommercial = contactCommercial;
        this.typeTransport = typeTransport;
        this.zoneGeographique = zoneGeographique;
        this.conditionsSpeciales = conditionsSpeciales;
        this.statutPartenaire = statutPartenaire;
        this.dateCreation = dateCreation;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public String getContactCommercial() {
        return contactCommercial;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public String getZoneGeographique() {
        return zoneGeographique;
    }

    public String getConditionsSpeciales() {
        return conditionsSpeciales;
    }

    public StatutPartenaire getStatutPartenaire() {
        return statutPartenaire;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    // Setters
    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public void setContactCommercial(String contactCommercial) {
        this.contactCommercial = contactCommercial;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public void setZoneGeographique(String zoneGeographique) {
        this.zoneGeographique = zoneGeographique;
    }

    public void setConditionsSpeciales(String conditionsSpeciales) {
        this.conditionsSpeciales = conditionsSpeciales;
    }

    public void setStatutPartenaire(StatutPartenaire statutPartenaire) {
        this.statutPartenaire = statutPartenaire;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Partenaire{" +
                "id=" + id +
                ", nomCompagnie='" + nomCompagnie + '\'' +
                ", contactCommercial='" + contactCommercial + '\'' +
                ", typeTransport=" + typeTransport +
                ", zoneGeographique='" + zoneGeographique + '\'' +
                ", conditionsSpeciales='" + conditionsSpeciales + '\'' +
                ", statutPartenaire=" + statutPartenaire +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
