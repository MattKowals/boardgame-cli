package org.example.services;

import org.example.dao.BoardGameDao;
import org.example.models.BoardGame;
import org.example.view.Menu;
import org.example.view.Prompts;

import java.time.LocalDate;

public class CrudService {

    private BoardGameDao boardGameDao;
    private Menu menu;
    private Prompts prompt;

    public CrudService(BoardGameDao boardGameDao) {
        this.boardGameDao = boardGameDao;
        this.menu = new Menu();
        this.prompt = new Prompts();
    }

    public void addNewGame() {
        BoardGame newBoarGame = promptForNewGameData();
        newBoarGame = boardGameDao.addBoardGame(newBoarGame);
    }

    public BoardGame promptForNewGameData() {
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

    public void deleteGame(String game_name) {
        int deletedRows = boardGameDao.deleteGameByName(game_name);
        if (deletedRows == 0) {
            prompt.displayError("**Error in deleting game**");
        } else System.out.println("Game successfully deleted");
    }

    public void promptForDeleteGame() {
        String game_name = prompt.promptForString("Enter the name of the game to delete: ");
        deleteGame(game_name);
    }

    public void editGame() {
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

}
