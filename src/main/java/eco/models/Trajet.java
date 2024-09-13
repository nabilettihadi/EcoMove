package main.java.eco.models;

import main.java.eco.enums.TypeTransport;

import java.time.LocalDateTime;
import java.util.List;

public class Trajet {
    private int id;
    private int villeDepartId;
    private int villeArriveeId;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private TypeTransport transporteur; // Renommé ici
    private List<Billet> billets;

    public Trajet(int villeDepartId, int villeArriveeId, LocalDateTime dateDepart, LocalDateTime dateArrivee, TypeTransport transporteur) {
        this.villeDepartId = villeDepartId;
        this.villeArriveeId = villeArriveeId;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.transporteur = transporteur; // Renommé ici
    }

    public Trajet(int id, int villeDepartId, int villeArriveeId, LocalDateTime dateDepart, LocalDateTime dateArrivee, TypeTransport transporteur) {
        this.id = id;
        this.villeDepartId = villeDepartId;
        this.villeArriveeId = villeArriveeId;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.transporteur = transporteur; // Renommé ici
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

    public TypeTransport getTransporteur() { // Renommé ici
        return transporteur;
    }

    public void setTransporteur(TypeTransport transporteur) { // Renommé ici
        this.transporteur = transporteur;
    }

    public List<Billet> getBillets() {
        return billets;
    }

    public void setBillets(List<Billet> billets) {
        this.billets = billets;
    }


@Override
    public String toString() {
        return "Trajet{" +
                "id=" + id +
                ", villeDepartId=" + villeDepartId +
                ", villeArriveeId=" + villeArriveeId +
                ", dateDepart=" + dateDepart +
                ", dateArrivee=" + dateArrivee +
                ", transporteur=" + transporteur +
                ", billets=" + billets +
                '}';
    }
}
