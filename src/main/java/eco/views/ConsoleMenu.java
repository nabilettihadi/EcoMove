package main.java.eco.views;


import main.java.eco.services.ClientService;
import java.sql.SQLException;
import java.util.Scanner;


public class ConsoleMenu {

    private ClientService clientService;

    public void displayMainMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Gestion des Partenaires");
            System.out.println("2. Gestion des Contrats");
            System.out.println("3. Gestion des Offres Promotionnelles");
            System.out.println("4. Suivi des Billets Partenaires");
            System.out.println("5. Gestion des Utilisateurs");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    PartenaireView partenaireView = new PartenaireView();
                    partenaireView.displayPartenaireMenu();
                    break;
                case 2:
                    ContratView contratView = new ContratView();
                    contratView.displayContratMenu();
                    break;
                case 3:
                    OffrePromotionnelleView offrePromotionnelleView = new OffrePromotionnelleView();
                    offrePromotionnelleView.displayOffreMenu();
                    break;
                case 4:
                    BilletView billetView = new BilletView();
                    billetView.displayBilletMenu();
                    break;
                case 5:
                    ClientView clientView = new ClientView();
                    clientView.displayClientMenu();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
