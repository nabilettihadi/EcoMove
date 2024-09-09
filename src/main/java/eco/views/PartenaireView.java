package main.java.eco.views;

import main.java.eco.models.Partenaire;
import main.java.eco.enums.StatutPartenaire;
import main.java.eco.enums.TypeTransport;
import main.java.eco.dao.PartenaireDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartenaireView {

    private final PartenaireDAO partenaireService;

    public PartenaireView() throws SQLException {
        this.partenaireService = new PartenaireDAO();
    }

    public void displayPartenaireMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Gestion des Partenaires ===");
            System.out.println("1. Ajouter un Partenaire");
            System.out.println("2. Modifier un Partenaire");
            System.out.println("3. Supprimer un Partenaire");
            System.out.println("4. Afficher les Partenaires");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        partenaireService.addPartenaire(saisirPartenaire());
                        break;
                    case 2:
                        System.out.print("Entrez l'ID du Partenaire à modifier (UUID): ");
                        UUID idModifier = UUID.fromString(scanner.nextLine());
                        Partenaire partenaireAModifier = partenaireService.getPartenaire(idModifier);
                        if (partenaireAModifier != null) {
                            partenaireService.updatePartenaire(idModifier, saisirPartenaire());
                        } else {
                            System.out.println("Partenaire non trouvé.");
                        }
                        break;
                    case 3:
                        System.out.print("Entrez l'ID du Partenaire à supprimer (UUID): ");
                        UUID idSupprimer = UUID.fromString(scanner.nextLine());
                        partenaireService.deletePartenaire(idSupprimer);
                        break;
                    case 4:
                        afficherPartenaires();
                        break;
                    case 0:
                        System.out.println("Retour au menu principal.");
                        break;
                    default:
                        System.out.println("Option invalide.");
                        break;
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre valide.");
                scanner.nextLine();
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }
    }

    private Partenaire saisirPartenaire() {
        Scanner scanner = new Scanner(System.in);
        UUID id = UUID.randomUUID();

        System.out.print("Nom de la compagnie: ");
        String nomCompagnie = scanner.nextLine();

        System.out.print("Contact commercial: ");
        String contactCommercial = scanner.nextLine();

        TypeTransport typeTransport = saisirTypeTransport(scanner);

        System.out.print("Zone géographique: ");
        String zoneGeographique = scanner.nextLine();

        System.out.print("Conditions spéciales: ");
        String conditionsSpeciales = scanner.nextLine();

        StatutPartenaire statutPartenaire = saisirStatutPartenaire(scanner);

        System.out.print("Date de création (format YYYY-MM-DD): ");
        String dateCreationStr = scanner.nextLine();
        Date dateCreation = Date.valueOf(dateCreationStr);

        return new Partenaire(id, nomCompagnie, contactCommercial, typeTransport, zoneGeographique, conditionsSpeciales, statutPartenaire, dateCreation);
    }

    private TypeTransport saisirTypeTransport(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Type de transport (e.g., AVION, TRAIN, BUS): ");
                return TypeTransport.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Type de transport invalide. Veuillez réessayer.");
            }
        }
    }

    private StatutPartenaire saisirStatutPartenaire(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Statut du partenaire (e.g., ACTIF, INACTIF, SUSPENDU): ");
                return StatutPartenaire.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Statut du partenaire invalide. Veuillez réessayer.");
            }
        }
    }

    private void afficherPartenaires() throws SQLException {
        List<Partenaire> partenaires = partenaireService.getAllPartenaires();
        if (partenaires.isEmpty()) {
            System.out.println("Aucun partenaire trouvé.");
        } else {
            for (Partenaire partenaire : partenaires) {
                System.out.println(partenaire);
            }
        }
    }
}
