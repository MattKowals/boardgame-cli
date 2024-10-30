package org.example.services;

import org.example.dao.BoardGameDao;
import org.example.models.BoardGame;
import org.example.view.Prompts;

import java.time.LocalDate;
import java.util.List;

public class SortingService {

    private BoardGameDao boardGameDao;
    private Prompts prompt;

    public void pickNumberOfPlayers() {
        System.out.println();
        int count = prompt.promptForInt("How many players?");
        List<BoardGame> games = boardGameDao.chooseNumberOfPlayers(count);
        printGameList(games);
    }

    public void printGameList(List<BoardGame> gamesList) {
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

}
