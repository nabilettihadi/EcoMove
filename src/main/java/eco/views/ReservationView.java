package main.java.eco.views;

import main.java.eco.services.ReservationService;
import main.java.eco.models.Reservation;
import main.java.eco.enums.StatutReservation;
import main.java.eco.models.Client;
import main.java.eco.models.Billet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ReservationView {
    private ReservationService reservationService;
    private Scanner scanner;

    public ReservationView() throws SQLException {
        this.reservationService = new ReservationService();
        this.scanner = new Scanner(System.in);
    }

    public void displayReservationMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("=== Gestion des Réservations ===");
            System.out.println("1. Ajouter une réservation");
            System.out.println("2. Modifier une réservation");
            System.out.println("3. Supprimer une réservation");
            System.out.println("4. Afficher toutes les réservations");
            System.out.println("5. Consulter les réservations d'un client");
            System.out.println("0. Retourner au menu principal");
            System.out.print("Choisissez une option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    ajouterReservation();
                    break;
                case 2:
                    modifierReservation();
                    break;
                case 3:
                    supprimerReservation();
                    break;
                case 4:
                    afficherToutesReservations();
                    break;
                case 5:
                    consulterReservationsClient();
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void ajouterReservation() {
        System.out.print("ID du client : ");
        UUID clientId = UUID.fromString(scanner.nextLine());
        System.out.print("ID du billet : ");
        UUID billetId = UUID.fromString(scanner.nextLine());
        System.out.print("Date de réservation (yyyy-MM-dd) : ");
        LocalDate dateReservation = LocalDate.parse(scanner.nextLine());
        System.out.print("Statut de la réservation (CONFIRMEE, ANNULEE, EN_ATTENTE) : ");
        StatutReservation statutReservation = StatutReservation.valueOf(scanner.nextLine().toUpperCase());

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID());
        reservation.setClient(new Client());
        reservation.setBillet(new Billet());
        reservation.setDateReservation(dateReservation);
        reservation.setStatutReservation(statutReservation);

        try {
            reservationService.addReservation(reservation);
            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }
    }

    private void modifierReservation() {
        System.out.print("ID de la réservation à modifier : ");
        UUID id = UUID.fromString(scanner.nextLine());

        try {
            Reservation reservation = reservationService.getReservationById(id);
            if (reservation == null) {
                System.out.println("Réservation non trouvée.");
                return;
            }

            System.out.print("Nouvelle date de réservation (yyyy-MM-dd) : ");
            LocalDate dateReservation = LocalDate.parse(scanner.nextLine());
            System.out.print("Nouveau statut de la réservation (CONFIRMEE, ANNULEE, EN_ATTENTE) : ");
            StatutReservation statutReservation = StatutReservation.valueOf(scanner.nextLine().toUpperCase());

            reservation.setDateReservation(dateReservation);
            reservation.setStatutReservation(statutReservation);

            reservationService.updateReservation(reservation);
            System.out.println("Réservation modifiée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la réservation : " + e.getMessage());
        }
    }

    private void supprimerReservation() {
        System.out.print("ID de la réservation à supprimer : ");
        UUID id = UUID.fromString(scanner.nextLine());

        try {
            reservationService.deleteReservation(id);
            System.out.println("Réservation supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }

    private void afficherToutesReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            for (Reservation reservation : reservations) {
                System.out.println("ID : " + reservation.getId());
                System.out.println("Client : " + reservation.getClient().getId());
                System.out.println("Billet : " + reservation.getBillet().getId());
                System.out.println("Date de réservation : " + reservation.getDateReservation());
                System.out.println("Statut : " + reservation.getStatutReservation());
                System.out.println("----");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des réservations : " + e.getMessage());
        }
    }

    private void consulterReservationsClient() {
        System.out.print("ID du client : ");
        UUID clientId = UUID.fromString(scanner.nextLine());

        try {
            List<Reservation> reservations = reservationService.getAllReservations(); // Filtering logic based on clientId to be added
            for (Reservation reservation : reservations) {
                if (reservation.getClient().getId().equals(clientId)) {
                    System.out.println("ID : " + reservation.getId());
                    System.out.println("Billet : " + reservation.getBillet().getId());
                    System.out.println("Date de réservation : " + reservation.getDateReservation());
                    System.out.println("Statut : " + reservation.getStatutReservation());
                    System.out.println("----");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la consultation des réservations du client : " + e.getMessage());
        }
    }
}