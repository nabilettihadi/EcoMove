package main.java.eco.views;

import main.java.eco.models.Contrat;
import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.enums.StatutOffre;
import main.java.eco.enums.TypeReduction;
import main.java.eco.dao.ContratDAO;
import main.java.eco.services.OffrePromotionnelleService;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OffrePromotionnelleView {
    private final OffrePromotionnelleService offreService;
    private final ContratDAO contratDAO;
    private final Scanner scanner;

    public OffrePromotionnelleView() {
        try {
            this.offreService = new OffrePromotionnelleService();
            this.contratDAO = new ContratDAO();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'initialisation des services.", e);
        }
        this.scanner = new Scanner(System.in);
    }

    public void displayOffreMenu() throws SQLException {
        while (true) {
            System.out.println("Gestion des offres promotionnelles");
            System.out.println("1. Ajouter une offre promotionnelle");
            System.out.println("2. Modifier une offre promotionnelle");
            System.out.println("3. Supprimer une offre promotionnelle");
            System.out.println("4. Afficher toutes les offres promotionnelles");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterOffrePromotionnelle();
                    break;
                case 2:
                    modifierOffrePromotionnelle();
                    break;
                case 3:
                    supprimerOffrePromotionnelle();
                    break;
                case 4:
                    afficherToutesLesOffresPromotionnelles();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterOffrePromotionnelle() throws SQLException {
        UUID id = UUID.randomUUID();
        System.out.print("Nom de l'offre : ");
        String nomOffre = scanner.nextLine();

        System.out.print("ID du contrat (UUID) : ");
        UUID contratId = UUID.fromString(scanner.nextLine());
        Contrat contrat = contratDAO.getContrat(contratId);
        if (contrat == null) {
            System.out.println("Contrat non trouvé.");
            return;
        }

        System.out.print("Type de réduction (ex : POURCENTAGE, MONTANT) : ");
        TypeReduction typeReduction = TypeReduction.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Statut de l'offre (ex : ACTIVE, EXPIREE, SUSPENDUE) : ");
        StatutOffre statutOffre = StatutOffre.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Date de début (yyyy-mm-dd) : ");
        Date dateDebut = Date.valueOf(scanner.nextLine());

        System.out.print("Date de fin (yyyy-mm-dd) : ");
        Date dateFin = Date.valueOf(scanner.nextLine());

        System.out.print("Valeur de réduction : ");
        BigDecimal valeurReduction = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Conditions : ");
        String conditions = scanner.nextLine();

        System.out.print("Description : "); // Ajoutez cette ligne
        String description = scanner.nextLine(); // Ajoutez cette ligne

        OffrePromotionnelle offre = new OffrePromotionnelle(
                id,
                nomOffre,
                description, // Ajoutez description ici
                dateDebut,
                dateFin,
                typeReduction,
                valeurReduction,
                conditions,
                statutOffre,
                contrat
        );
        offreService.addOffrePromotionnelle(offre);
        System.out.println("Offre promotionnelle ajoutée avec succès !");
    }


    private void modifierOffrePromotionnelle() throws SQLException {
        System.out.print("ID de l'offre à modifier (UUID) : ");
        UUID id = UUID.fromString(scanner.nextLine());

        OffrePromotionnelle offre = offreService.getOffrePromotionnelle(id);
        if (offre == null) {
            System.out.println("Offre non trouvée.");
            return;
        }

        System.out.print("Nouveau nom de l'offre : ");
        offre.setNomOffre(scanner.nextLine());

        System.out.print("Nouvelle description : "); // Ajoutez cette ligne
        offre.setDescription(scanner.nextLine()); // Ajoutez cette ligne

        System.out.print("Nouvelles conditions : ");
        offre.setConditions(scanner.nextLine());

        System.out.print("Nouveau type de réduction (ex : POURCENTAGE, MONTANT) : ");
        offre.setTypeReduction(TypeReduction.valueOf(scanner.nextLine().toUpperCase()));

        System.out.print("Nouveau statut de l'offre (ex : ACTIVE, EXPIREE, SUSPENDUE) : ");
        offre.setStatutOffre(StatutOffre.valueOf(scanner.nextLine().toUpperCase()));

        System.out.print("Nouvelle date de début (yyyy-mm-dd) : ");
        offre.setDateDebut(Date.valueOf(scanner.nextLine()));

        System.out.print("Nouvelle date de fin (yyyy-mm-dd) : ");
        offre.setDateFin(Date.valueOf(scanner.nextLine()));

        System.out.print("Nouvelle valeur de réduction : ");
        offre.setValeurReduction(scanner.nextBigDecimal());
        scanner.nextLine();

        offreService.updateOffrePromotionnelle(offre);
        System.out.println("Offre promotionnelle modifiée avec succès !");
    }


    private void supprimerOffrePromotionnelle() throws SQLException {
        System.out.print("ID de l'offre à supprimer (UUID) : ");
        UUID id = UUID.fromString(scanner.nextLine());
        offreService.deleteOffrePromotionnelle(id);
        System.out.println("Offre promotionnelle supprimée avec succès !");
    }

    private void afficherToutesLesOffresPromotionnelles() throws SQLException {
        List<OffrePromotionnelle> offres = offreService.getAllOffresPromotionnelles();
        for (OffrePromotionnelle offre : offres) {
            System.out.println(offre);
        }
    }
}
