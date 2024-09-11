package main.java.eco.dao;

import main.java.eco.models.Client;
import main.java.eco.db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection(); // Utiliser la même connexion
    }


    public void addClient(Client client) throws SQLException {
        String query = "INSERT INTO client (id, nom, prenom, email, telephone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, client.getId());
            statement.setString(2, client.getNom());
            statement.setString(3, client.getPrenom());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getTelephone());
            statement.executeUpdate();
        }
    }

    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE client SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getTelephone());
            statement.setObject(5, client.getId());
            statement.executeUpdate();
        }
    }

    public void deleteClient(UUID id) throws SQLException {
        String query = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        }
    }

    public Client getClient(UUID id) throws SQLException {
        String query = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
                client.setId((UUID) rs.getObject("id"));
                return client;
            }
        }
        return null;
    }


    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM client";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
                client.setId((UUID) rs.getObject("id"));
                clients.add(client);
            }
        }
        return clients;
    }
}
