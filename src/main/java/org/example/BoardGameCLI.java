package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.dao.JdbcBoardGameDao;
import org.example.models.BoardGame;
import org.example.services.ExportService;
import org.example.services.SortingService;
import org.example.services.StatisticsService;
import org.example.view.Menu;
import org.example.view.Prompts;

import javax.sql.DataSource;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class BoardGameCLI {

    private Menu menu;
    private Prompts prompt;
    private BoardGameDao boardGameDao;
    private StatisticsService statisticsService;
    private SortingService sortingService;
    private ExportService exportService;


    public BoardGameCLI(DataSource dataSource) {
        boardGameDao = new JdbcBoardGameDao(dataSource);
        this.menu = new Menu();
        this.prompt = new Prompts();
    }

    // makes ApplicationCLI runnable
    public static void main(String[] args) {

        // Connect to database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/boardgames_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        BoardGameCLI cli = new BoardGameCLI(dataSource);
        cli.run();
    }


    // App starts here!
    public void run() {

        menu.showAppName();

        while (true) {

            String userSelection = menu.showMainMenu();
            if (userSelection.equals("1")) {
                menu.showCollectionBanner();
                printGameList(boardGameDao.getBoardGames());
            } else if (userSelection.equals("2")) {
                printGameList(boardGameDao.sortByBaseGameAndName());
                pickNumberOfPlayers();
            } else if (userSelection.equals("3")) {
                menu.showStatsBanner();
                System.out.println();
                statisticsService.displayGameTypeCount();
                statisticsService.displayTotalPrices();
                statisticsService.bubbleSortGamesPerPublisher();
            } else if (userSelection.equals("4")) {
                menu.showAddGameBanner();
                addNewGame();
            } else if (userSelection.equals("5")) {
                menu.showEditGameBanner();
                editGame();
            } else if (userSelection.equals("6")) {
                menu.showDeleteGameBanner();
                promptForDeleteGame();
            } else if (userSelection.equals("7")) {
                System.out.println("Thanks for playing!");
                break;
            } else if (userSelection.equals("0")) {
                System.out.println(boardGameDao.getRandomGame().getName());
            } else if (userSelection.equals("9")) {
                exportService.exportDatabaseToFile(exportService.promptForExportDatabase());
            }
        }
    }


    /******************
     * Start of Methods
     ******************/

    private void printGameList(List<BoardGame> gamesList) {
        BoardGame[] games = gamesList.toArray(new BoardGame[0]);
        String header = String.format("| %-45s | %-20s | %-6s | %-16s | %-8s | %-15s | %-14s | %-14s | %-11s | %s",
                "Name", "Publisher", "Year", "Date Purchased", "Price", "Time to Teach", "Time to Play", "Player Count", "Game Type", "Description");
        System.out.println(header);
        for (int i = 0; i < games.length; i++) {
            String datePurchased = games[i].date_purchased.toString();
            // promptForNewGameData() sets null date as 1970-01-01
            // This sets 1970-01-01 as "N/A"
            if (games[i].date_purchased.isEqual(LocalDate.parse("1970-01-01"))) {
                datePurchased = "N/A";
            }
            String expansionDetail = "";
            if (games[i].expansion == true) {
                expansionDetail = "Expansion";
            } else expansionDetail = "Base Game";
            String individualGame = String.format("| %-45s | %-20s | %-6d | %-16s | $%-7.2f | %-15s | %-14s | %-14s | %-11s | %s",
                    games[i].getName(), games[i].getPublisher(), games[i].getYear_published(), datePurchased, games[i].getPrice(),
                    games[i].getTime_to_teach_in_minutes(), games[i].getTime_to_play_in_minutes_min() + " - " + games[i].getTime_to_play_in_minutes_max(),
                    games[i].getMin_players() + " - " + games[i].getMax_players(), expansionDetail, games[i].getDescription());
            System.out.println(individualGame);
        }
    }

    private void pickNumberOfPlayers() {
        System.out.println();
        int count = prompt.promptForInt("How many players?");
        List<BoardGame> games = boardGameDao.chooseNumberOfPlayers(count);
        printGameList(games);
    }




    /**************
     * CRUD Methods
     **************/

    private void addNewGame() {
        BoardGame newBoarGame = promptForNewGameData();
        newBoarGame = boardGameDao.addBoardGame(newBoarGame);
    }

    private BoardGame promptForNewGameData() {
        BoardGame newGame = new BoardGame();
        String newName = "";
        while (newName.isBlank()) {
            newName = prompt.promptForString("Game name: ");
        }
        newGame.setName(newName);
        String newPublisher = "";
        while (newPublisher.isBlank()) {
            newPublisher = prompt.promptForString("Publisher: ");
        }
        newGame.setPublisher(newPublisher);
        int newYear = 0;
        while (newYear == 0) {
            newYear = prompt.promptForInt("Year game released: ");
        }
        newGame.setYear_published(newYear);
        LocalDate newDatePurchased = null;
        newDatePurchased = prompt.promptForDate("Date purchased (YYYY-MM-DD): ");
        if (newDatePurchased == null) {
            // sets date as 1970-01-01
            newDatePurchased = LocalDate.ofEpochDay(0000-00-00);
        }
        newGame.setDate_purchased(newDatePurchased);
        double newPrice = -1;
        while (newPrice == -1) {
            newPrice = prompt.promptForDouble("Price: ");
        }
        newGame.setPrice(newPrice);
        int newTimeToTeach = -1;
        while (newTimeToTeach == -1) {
            newTimeToTeach = prompt.promptForInt("Time to teach game (in minutes): ");
        }
        newGame.setTime_to_teach_in_minutes(newTimeToTeach);
        int newMinTimeToPlay = -1;
        while (newMinTimeToPlay == -1) {
            newMinTimeToPlay = prompt.promptForInt("Minimum gameplay time (in minutes): ");
        }
        newGame.setTime_to_play_in_minutes_min(newMinTimeToPlay);
        int newMaxTimeToPlay = -1;
        while (newMaxTimeToPlay == -1) {
            newMaxTimeToPlay = prompt.promptForInt("Maximum gameplay time (in minutes) :");
            if (newMaxTimeToPlay < newMinTimeToPlay) {
                newMaxTimeToPlay = -1;
                System.out.println("Maximum time to play must be greater than or equal to minimum time to play.");
            }
        }
        newGame.setTime_to_play_in_minutes_max(newMaxTimeToPlay);
        int newMinPlayers = -1;
        while (newMinPlayers == -1) {
            newMinPlayers = prompt.promptForInt("Minimum number of players: ");
        }
        newGame.setMin_players(newMinPlayers);
        int newMaxPlayers = -1;
        while (newMaxPlayers == -1) {
            newMaxPlayers = prompt.promptForInt("Maximum number of players: ");
            if (newMaxPlayers < newMinPlayers) {
                newMaxPlayers = -1;
                System.out.println("Maximum number of players must be greater than or equal to minimum players.");
            }
        }
        newGame.setMax_players(newMaxPlayers);
        boolean isExpansion = false;
        String yes_or_no = prompt.promptForString("Is this game an expansion (yes/no): ").toLowerCase();
        if (yes_or_no.equals("yes")) {
            isExpansion = true;
            newGame.setExpansion(isExpansion);
        } else newGame.setExpansion(isExpansion);
        String newDescription = "";
        while (newDescription == "") {
            newDescription = prompt.promptForString("Game description: ");
        }
        newGame.setDescription(newDescription);

        return newGame;
    }

    private void deleteGame(String game_name) {
        int deletedRows = boardGameDao.deleteGameByName(game_name);
        if (deletedRows == 0) {
            prompt.displayError("**Error in deleting game**");
        } else System.out.println("Game successfully deleted");
    }

    private void promptForDeleteGame() {
        String game_name = prompt.promptForString("Enter the name of the game to delete: ");
        deleteGame(game_name);
    }

    private void editGame() {
        int editedRows;
        String selection = menu.editGameChoiceMenu();
        if (selection.equals("1")) {
            String currentName = prompt.promptForString("What is the current game name to edit?");
            String newName = prompt.promptForString("What is the updated game name?");
            editedRows = boardGameDao.updateGameNameByOldName(currentName, newName);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("2")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            String publisher = prompt.promptForString("What is the new publisher name?");
            editedRows = boardGameDao.updateGamePublisherByName(game_name, publisher);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("3")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newYear = prompt.promptForInt("What year was the game published?");
            editedRows = boardGameDao.updateYearPublishedByName(game_name, newYear);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("4")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            LocalDate newDate = prompt.promptForDate("What date was the game purchased?");
            editedRows = boardGameDao.updateDatePurchasedByName(game_name, newDate);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("5")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            double newPrice = prompt.promptForDouble("What is the new price?");
            editedRows = boardGameDao.updatePriceByName(game_name, newPrice);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("6")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newTime = prompt.promptForInt("How long does the game take to teach (in minutes)?");
            editedRows = boardGameDao.updateTimeToTeachByName(game_name, newTime);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("7")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newTime = prompt.promptForInt("What is the minimum play time (in minutes)?");
            editedRows = boardGameDao.updateTimeToPlayMinByName(game_name, newTime);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("8")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newTime = prompt.promptForInt("What is the maximum play time (in minutes)?");
            editedRows = boardGameDao.updateTimeToPlayMaxByName(game_name, newTime);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("9")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newCount = prompt.promptForInt("What is the minimum number of players?");
            editedRows = boardGameDao.updatePlayersMinByName(game_name, newCount);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("10")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            int newCount = prompt.promptForInt("What is the maximum number of players?");
            editedRows = boardGameDao.updatePlayersMaxByName(game_name, newCount);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("11")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            editedRows = boardGameDao.updateExpansionStatusByName(game_name);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }
        if (selection.equals("12")) {
            String game_name = prompt.promptForString("What game do you want to edit?");
            String newDescription = prompt.promptForString("What is the new description?");
            editedRows = boardGameDao.updateDescriptionByName(game_name, newDescription);
            if (editedRows == 0) {
                prompt.displayError("Error updating game");
            } else System.out.println("Game updated");
        }

    }



    /******************
     * Replaced Methods
     ******************/

    private void displayGameNames() {
        // replaced displayGameNames() with displayAllGameData()
        System.out.println("Games in your collection: ");
        for (String name : boardGameDao.getGameNames()) {
            System.out.println("| " + name + " |");
        }
    }

    private void displayGameCount() {
        // Replaced displayGameCount() with displayGameTypeCount()
        System.out.println("Number of games: " + boardGameDao.getGameCount());
    }

    private void showSortedGames() {
        // Replaced with the more general printGameList() method
        BoardGame[] games = boardGameDao.sortGameNamesAscending().toArray(new BoardGame[0]);
        String header = String.format("| %-45s | %-20s | %-6s | %-16s | %-8s | %-15s | %-14s | %-14s | %-11s | %s",
                "Name", "Publisher", "Year", "Date Purchased", "Price", "Time to Teach", "Time to Play", "Player Count", "Game Type", "Description");
        System.out.println(header);
        for (int i = 0; i < games.length; i++) {
            String expansionDetail = "";
            if (games[0].expansion == true) {
                expansionDetail = "Expansion";
            } else expansionDetail = "Base Game";
            String individualGame = String.format("| %-45s | %-20s | %-6d | %-16s | $%-7.2f | %-15s | %-14s | %-14s | %-11s | %s",
                    games[i].getName(), games[i].getPublisher(), games[i].getYear_published(), games[i].getDate_purchased(), games[i].getPrice(),
                    games[i].getTime_to_teach_in_minutes(), games[i].getTime_to_play_in_minutes_min() + " - " + games[i].getTime_to_play_in_minutes_max(),
                    games[i].getMin_players() + " - " + games[i].getMax_players(), expansionDetail, games[i].getDescription());
            System.out.println(individualGame);
        }
    }

}