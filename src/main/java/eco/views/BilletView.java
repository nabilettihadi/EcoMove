package main.java.eco.views;

import main.java.eco.dao.ContratDAO;
import main.java.eco.enums.StatutBillet;
import main.java.eco.enums.TypeTransport;
import main.java.eco.models.Billet;
import main.java.eco.dao.BilletDAO;
import main.java.eco.models.Trajet;
import main.java.eco.dao.TrajetDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BilletView {

    private final BilletDAO billetDAO = new BilletDAO();
    private final TrajetDAO trajetDAO = new TrajetDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Scanner scanner = new Scanner(System.in);

    public BilletView() throws SQLException {
    }

    public void displayBilletMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Gestion des Billets ===");
            System.out.println("1. Ajouter un Billet");
            System.out.println("2. Modifier un Billet");
            System.out.println("3. Supprimer un Billet");
            System.out.println("4. Afficher les Billets");
            System.out.println("0. Retour");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        billetDAO.addBillet(saisirBillet());
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'ajout du billet : " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Entrez l'ID du Billet à modifier (UUID): ");
                    UUID idModifier = UUID.fromString(scanner.nextLine());
                    try {
                        billetDAO.updateBillet(saisirBillet(idModifier));
                    } catch (Exception e) {
                        System.out.println("Erreur lors de la modification du billet : " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du Billet à supprimer (UUID): ");
                    UUID idSupprimer = UUID.fromString(scanner.nextLine());
                    try {
                        billetDAO.deleteBillet(idSupprimer);
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
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Option invalide.");
                    break;
            }
        }
    }

    private Billet saisirBillet() throws ParseException, SQLException {
        return saisirBillet(null);
    }

    private Billet saisirBillet(UUID id) throws ParseException, SQLException {
        System.out.print("ID du contrat (UUID) : ");
        UUID contratId = UUID.fromString(scanner.nextLine());

        System.out.print("Type de transport (bus, train, avion) : ");
        String typeTransportStr = scanner.nextLine().toUpperCase();
        TypeTransport typeTransport = TypeTransport.valueOf(typeTransportStr);

        System.out.print("Prix d'achat : ");
        BigDecimal prixAchat = new BigDecimal(scanner.nextLine());

        System.out.print("Prix de vente : ");
        BigDecimal prixVente = new BigDecimal(scanner.nextLine());

        System.out.print("Date de vente (yyyy-MM-dd) : ");
        Date dateVente = dateFormat.parse(scanner.nextLine());

        System.out.print("Statut du billet (valide, annulé) : ");
        String statutBilletStr = scanner.nextLine().toUpperCase();
        StatutBillet statutBillet = StatutBillet.valueOf(statutBilletStr);

        System.out.print("ID du trajet : ");
        int trajetId = Integer.parseInt(scanner.nextLine());
        Trajet trajet = trajetDAO.getTrajetById(trajetId);

        if (id == null) {
            return new Billet(UUID.randomUUID(), new ContratDAO().getContrat(contratId), typeTransport, prixAchat, prixVente, dateVente, statutBillet, trajet);
        } else {
            return new Billet(id, new ContratDAO().getContrat(contratId), typeTransport, prixAchat, prixVente, dateVente, statutBillet, trajet);
        }
    }

    private void displayBillets() throws SQLException {
        List<Billet> billets = billetDAO.getAllBillets();
        for (Billet billet : billets) {
            System.out.println(billet);
        }
    }
}
