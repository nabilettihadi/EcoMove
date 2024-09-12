package main.java.eco.models;

import main.java.eco.enums.TypeTransport;

import java.time.LocalDateTime;

public class Trajet {
    private int id;
    private int villeDepartId;
    private int villeArriveeId;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private String description;
    private TypeTransport typeTransport;

    public Trajet(int villeDepartId, int villeArriveeId, LocalDateTime dateDepart, LocalDateTime dateArrivee, String description, TypeTransport typeTransport) {
        this.villeDepartId = villeDepartId;
        this.villeArriveeId = villeArriveeId;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.description = description;
        this.typeTransport = typeTransport;
    }

    public Trajet(int id, int villeDepartId, int villeArriveeId, LocalDateTime dateDepart, LocalDateTime dateArrivee, String description, TypeTransport typeTransport) {
        this.id = id;
        this.villeDepartId = villeDepartId;
        this.villeArriveeId = villeArriveeId;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.description = description;
        this.typeTransport = typeTransport;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVilleDepartId() {
        return villeDepartId;
    }

    public void setVilleDepartId(int villeDepartId) {
        this.villeDepartId = villeDepartId;
    }

    public int getVilleArriveeId() {
        return villeArriveeId;
    }

    public void setVilleArriveeId(int villeArriveeId) {
        this.villeArriveeId = villeArriveeId;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDateTime getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    @Override
    public String toString() {
        return "Trajet{" +
                "id=" + id +
                ", villeDepartId=" + villeDepartId +
                ", villeArriveeId=" + villeArriveeId +
                ", dateDepart=" + dateDepart +
                ", dateArrivee=" + dateArrivee +
                ", description='" + description + '\'' +
                ", typeTransport=" + typeTransport +
                '}';
    }
}
