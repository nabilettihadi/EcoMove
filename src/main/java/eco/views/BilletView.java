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
            System.out.println("=== Suivi des Billets Partenaires ===");
            System.out.println("1. Ajouter un Billet");
            System.out.println("2. Modifier un Billet");
            System.out.println("3. Supprimer un Billet");
            System.out.println("4. Afficher les Billets");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();

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
                    UUID idModifier = UUID.fromString(scanner.next());
                    try {
                        billetService.updateBillet(saisirBillet(idModifier));
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la modification du billet : " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du Billet à supprimer (UUID): ");
                    UUID idSupprimer = UUID.fromString(scanner.next());
                    try {
                        billetService.deleteBillet(idSupprimer);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la suppression du billet : " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        billetService.getAllBillets().forEach(System.out::println);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'affichage des billets : " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private Billet saisirBillet() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID (UUID): ");
        UUID id = UUID.fromString(scanner.next());

        System.out.print("Type de transport (ex: BUS, TRAIN): ");
        String typeTransport = scanner.next();

        System.out.print("Prix d'achat: ");
        BigDecimal prixAchat = scanner.nextBigDecimal();

        System.out.print("Prix de vente: ");
        BigDecimal prixVente = scanner.nextBigDecimal();

        System.out.print("Date de vente (YYYY-MM-DD): ");
        Date dateVente;
        try {
            dateVente = dateFormat.parse(scanner.next());
        } catch (ParseException e) {
            dateVente = new Date();
            System.out.println("Format de date invalide, valeur par défaut utilisée.");
        }

        System.out.print("Statut (VALID/INVALID): ");
        String statut = scanner.next().toUpperCase();

        return new Billet(id, TypeTransport.valueOf(typeTransport), prixAchat, prixVente, dateVente, StatutBillet.valueOf(statut));
    }

    private Billet saisirBillet(UUID id) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Type de transport (ex: BUS, TRAIN): ");
        String typeTransport = scanner.next();

        System.out.print("Prix d'achat: ");
        BigDecimal prixAchat = scanner.nextBigDecimal();

        System.out.print("Prix de vente: ");
        BigDecimal prixVente = scanner.nextBigDecimal();

        System.out.print("Date de vente (YYYY-MM-DD): ");
        Date dateVente;
        try {
            dateVente = dateFormat.parse(scanner.next());
        } catch (ParseException e) {
            dateVente = new Date();
            System.out.println("Format de date invalide, valeur par défaut utilisée.");
        }

        System.out.print("Statut (VALID/INVALID): ");
        String statut = scanner.next().toUpperCase();

        return new Billet(id, TypeTransport.valueOf(typeTransport), prixAchat, prixVente, dateVente, StatutBillet.valueOf(statut));
    }
}
