
package main.java.eco.dao;

import main.java.eco.db.DatabaseConnection;
import main.java.eco.models.Billet;
import main.java.eco.enums.StatutBillet;
import main.java.eco.enums.TypeTransport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BilletDAO {

    private final Connection connection;

    public BilletDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addBillet(Billet billet) throws SQLException {
        String query = "INSERT INTO billets (id, id_contrat, type_transport, prix_achat, prix_vente, date_vente, statut_billet) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, billet.getId());
            statement.setObject(2, billet.getContrat().getId());
            statement.setObject(3, billet.getTypeTransport().name().toLowerCase(), Types.OTHER);
            statement.setBigDecimal(4, billet.getPrixAchat());
            statement.setBigDecimal(5, billet.getPrixVente());
            statement.setDate(6, new java.sql.Date(billet.getDateVente().getTime()));
            statement.setObject(7, billet.getStatutBillet().name().toLowerCase(), Types.OTHER);
            statement.executeUpdate();
        }
    }

    public void updateBillet(Billet billet) throws SQLException {
        String query = "UPDATE billets SET id_contrat = ?, type_transport = ?, prix_achat = ?, prix_vente = ?, date_vente = ?, statut_billet = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, billet.getContrat().getId());
            statement.setObject(2, billet.getTypeTransport().name().toLowerCase(), Types.OTHER);
            statement.setBigDecimal(3, billet.getPrixAchat());
            statement.setBigDecimal(4, billet.getPrixVente());
            statement.setDate(5, new java.sql.Date(billet.getDateVente().getTime()));
            statement.setObject(6, billet.getStatutBillet().name().toLowerCase(), Types.OTHER);
            statement.setObject(7, billet.getId());
            statement.executeUpdate();
        }
    }

    public void deleteBillet(UUID id) throws SQLException {
        String query = "DELETE FROM billets WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        }
    }

    public Billet getBillet(UUID id) throws SQLException {
        String query = "SELECT * FROM billets WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Créer un billet avec les données récupérées
                    return new Billet(
                            UUID.fromString(resultSet.getString("id")),
                            new ContratDAO().getContrat(UUID.fromString(resultSet.getString("id_contrat"))),
                            TypeTransport.valueOf(resultSet.getString("type_transport")),
                            resultSet.getBigDecimal("prix_achat"),
                            resultSet.getBigDecimal("prix_vente"),
                            resultSet.getDate("date_vente"),
                            StatutBillet.valueOf(resultSet.getString("statut_billet"))
                    );
                }
                return null;
            }
        }
    }

    public List<Billet> getAllBillets() throws SQLException {
        List<Billet> billets = new ArrayList<>();
        String query = "SELECT * FROM billets";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                billets.add(new Billet(
                        UUID.fromString(resultSet.getString("id")),
                        new ContratDAO().getContrat(UUID.fromString(resultSet.getString("id_contrat"))),
                        TypeTransport.valueOf(resultSet.getString("type_transport").toUpperCase()),
                        resultSet.getBigDecimal("prix_achat"),
                        resultSet.getBigDecimal("prix_vente"),
                        resultSet.getDate("date_vente"),
                        StatutBillet.valueOf(resultSet.getString("statut_billet").toUpperCase())
                ));
            }
        }
        return billets;
    }
}
