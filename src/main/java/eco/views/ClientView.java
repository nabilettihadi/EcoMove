package main.java.eco.views;

import main.java.eco.models.Client;
import main.java.eco.services.ClientService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ClientView {
    private final ClientService clientService;
    private final Scanner scanner;

    public ClientView() throws SQLException {
        this.clientService = new ClientService();
        this.scanner = new Scanner(System.in);
    }

    public void displayClientMenu() throws SQLException {
        while (true) {
            System.out.println("Gestion des clients");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Modifier un client");
            System.out.println("3. Supprimer un client");
            System.out.println("4. Afficher tous les clients");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterClient();
                    break;
                case 2:
                    modifierClient();
                    break;
                case 3:
                    supprimerClient();
                    break;
                case 4:
                    afficherTousLesClients();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterClient() throws SQLException {
        System.out.print("Nom du client : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom du client : ");
        String prenom = scanner.nextLine();

        System.out.print("Email du client : ");
        String email = scanner.nextLine();

        System.out.print("Téléphone du client : ");
        String telephone = scanner.nextLine();

        Client client = new Client(nom, prenom, email, telephone);
        client.setId(UUID.randomUUID());
        clientService.addClient(client);
        System.out.println("Client ajouté avec succès !");
    }

    private void modifierClient() throws SQLException {
        System.out.print("ID du client à modifier (UUID) : ");
        UUID id = UUID.fromString(scanner.nextLine());

        Client client = clientService.getClient(id);
        if (client == null) {
            System.out.println("Client non trouvé.");
            return;
        }

        System.out.print("Nouveau nom du client : ");
        client.setNom(scanner.nextLine());

        System.out.print("Nouveau prénom du client : ");
        client.setPrenom(scanner.nextLine());

        System.out.print("Nouvel email du client : ");
        client.setEmail(scanner.nextLine());

        System.out.print("Nouveau téléphone du client : ");
        client.setTelephone(scanner.nextLine());

        clientService.updateClient(client);
        System.out.println("Client modifié avec succès !");
    }

    private void supprimerClient() throws SQLException {
        System.out.print("ID du client à supprimer (UUID) : ");
        UUID id = UUID.fromString(scanner.nextLine());
        clientService.deleteClient(id);
        System.out.println("Client supprimé avec succès !");
    }

    private void afficherTousLesClients() throws SQLException {
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            System.out.println(client);
        }
    }
}
