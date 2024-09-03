package main.java.eco.models;

import main.java.eco.models.enums.StatutOffre;
import main.java.eco.models.enums.TypeReduction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class OffrePromotionnelle {
    private UUID id;
    private String nomOffre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private TypeReduction typeReduction;
    private BigDecimal valeurReduction;
    private String conditions;
    private StatutOffre statutOffre;

    public OffrePromotionnelle(UUID id) {
        this.id = id;
    }

    public OffrePromotionnelle(UUID id, String nomOffre, String description, Date dateDebut, Date dateFin,
                               TypeReduction typeReduction, BigDecimal valeurReduction, String conditions, StatutOffre statutOffre) {
        this.id = id;
        this.nomOffre = nomOffre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.typeReduction = typeReduction;
        this.valeurReduction = valeurReduction;
        this.conditions = conditions;
        this.statutOffre = statutOffre;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TypeReduction getTypeReduction() {
        return typeReduction;
    }

    public void setTypeReduction(TypeReduction typeReduction) {
        this.typeReduction = typeReduction;
    }

    public BigDecimal getValeurReduction() {
        return valeurReduction;
    }

    public void setValeurReduction(BigDecimal valeurReduction) {
        this.valeurReduction = valeurReduction;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public StatutOffre getStatutOffre() {
        return statutOffre;
    }

    public void setStatutOffre(StatutOffre statutOffre) {
        this.statutOffre = statutOffre;
    }

    @Override
    public String toString() {
        return "OffrePromotionnelle{" +
                "id=" + id +
                ", nomOffre='" + nomOffre + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", typeReduction=" + typeReduction +
                ", valeurReduction=" + valeurReduction +
                ", conditions='" + conditions + '\'' +
                ", statutOffre=" + statutOffre +
                '}';
    }
}
