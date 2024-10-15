package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.dao.JdbcBoardGameDao;
import org.example.models.BoardGame;
import org.example.view.Menu;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class BoardGameCLI {

    private Menu menu;
    private BoardGameDao boardGameDao;

    private Scanner userInput = new Scanner(System.in);

    public BoardGameCLI(DataSource dataSource, Menu menu) {
        boardGameDao = new JdbcBoardGameDao(dataSource);
        this.menu = menu;
    }


    // makes ApplicationCLI runnable
    public static void main(String[] args) {
        Menu menu = new Menu();


        // Connect to database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/boardgames_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Beta150@");   // TODO: 10/10/2024 Hide password

        BoardGameCLI cli = new BoardGameCLI(dataSource, menu);
        cli.run();
    }


    // App starts here!
    public void run() {

        menu.showAppName();

        // TODO add a try catch


        while (true) {


            String userSelection = menu.showMainMenu();
            if (userSelection.equals("1")) {
                System.out.println("Selected 1 - Show board games");
                displayGameCount();
                displayGameNames();
            } else if (userSelection.equals("2")) {
                System.out.println("Selected 2 - Add game");
                addNewGame();
            } else if (userSelection.equals("3")) {
                System.out.println("Selected 3 - Delete game");
            } else if (userSelection.equals("4")) {
                break;
            }

        }

    }

    private void displayGameCount() {
        System.out.println("Number of games: " + boardGameDao.getGameCount());
        System.out.println();
    }

    private void displayGameNames() {
        System.out.println("Games in your collection: ");
        for (String name : boardGameDao.getGameNames()) {
            System.out.println(name);
        }
    }

    private void addNewGame() {
        BoardGame newBoarGame = promptForNewGameData();
        newBoarGame = boardGameDao.addBoardGame(newBoarGame);
    }

    private BoardGame promptForNewGameData() {
        BoardGame newGame = new BoardGame();
        String newName = "";
            while (newName.isBlank()) {
                newName = promptForString("Game name: ");
            }
            newGame.setName(newName);
        String newPublisher = "";
            while (newPublisher.isBlank()) {
                newPublisher = promptForString("Publisher: ");
            }
            newGame.setPublisher(newPublisher);
        int newYear = 0;
            while (newYear == 0) {
                newYear = promptForInt("Year game released: ");
            }
            newGame.setYear_published(newYear);
        LocalDate newDatePurchased = null;
            while (newDatePurchased == null) {
                newDatePurchased = promptForDate("Date purchased (YYYY-MM-DD): ");
            }
            newGame.setDate_purchased(newDatePurchased);
        double newPrice = -1;
            while (newPrice == -1) {
                newPrice = promptForDouble("Price: ");
            }
            newGame.setPrice(newPrice);
        int newTimeToTeach = -1;
            while (newTimeToTeach == -1) {
                newTimeToTeach = promptForInt("Time to teach game (in minutes): ");
            }
            newGame.setTime_to_teach_in_minutes(newTimeToTeach);
        int newMinTimeToPlay = -1;
            while (newMinTimeToPlay == -1) {
                newMinTimeToPlay = promptForInt("Minimum gameplay time (in minutes): ");
            }
            newGame.setTime_to_play_in_minutes_min(newMinTimeToPlay);
        int newMaxTimeToPlay = -1;
            while (newMaxTimeToPlay == -1) {
                newMaxTimeToPlay = promptForInt("Maximum gameplay time (in minutes) :");
            }
            newGame.setTime_to_play_in_minutes_max(newMaxTimeToPlay);
        int newMinPlayers = -1;
            while (newMinPlayers == -1) {
                newMinPlayers = promptForInt("Minimum number of players: ");
            }
            newGame.setMin_players(newMinPlayers);
        int newMaxPlayers = -1;
            while (newMaxPlayers == -1) {
                newMaxPlayers = promptForInt("Maximum number of players: ");
            }
        boolean isExpansion = false;
            String yes_or_no = promptForString("Is this game an expansion (yes/no): ").toLowerCase();
            if (yes_or_no.equals("yes")) {
                isExpansion = true;
                newGame.setExpansion(isExpansion);
            } else newGame.setExpansion(isExpansion);
        String newDescription = "";
            while (newDescription == "") {
                newDescription = promptForString("Game description: ");
            }
            newGame.setDescription(newDescription);


        return newGame;
    }

    // Methods for converting answers to data

    private String promptForString(String prompt) {
        System.out.println(prompt);
        return userInput.nextLine();
    }

    private double promptForDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            String response = userInput.nextLine();
            try {
                return Double.parseDouble(response);
            } catch (NumberFormatException e) {
                if (response.isBlank()) {
                    return -1;
                } else {
                    displayError("Numbers only");
                }
            }
        }
    }

    private int promptForInt(String prompt) {
        return (int) promptForDouble(prompt);
    }

    private LocalDate promptForDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String response = userInput.nextLine();
            try {
                return LocalDate.parse(response);
            } catch (DateTimeParseException e) {
                if (response.isBlank()) {
                    return null;
                } else {
                    displayError("Enter date as YYYY-MM-DD");
                }
            }
        }
    }

    private void displayError(String message) {
        System.out.println();
        System.out.println(message);
    }

}