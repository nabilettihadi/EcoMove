package main.java.eco.models;

import main.java.eco.models.enums.TypeTransport;
import main.java.eco.models.enums.StatutPartenaire;

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

    // Getters et setters


}
