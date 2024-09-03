package main.java.eco.views;

import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.models.enums.StatutOffre;
import main.java.eco.models.enums.TypeReduction;
import main.java.eco.services.OffrePromotionnelleService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class OffrePromotionnelleView {

    private final OffrePromotionnelleService offrePromotionnelleService;

    public OffrePromotionnelleView() throws SQLException {
        this.offrePromotionnelleService = new OffrePromotionnelleService();
    }

    public void displayOffreMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Gestion des Offres Promotionnelles ===");
            System.out.println("1. Ajouter une Offre Promotionnelle");
            System.out.println("2. Modifier une Offre Promotionnelle");
            System.out.println("3. Supprimer une Offre Promotionnelle");
            System.out.println("4. Afficher les Offres Promotionnelles");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        offrePromotionnelleService.addOffre(saisirOffre());
                        break;
                    case 2:
                        System.out.print("Entrez l'ID de l'Offre à modifier (UUID): ");
                        UUID idModifier = UUID.fromString(scanner.nextLine());
                        offrePromotionnelleService.updateOffre(idModifier, saisirOffre());
                        break;
                    case 3:
                        System.out.print("Entrez l'ID de l'Offre à supprimer (UUID): ");
                        UUID idSupprimer = UUID.fromString(scanner.nextLine());
                        offrePromotionnelleService.deleteOffre(idSupprimer);
                        break;
                    case 4:
                        afficherOffres();
                        break;
                    case 0:
                        System.out.println("Retour au menu principal.");
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("ID invalide. Veuillez réessayer.");
            }
        }
    }

    private OffrePromotionnelle saisirOffre() {
        Scanner scanner = new Scanner(System.in);
        OffrePromotionnelle offre = new OffrePromotionnelle(UUID.randomUUID()); // Generate new UUID for new offer

        System.out.print("Nom de l'offre: ");
        offre.setNomOffre(scanner.nextLine());
        System.out.print("Description de l'offre: ");
        offre.setDescription(scanner.nextLine());
        System.out.print("Date de début (YYYY-MM-DD): ");
        offre.setDateDebut(java.sql.Date.valueOf(scanner.nextLine()));
        System.out.print("Date de fin (YYYY-MM-DD): ");
        offre.setDateFin(java.sql.Date.valueOf(scanner.nextLine()));
        System.out.print("Type de réduction (ex. PERCENTAGE, FIXED): ");
        offre.setTypeReduction(TypeReduction.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Valeur de réduction: ");
        offre.setValeurReduction(new BigDecimal(scanner.nextLine()));
        System.out.print("Conditions de l'offre: ");
        offre.setConditions(scanner.nextLine());
        System.out.print("Statut de l'offre (ex. ACTIVE, INACTIVE): ");
        offre.setStatutOffre(StatutOffre.valueOf(scanner.nextLine().toUpperCase()));

        return offre;
    }

    private void afficherOffres() throws SQLException {
        for (OffrePromotionnelle offre : offrePromotionnelleService.getAllOffres()) {
            System.out.println(offre);
        }
    }
}
