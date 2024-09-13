package main.java.eco.views;

import main.java.eco.services.TrajetService;
import main.java.eco.models.Trajet;
import main.java.eco.enums.TypeTransport;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TrajetView {
    private TrajetService trajetService;
    private Scanner scanner;

    public TrajetView() throws SQLException {
        this.trajetService = new TrajetService();
        this.scanner = new Scanner(System.in);
    }

    public void displayTrajetMenu() {
        while (true) {
            System.out.println("1. Ajouter un trajet");
            System.out.println("2. Modifier un trajet");
            System.out.println("3. Supprimer un trajet");
            System.out.println("4. Afficher tous les trajets");
            System.out.println("5. Rechercher des trajets");
            System.out.println("0. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    ajouterTrajet();
                    break;
                case 2:
                    modifierTrajet();
                    break;
                case 3:
                    supprimerTrajet();
                    break;
                case 4:
                    afficherTousTrajets();
                    break;
                case 5:
                    trouverTrajets();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterTrajet() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.print("Ville de départ (ID) : ");
        int villeDepartId = scanner.nextInt();
        System.out.print("Ville d'arrivée (ID) : ");
        int villeArriveeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Date de départ (yyyy-MM-dd HH:mm:ss) : ");
        LocalDateTime dateDepart = LocalDateTime.parse(scanner.nextLine(), formatter);
        System.out.print("Date d'arrivée (yyyy-MM-dd HH:mm:ss) : ");
        LocalDateTime dateArrivee = LocalDateTime.parse(scanner.nextLine(), formatter);

        System.out.print("Transporteur (e.g., BUS, TRAIN) : ");
        TypeTransport transporteur = TypeTransport.valueOf(scanner.nextLine().toUpperCase());

        Trajet trajet = new Trajet(villeDepartId, villeArriveeId, dateDepart, dateArrivee, transporteur);
        try {
            trajetService.addTrajet(trajet);
            System.out.println("Trajet ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du trajet : " + e.getMessage());
        }
    }

    private void modifierTrajet() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.print("ID du trajet à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouvelle ville de départ (ID) : ");
        int villeDepartId = scanner.nextInt();
        System.out.print("Nouvelle ville d'arrivée (ID) : ");
        int villeArriveeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouvelle date de départ (yyyy-MM-dd HH:mm:ss) : ");
        LocalDateTime dateDepart = LocalDateTime.parse(scanner.nextLine(), formatter);
        System.out.print("Nouvelle date d'arrivée (yyyy-MM-dd HH:mm:ss) : ");
        LocalDateTime dateArrivee = LocalDateTime.parse(scanner.nextLine(), formatter);

        System.out.print("Nouveau transporteur (e.g., BUS, TRAIN) : ");
        TypeTransport transporteur = TypeTransport.valueOf(scanner.nextLine().toUpperCase());

        Trajet trajet = new Trajet(id, villeDepartId, villeArriveeId, dateDepart, dateArrivee, transporteur);
        try {
            trajetService.updateTrajet(trajet);
            System.out.println("Trajet modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du trajet : " + e.getMessage());
        }
    }

    private void supprimerTrajet() {
        System.out.print("ID du trajet à supprimer : ");
        int id = scanner.nextInt();

        try {
            trajetService.deleteTrajet(id);
            System.out.println("Trajet supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du trajet : " + e.getMessage());
        }
    }

    private void afficherTousTrajets() {
        try {
            List<Trajet> trajets = trajetService.getAllTrajets();
            for (Trajet trajet : trajets) {
                System.out.println("ID: " + trajet.getId() +
                        ", Ville de départ ID: " + trajet.getVilleDepartId() +
                        ", Ville d'arrivée ID: " + trajet.getVilleArriveeId() +
                        ", Date de départ: " + trajet.getDateDepart() +
                        ", Date d'arrivée: " + trajet.getDateArrivee() +
                        ", Transporteur: " + trajet.getTransporteur());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des trajets : " + e.getMessage());
        }
    }

    private void trouverTrajets() {
        System.out.print("Ville de départ (ID) : ");
        int villeDepartId = scanner.nextInt();
        System.out.print("Ville d'arrivée (ID) : ");
        int villeArriveeId = scanner.nextInt();

        try {
            List<List<Trajet>> chemins = trajetService.findTrajets(villeDepartId, villeArriveeId);
            if (chemins.isEmpty()) {
                System.out.println("Aucun trajet trouvé.");
            } else {
                System.out.println("Trajets trouvés :");
                for (List<Trajet> chemin : chemins) {
                    for (Trajet trajet : chemin) {
                        System.out.println("ID: " + trajet.getId() +
                                ", Ville de départ ID: " + trajet.getVilleDepartId() +
                                ", Ville d'arrivée ID: " + trajet.getVilleArriveeId() +
                                ", Date de départ: " + trajet.getDateDepart() +
                                ", Date d'arrivée: " + trajet.getDateArrivee() +
                                ", Transporteur: " + trajet.getTransporteur());
                    }
                    System.out.println("-----");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de trajets: " + e.getMessage());
        }
    }
}
