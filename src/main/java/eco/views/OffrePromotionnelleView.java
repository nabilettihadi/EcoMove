package main.java.eco.views;

import main.java.eco.models.OffrePromotionnelle;
import main.java.eco.models.enums.StatutOffre;
import main.java.eco.models.enums.TypeReduction;
import main.java.eco.services.OffrePromotionnelleService;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OffrePromotionnelleView {
    private final OffrePromotionnelleService service;
    private final Scanner scanner;

    public OffrePromotionnelleView() throws SQLException {
        this.service = new OffrePromotionnelleService();
        this.scanner = new Scanner(System.in);
    }

    public void displayOffreMenu() {
        while (true) {
            System.out.println("1. Ajouter une nouvelle offre");
            System.out.println("2. Mettre à jour une offre");
            System.out.println("3. Supprimer une offre");
            System.out.println("4. Afficher toutes les offres");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    try {
                        ajouterOffre();
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de l'ajout de l'offre : " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        mettreAJourOffre();
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la mise à jour de l'offre : " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        supprimerOffre();
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la suppression de l'offre : " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        afficherToutesLesOffres();
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de l'affichage des offres : " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        }
    }

    private void ajouterOffre() throws SQLException {
        System.out.print("Entrez le nom de l'offre : ");
        String nomOffre = scanner.nextLine();

        System.out.print("Entrez la description de l'offre : ");
        String description = scanner.nextLine();

        System.out.print("Entrez la date de début (yyyy-mm-dd) : ");
        Date dateDebut = Date.valueOf(scanner.nextLine());

        System.out.print("Entrez la date de fin (yyyy-mm-dd) : ");
        Date dateFin = Date.valueOf(scanner.nextLine());

        System.out.print("Choisissez le type de réduction (1 pour POURCENTAGE, 2 pour MONTANT_FIXE) : ");
        TypeReduction typeReduction = TypeReduction.values()[scanner.nextInt() - 1];

        System.out.print("Entrez la valeur de la réduction : ");
        BigDecimal valeurReduction = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.print("Entrez les conditions de l'offre : ");
        String conditions = scanner.nextLine();

        System.out.print("Choisissez le statut de l'offre (1 pour ACTIVE, 2 pour EXPIREE, 3 pour SUSPENDUE) : ");
        StatutOffre statutOffre = StatutOffre.values()[scanner.nextInt() - 1];
        scanner.nextLine();

        System.out.print("Choisissez l'ID du contrat : ");
        UUID idContrat = UUID.fromString(scanner.nextLine());

        OffrePromotionnelle offre = new OffrePromotionnelle(
                UUID.randomUUID(),
                nomOffre,
                description,
                dateDebut,
                dateFin,
                typeReduction,
                valeurReduction,
                conditions,
                statutOffre,
                idContrat
        );

        service.addOffre(offre);
        System.out.println("Offre ajoutée avec succès !");
    }

    private void mettreAJourOffre() throws SQLException {
        System.out.print("Entrez l'ID de l'offre à mettre à jour : ");
        UUID id = UUID.fromString(scanner.nextLine());

        OffrePromotionnelle offre = service.getOffre(id);

        if (offre == null) {
            System.out.println("Offre non trouvée.");
            return;
        }

        System.out.print("Entrez le nouveau nom de l'offre (laisser vide pour ne pas modifier) : ");
        String nomOffre = scanner.nextLine();
        if (!nomOffre.isEmpty()) {
            offre.setNomOffre(nomOffre);
        }

        System.out.print("Entrez la nouvelle description de l'offre (laisser vide pour ne pas modifier) : ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            offre.setDescription(description);
        }

        System.out.print("Entrez la nouvelle date de début (yyyy-mm-dd) (laisser vide pour ne pas modifier) : ");
        String dateDebutStr = scanner.nextLine();
        if (!dateDebutStr.isEmpty()) {
            offre.setDateDebut(Date.valueOf(dateDebutStr));
        }

        System.out.print("Entrez la nouvelle date de fin (yyyy-mm-dd) (laisser vide pour ne pas modifier) : ");
        String dateFinStr = scanner.nextLine();
        if (!dateFinStr.isEmpty()) {
            offre.setDateFin(Date.valueOf(dateFinStr));
        }

        System.out.print("Choisissez le nouveau type de réduction (1 pour POURCENTAGE, 2 pour MONTANT_FIXE) (laisser vide pour ne pas modifier) : ");
        String typeReductionStr = scanner.nextLine();
        if (!typeReductionStr.isEmpty()) {
            offre.setTypeReduction(TypeReduction.values()[Integer.parseInt(typeReductionStr) - 1]);
        }

        System.out.print("Entrez la nouvelle valeur de la réduction (laisser vide pour ne pas modifier) : ");
        String valeurReductionStr = scanner.nextLine();
        if (!valeurReductionStr.isEmpty()) {
            offre.setValeurReduction(new BigDecimal(valeurReductionStr));
        }

        System.out.print("Entrez les nouvelles conditions de l'offre (laisser vide pour ne pas modifier) : ");
        String conditions = scanner.nextLine();
        if (!conditions.isEmpty()) {
            offre.setConditions(conditions);
        }

        System.out.print("Choisissez le nouveau statut de l'offre (1 pour ACTIVE, 2 pour EXPIREE, 3 pour SUSPENDUE) (laisser vide pour ne pas modifier) : ");
        String statutOffreStr = scanner.nextLine();
        if (!statutOffreStr.isEmpty()) {
            offre.setStatutOffre(StatutOffre.values()[Integer.parseInt(statutOffreStr) - 1]);
        }

        System.out.print("Choisissez le nouvel ID du contrat (laisser vide pour ne pas modifier) : ");
        String idContratStr = scanner.nextLine();
        if (!idContratStr.isEmpty()) {
            offre.setIdContrat(UUID.fromString(idContratStr));
        }

        service.updateOffre(id, offre);
        System.out.println("Offre mise à jour avec succès !");
    }

    private void supprimerOffre() throws SQLException {
        System.out.print("Entrez l'ID de l'offre à supprimer : ");
        UUID id = UUID.fromString(scanner.nextLine());

        service.deleteOffre(id);
        System.out.println("Offre supprimée avec succès !");
    }

    private void afficherToutesLesOffres() throws SQLException {
        List<OffrePromotionnelle> offres = service.getAllOffres();

        if (offres.isEmpty()) {
            System.out.println("Aucune offre trouvée.");
        } else {
            for (OffrePromotionnelle offre : offres) {
                System.out.println(offre);
            }
        }
    }
}
