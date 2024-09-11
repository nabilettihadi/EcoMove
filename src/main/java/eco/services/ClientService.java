package main.java.eco.services;

import main.java.eco.dao.ClientDAO;
import main.java.eco.models.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService() throws SQLException {
        this.clientDAO = new ClientDAO();
    }


    public void addClient(Client client) throws SQLException {


        clientDAO.addClient(client);
    }

    public void updateClient(Client client) throws SQLException {
        clientDAO.updateClient(client);
    }

    public void deleteClient(UUID id) throws SQLException {
        clientDAO.deleteClient(id);
    }

    public Client getClient(UUID id) throws SQLException {
        return clientDAO.getClient(id);
    }

    public List<Client> getAllClients() throws SQLException {
        return clientDAO.getAllClients();
    }
}
