package main.java.eco.views;

import main.java.eco.models.Billet;
import main.java.eco.models.enums.StatutBillet;
import main.java.eco.models.enums.TypeTransport;
import main.java.eco.services.BilletService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BilletView {

    private final BilletService billetService = new BilletService();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BilletView() throws SQLException {
    }

    public void displayBilletMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Suivi des Billets ===");
            System.out.println("1. Ajouter un Billet");
            System.out.println("2. Modifier un Billet");
            System.out.println("3. Supprimer un Billet");
            System.out.println("4. Afficher les Billets");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        billetService.addBillet(saisirBillet());
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'ajout du billet : " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Entrez l'ID du Billet à modifier (UUID): ");
                    UUID idModifier = UUID.fromString(scanner.nextLine());
                    try {
                        billetService.updateBillet(saisirBillet(idModifier));
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la modification du billet : " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du Billet à supprimer (UUID): ");
                    UUID idSupprimer = UUID.fromString(scanner.nextLine());
                    try {
                        billetService.deleteBillet(idSupprimer);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la suppression du billet : " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        displayBillets();
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de l'affichage des billets : " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private Billet saisirBillet() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez l'ID du Contrat (UUID): ");
        UUID idContrat = UUID.fromString(scanner.nextLine());

        // Choix du Type de Transport
        System.out.println("Choisissez le Type de Transport :");
        System.out.println("1. AVION");
        System.out.println("2. TRAIN");
        System.out.println("3. BUS");
        int choixTypeTransport = scanner.nextInt();
        TypeTransport typeTransport;
        switch (choixTypeTransport) {
            case 1:
                typeTransport = TypeTransport.AVION;
                break;
            case 2:
                typeTransport = TypeTransport.TRAIN;
                break;
            case 3:
                typeTransport = TypeTransport.BUS;
                break;
            default:
                System.out.println("Choix invalide. Type de Transport par défaut : AVION.");
                typeTransport = TypeTransport.AVION;
                break;
        }

        System.out.print("Entrez le Prix d'Achat: ");
        BigDecimal prixAchat = scanner.nextBigDecimal();

        System.out.print("Entrez le Prix de Vente: ");
        BigDecimal prixVente = scanner.nextBigDecimal();

        System.out.print("Entrez la Date de Vente (yyyy-MM-dd): ");
        Date dateVente = null;
        try {
            dateVente = dateFormat.parse(scanner.next());
        } catch (ParseException e) {
            System.out.println("Format de date invalide. Utilisez yyyy-MM-dd.");
        }

        // Choix du Statut du Billet
        System.out.println("Choisissez le Statut du Billet :");
        System.out.println("1. VENDU");
        System.out.println("2. ANNULE");
        System.out.println("3. EN_ATTENTE");
        int choixStatutBillet = scanner.nextInt();
        StatutBillet statutBillet;
        switch (choixStatutBillet) {
            case 1:
                statutBillet = StatutBillet.VENDU;
                break;
            case 2:
                statutBillet = StatutBillet.ANNULE;
                break;
            case 3:
                statutBillet = StatutBillet.EN_ATTENTE;
                break;
            default:
                System.out.println("Choix invalide. Statut du Billet par défaut : EN_ATTENTE.");
                statutBillet = StatutBillet.EN_ATTENTE;
                break;
        }

        return new Billet(UUID.randomUUID(), idContrat, typeTransport, prixAchat, prixVente, dateVente, statutBillet);
    }

    private Billet saisirBillet(UUID id) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez l'ID du Contrat (UUID): ");
        UUID idContrat = UUID.fromString(scanner.nextLine());

        // Choix du Type de Transport
        System.out.println("Choisissez le Type de Transport :");
        System.out.println("1. AVION");
        System.out.println("2. TRAIN");
        System.out.println("3. BUS");
        int choixTypeTransport = scanner.nextInt();
        TypeTransport typeTransport;
        switch (choixTypeTransport) {
            case 1:
                typeTransport = TypeTransport.AVION;
                break;
            case 2:
                typeTransport = TypeTransport.TRAIN;
                break;
            case 3:
                typeTransport = TypeTransport.BUS;
                break;
            default:
                System.out.println("Choix invalide. Type de Transport par défaut : AVION.");
                typeTransport = TypeTransport.AVION;
                break;
        }

        System.out.print("Entrez le Prix d'Achat: ");
        BigDecimal prixAchat = scanner.nextBigDecimal();

        System.out.print("Entrez le Prix de Vente: ");
        BigDecimal prixVente = scanner.nextBigDecimal();

        System.out.print("Entrez la Date de Vente (yyyy-MM-dd): ");
        Date dateVente = null;
        try {
            dateVente = dateFormat.parse(scanner.next());
        } catch (ParseException e) {
            System.out.println("Format de date invalide. Utilisez yyyy-MM-dd.");
        }

        // Choix du Statut du Billet
        System.out.println("Choisissez le Statut du Billet :");
        System.out.println("1. VENDU");
        System.out.println("2. ANNULE");
        System.out.println("3. EN_ATTENTE");
        int choixStatutBillet = scanner.nextInt();
        StatutBillet statutBillet;
        switch (choixStatutBillet) {
            case 1:
                statutBillet = StatutBillet.VENDU;
                break;
            case 2:
                statutBillet = StatutBillet.ANNULE;
                break;
            case 3:
                statutBillet = StatutBillet.EN_ATTENTE;
                break;
            default:
                System.out.println("Choix invalide. Statut du Billet par défaut : EN_ATTENTE.");
                statutBillet = StatutBillet.EN_ATTENTE;
                break;
        }

        return new Billet(id, idContrat, typeTransport, prixAchat, prixVente, dateVente, statutBillet);
    }

    private void displayBillets() throws SQLException {
        List<Billet> billets = billetService.getAllBillets();
        System.out.println("=== Liste des Billets ===");
        for (Billet billet : billets) {
            System.out.println("ID: " + billet.getId());
            System.out.println("ID Contrat: " + billet.getIdContrat());
            System.out.println("Type de Transport: " + billet.getTypeTransport());
            System.out.println("Prix d'Achat: " + billet.getPrixAchat());
            System.out.println("Prix de Vente: " + billet.getPrixVente());
            System.out.println("Date de Vente: " + dateFormat.format(billet.getDateVente()));
            System.out.println("Statut du Billet: " + billet.getStatutBillet());
            System.out.println("--------------------------");
        }
    }
}
