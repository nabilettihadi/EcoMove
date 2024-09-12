package main.java.eco.views;

import main.java.eco.models.Trajet;
import main.java.eco.services.TrajetService;
import main.java.eco.enums.TypeTransport;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TrajetView {
    private final TrajetService trajetService;
    private final Scanner scanner;

    public TrajetView() throws SQLException {
        this.trajetService = new TrajetService();
        this.scanner = new Scanner(System.in);
    }

    public void displayTrajetMenu() throws SQLException {
        while (true) {
            System.out.println("Gestion des trajets");
            System.out.println("1. Ajouter un trajet");
            System.out.println("2. Modifier un trajet");
            System.out.println("3. Supprimer un trajet");
            System.out.println("4. Afficher tous les trajets");
            System.out.println("5. Rechercher des trajets");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
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
                    afficherTousLesTrajets();
                    break;
                case 5:
                    rechercherTrajets();
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterTrajet() throws SQLException {
        try {
            System.out.print("Ville de départ ID : ");
            int villeDepartId = scanner.nextInt();

            System.out.print("Ville d'arrivée ID : ");
            int villeArriveeId = scanner.nextInt();

            System.out.print("Date de départ (yyyy-MM-dd HH:mm:ss) : ");
            LocalDateTime dateDepart = LocalDateTime.parse(scanner.next());

            System.out.print("Date d'arrivée (yyyy-MM-dd HH:mm:ss) : ");
            LocalDateTime dateArrivee = LocalDateTime.parse(scanner.next());

            System.out.print("Description : ");
            String description = scanner.next();

            System.out.print("Type de transport (0 pour BUS, 1 pour TRAIN, 2 pour AVION) : ");
            TypeTransport typeTransport = TypeTransport.values()[scanner.nextInt()];

            Trajet trajet = new Trajet(villeDepartId, villeArriveeId, dateDepart, dateArrivee, description, typeTransport);
            trajetService.addTrajet(trajet);

            System.out.println("Trajet ajouté avec succès !");
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Format de date invalide ou type de transport incorrect.");
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez entrer des valeurs numériques correctes.");
        }
    }

    private void modifierTrajet() throws SQLException {
        System.out.print("ID du trajet à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Trajet trajet = trajetService.getTrajetById(id);
        if (trajet == null) {
            System.out.println("Trajet non trouvé.");
            return;
        }

        System.out.print("Nouvelle ville de départ ID (laisser vide pour conserver l'ancienne) : ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setVilleDepartId(Integer.parseInt(input));
        }

        System.out.print("Nouvelle ville d'arrivée ID (laisser vide pour conserver l'ancienne) : ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setVilleArriveeId(Integer.parseInt(input));
        }

        System.out.print("Nouvelle date de départ (yyyy-MM-dd HH:mm:ss, laisser vide pour conserver l'ancienne) : ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setDateDepart(LocalDateTime.parse(input));
        }

        System.out.print("Nouvelle date d'arrivée (yyyy-MM-dd HH:mm:ss, laisser vide pour conserver l'ancienne) : ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setDateArrivee(LocalDateTime.parse(input));
        }

        System.out.print("Nouvelle description (laisser vide pour conserver l'ancienne) : ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setDescription(input);
        }

        System.out.print("Nouveau type de transport (0 pour BUS, 1 pour TRAIN, 2 pour AVION, laisser vide pour conserver l'ancienne) : ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            trajet.setTypeTransport(TypeTransport.values()[Integer.parseInt(input)]);
        }

        trajetService.updateTrajet(trajet);
        System.out.println("Trajet modifié avec succès !");
    }

    private void supprimerTrajet() throws SQLException {
        System.out.print("ID du trajet à supprimer : ");
        int id = scanner.nextInt();
        trajetService.deleteTrajet(id);
        System.out.println("Trajet supprimé avec succès !");
    }

    private void afficherTousLesTrajets() throws SQLException {
        List<Trajet> trajets = trajetService.getAllTrajets();
        for (Trajet trajet : trajets) {
            System.out.println(trajet);
        }
    }

    private void rechercherTrajets() throws SQLException {
        System.out.print("ID de la ville de départ : ");
        int villeDepartId = scanner.nextInt();

        System.out.print("ID de la ville d'arrivée : ");
        int villeArriveeId = scanner.nextInt();

        List<Trajet> trajets = trajetService.findTrajets(villeDepartId, villeArriveeId);
        for (Trajet trajet : trajets) {
            System.out.println(trajet);
        }
    }
}
