package main.java.eco;

import main.java.eco.views.ConsoleMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConsoleMenu menu = new ConsoleMenu();
        menu.displayMainMenu();
    }
}
