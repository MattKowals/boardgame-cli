package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.dao.JdbcBoardGameDao;
import org.example.models.BoardGame;
import org.example.view.Menu;
import org.example.view.Prompts;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class BoardGameCLI {

    private Menu menu;
    private Prompts prompt;
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
                boardGameDao.displayAllGamesData();
            } else if (userSelection.equals("2")) {
                System.out.println("Selected 2 - Game Collection Statistics");
                System.out.println();
                displayGameCount();
                displayTotalPrices();
                numberOfGamesPerPublisher();
                // graph of when games were purchased?
            } else if (userSelection.equals("3")) {
                System.out.println("Selected 3 - Add game");
                addNewGame();
            } else if (userSelection.equals("4")) {
                System.out.println("Selected 4 - Edit game");
                editGame();
            } else if (userSelection.equals("5")) {
                System.out.println("Selected Delete Game");
                promptForDeleteGame();
            } else if (userSelection.equals("6")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private void displayGameCount() {
        System.out.println("Number of games: " + boardGameDao.getGameCount());
    }


    // replaced displayGameNames() with displayAllGameData()
    private void displayGameNames() {
        System.out.println("Games in your collection: ");
        for (String name : boardGameDao.getGameNames()) {
            System.out.println("| " + name + " |");
        }
    }

    private void numberOfGamesPerPublisher() {
        List<String> publishers = new ArrayList<>();
        publishers = boardGameDao.getPublisherList();
        Map<String, Integer> publisherCount = new HashMap<>();
        for (String publisher : publishers) {
            if (publisherCount.containsKey(publisher)) {
                int currentValue = publisherCount.get(publisher);
                int newValue = currentValue + 1;
                publisherCount.put(publisher, newValue);
            } else {
                publisherCount.put(publisher, 1);
            }
        }
        System.out.println();
        System.out.printf("%-18s | %-8s", "Publisher", "Number of Games");
        System.out.println();
        for (Map.Entry<String, Integer> entry : publisherCount.entrySet()) {
            System.out.printf("%-18s | %-8s%n", entry.getKey(), entry.getValue());
//            System.out.println(entry.getKey() + " | " + entry.getValue() + " games");
        }
    }

    private void displayTotalPrices() {
        double prices = boardGameDao.getGamePrices();
        System.out.println("Total prices of games collection: $" + prices);
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
                if (newMaxTimeToPlay < newMinTimeToPlay) {
                    newMaxTimeToPlay = -1;
                    System.out.println("Maximum time to play must be greater than or equal to minimum time to play.");
                }
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
                if (newMaxPlayers < newMinPlayers) {
                    newMaxPlayers = -1;
                    System.out.println("Maximum number of players must be greater than or equal to minimum players.");
                }
            }
            newGame.setMax_players(newMaxPlayers);
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


    private void promptForDeleteGame() {
        String game_name = promptForString("Enter the name of the game to delete: ");
        deleteGame(game_name);
    }

    private void deleteGame(String game_name) {
        int deletedRows = boardGameDao.deleteGameByName(game_name);
        if (deletedRows == 0) {
            displayError("**Error in deleting game**");
        } else System.out.println("Game successfully deleted");
    }

    private void editGame() {
        int editedRows;
        String selection = menu.editGameChoiceMenu();
        if (selection.equals("1")) {
            String currentName = promptForString("What is the current game name to edit?");
            String newName = promptForString("What is the updated game name?");
            editedRows = boardGameDao.updateGameNameByOldName(currentName, newName);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("2")) {
            String game_name = promptForString("What game do you want to edit?");
            String publisher = promptForString("What is the new publisher name?");
            editedRows = boardGameDao.updateGamePublisherByName(game_name, publisher);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("3")) {
            String game_name = promptForString("What game do you want to edit?");
            int newYear = promptForInt("What year was the game published?");
            editedRows = boardGameDao.updateYearPublishedByName(game_name, newYear);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("4")) {
            String game_name = promptForString("What game do you want to edit?");
            LocalDate newDate = promptForDate("What date was the game purchased?");
            editedRows = boardGameDao.updateDatePurchasedByName(game_name, newDate);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("5")) {
            String game_name = promptForString("What game do you want to edit?");
            double newPrice = promptForDouble("What is the new price?");
            editedRows = boardGameDao.updatePriceByName(game_name, newPrice);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("6")) {
            String game_name = promptForString("What game do you want to edit?");
            int newTime = promptForInt("How long does the game take to teach (in minutes)?");
            editedRows = boardGameDao.updateTimeToTeachByName(game_name, newTime);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("7")) {
            String game_name = promptForString("What game do you want to edit?");
            int newTime = promptForInt("What is the minimum play time (in minutes)?");
            editedRows = boardGameDao.updateTimeToPlayMinByName(game_name, newTime);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("8")) {
            String game_name = promptForString("What game do you want to edit?");
            int newTime = promptForInt("What is the maximum play time (in minutes)?");
            editedRows = boardGameDao.updateTimeToPlayMaxByName(game_name, newTime);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("9")) {
            String game_name = promptForString("What game do you want to edit?");
            int newCount = promptForInt("What is the minimum number of players?");
            editedRows = boardGameDao.updatePlayersMinByName(game_name, newCount);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("10")) {
            String game_name = promptForString("What game do you want to edit?");
            int newCount = promptForInt("What is the maximum number of players?");
            editedRows = boardGameDao.updatePlayersMaxByName(game_name, newCount);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("11")) {
            String game_name = promptForString("What game do you want to edit?");
            editedRows = boardGameDao.updateExpansionStatusByName(game_name);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("12")) {
            String game_name = promptForString("What game do you want to edit?");
            String newDescription = promptForString("What is the new description?");
            editedRows = boardGameDao.updateDescriptionByName(game_name, newDescription);
            if (editedRows == 0) {
                displayError("Error updating game");
            } else System.out.println("Game updated");
        }

    }



    /****************************************
     * Methods for converting answers to data
     ****************************************/

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
                    displayError("**Numbers only**");
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
                    displayError("**Enter date as YYYY-MM-DD**");
                }
            }
        }
    }

    private void displayError(String message) {
        System.out.println();
        System.out.println(message);
    }

}