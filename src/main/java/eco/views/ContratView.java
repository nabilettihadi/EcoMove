package main.java.eco.views;

import main.java.eco.models.Contrat;
import main.java.eco.services.ContratService;
import main.java.eco.models.enums.StatutContrat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class ContratView {

    private final ContratService contratService;
    private final SimpleDateFormat dateFormat;

    public ContratView() throws SQLException {
        this.contratService = new ContratService();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void displayContratMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Gestion des Contrats ===");
            System.out.println("1. Ajouter un Contrat");
            System.out.println("2. Modifier un Contrat");
            System.out.println("3. Supprimer un Contrat");
            System.out.println("4. Afficher les Contrats");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        contratService.addContrat(saisirContrat());
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'ajout du contrat : " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Entrez l'ID du Contrat à modifier (UUID): ");
                    UUID id = UUID.fromString(scanner.nextLine());
                    try {
                        Contrat contrat = saisirContrat();
                        contrat.setId(id);
                        contratService.updateContrat(contrat);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la modification du contrat : " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du Contrat à supprimer (UUID): ");
                    UUID idToDelete = UUID.fromString(scanner.nextLine());
                    try {
                        contratService.deleteContrat(idToDelete);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la suppression du contrat : " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        contratService.getAllContrats().forEach(System.out::println);
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de l'affichage des contrats : " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Retour...");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private Contrat saisirContrat() {
        Scanner scanner = new Scanner(System.in);
        UUID id = UUID.randomUUID();
        Date dateDebut = saisirDate("date de début (yyyy-MM-dd)");
        Date dateFin = saisirDate("date de fin (yyyy-MM-dd)");
        BigDecimal tarifSpecial = saisirBigDecimal("tarif spécial");
        System.out.print("Conditions d'accord: ");
        String conditionsAccord = scanner.nextLine();
        System.out.print("Renouvelable (true/false): ");
        boolean renouvelable = scanner.nextBoolean();
        scanner.nextLine();  // Consume newline
        System.out.print("Statut du contrat (EN_COURS, TERMINE, SUSPENDU): ");
        StatutContrat statutContrat = StatutContrat.valueOf(scanner.nextLine().toUpperCase());

        return new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat);
    }

    private Date saisirDate(String prompt) {
        Date date = null;
        boolean valid = false;

        while (!valid) {
            System.out.print("Entrez la " + prompt + ": ");
            String dateString = new Scanner(System.in).nextLine();
            try {
                date = dateFormat.parse(dateString);
                valid = true;
            } catch (ParseException e) {
                System.out.println("Format de date invalide. Veuillez réessayer.");
            }
        }
        return date;
    }

    private BigDecimal saisirBigDecimal(String prompt) {
        BigDecimal value = null;
        boolean valid = false;

        while (!valid) {
            System.out.print("Entrez le " + prompt + ": ");
            String input = new Scanner(System.in).nextLine();
            try {
                value = new BigDecimal(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Format invalide. Veuillez entrer un nombre valide.");
            }
        }
        return value;
    }
}
