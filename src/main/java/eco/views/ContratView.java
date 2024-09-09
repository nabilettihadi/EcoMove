package main.java.eco.views;

import main.java.eco.models.Contrat;
import main.java.eco.models.Partenaire;
import main.java.eco.services.ContratService;
import main.java.eco.services.PartenaireService;
import main.java.eco.enums.StatutContrat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContratView {

    private final ContratService contratService;
    private final PartenaireService partenaireService;
    private final SimpleDateFormat dateFormat;

    public ContratView() throws SQLException {
        this.contratService = new ContratService();
        this.partenaireService = new PartenaireService();
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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        Contrat contrat = saisirContrat();
                        contratService.addContrat(
                                contrat.getId(),
                                contrat.getDateDebut(),
                                contrat.getDateFin(),
                                contrat.getTarifSpecial(),
                                contrat.getConditionsAccord(),
                                contrat.isRenouvelable(),
                                contrat.getStatutContrat(),
                                contrat.getPartenaire().getId()
                        );
                        System.out.println("Contrat ajouté avec succès !");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de l'ajout du contrat.");
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Entrez l'ID du contrat à modifier: ");
                        UUID id = UUID.fromString(scanner.nextLine());
                        Contrat contrat = contratService.getContrat(id);
                        if (contrat != null) {
                            System.out.println("Contrat trouvé : " + contrat);
                            Contrat updatedContrat = saisirContrat();
                            updatedContrat.setId(id);
                            contratService.updateContrat(
                                    updatedContrat.getId(),
                                    updatedContrat.getDateDebut(),
                                    updatedContrat.getDateFin(),
                                    updatedContrat.getTarifSpecial(),
                                    updatedContrat.getConditionsAccord(),
                                    updatedContrat.isRenouvelable(),
                                    updatedContrat.getStatutContrat(),
                                    updatedContrat.getPartenaire().getId()
                            );
                            System.out.println("Contrat modifié avec succès !");
                        } else {
                            System.out.println("Contrat non trouvé.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de la modification du contrat.");
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Entrez l'ID du contrat à supprimer: ");
                        UUID id = UUID.fromString(scanner.nextLine());
                        contratService.deleteContrat(id);
                        System.out.println("Contrat supprimé avec succès !");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de la suppression du contrat.");
                    }
                    break;
                case 4:
                    try {
                        List<Contrat> contrats = contratService.getAllContrats();
                        for (Contrat contrat : contrats) {
                            System.out.println(contrat);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de l'affichage des contrats.");
                    }
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Option invalide. Essayez encore.");
                    break;
            }
        }
    }

    private Contrat saisirContrat() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        UUID id = UUID.randomUUID();
        System.out.print("Entrez la date de début (yyyy-MM-dd): ");
        Date dateDebut = parseDate(scanner.nextLine());

        System.out.print("Entrez la date de fin (yyyy-MM-dd): ");
        Date dateFin = parseDate(scanner.nextLine());

        System.out.print("Entrez le tarif spécial: ");
        BigDecimal tarifSpecial = new BigDecimal(scanner.nextLine());

        System.out.print("Entrez les conditions d'accord: ");
        String conditionsAccord = scanner.nextLine();

        System.out.print("Le contrat est renouvelable (true/false): ");
        boolean renouvelable = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Entrez le statut du contrat (1: EN_COURS, 2: TERMINE, 3: SUSPENDU): ");
        int statut = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        StatutContrat statutContrat = StatutContrat.fromInt(statut);

        List<Partenaire> partenaires = partenaireService.getAllPartenaires();
        System.out.println("=== Choisir un Partenaire ===");
        for (int i = 0; i < partenaires.size(); i++) {
            System.out.println((i + 1) + ". " + partenaires.get(i).getNomCompagnie());
        }
        System.out.print("Choisissez un partenaire par numéro: ");
        int partenaireIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        Partenaire partenaire = partenaires.get(partenaireIndex);

        return new Contrat(id, dateDebut, dateFin, tarifSpecial, conditionsAccord, renouvelable, statutContrat, partenaire);
    }

    private Date parseDate(String dateString) {
        try {
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
