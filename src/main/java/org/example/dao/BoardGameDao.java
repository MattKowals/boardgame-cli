package org.example.dao;

import org.example.models.BoardGame;

import java.time.LocalDate;
import java.util.List;

public interface BoardGameDao {

    List<BoardGame> sortGameNamesAscending();

    List<BoardGame> sortGamesByMinTimeToPlay();

    List<BoardGame> sortByBaseGameAndName();

    List<BoardGame> chooseNumberOfPlayers(int count);

    int getGameCount();
    List<String> getGameNames();

    int getBaseGameCount();

    int getExpansionGameCount();

    List<BoardGame> getBoardGames();

    List<String> getPublisherList();

    List<String> getPublisherListForBaseGames();

    List<String> getPublisherListForExpansions();

    double getGamePrices();

    void displayAllGamesData();

    BoardGame addBoardGame(BoardGame boardGame);

    int deleteGameByName(String name);

    int updateGameNameByOldName(String oldName, String newName);

    int updateGamePublisherByName(String name, String publisher);

    int updateYearPublishedByName(String name, int year_published);

    int updateDatePurchasedByName(String name, LocalDate date_purchased);

    int updatePriceByName(String name, double price);

    int updateTimeToTeachByName(String name, int time_to_teach);

    int updateTimeToPlayMinByName(String name, int time_to_play_in_minutes_min);

    int updateTimeToPlayMaxByName(String name, int time_to_play_in_minutes_min);

    int updatePlayersMinByName(String name, int min_players);

    int updatePlayersMaxByName(String name, int max_players);

    int updateExpansionStatusByName(String name);

    int updateDescriptionByName(String name, String description);
}
