package org.example.dao;

import org.example.models.BoardGame;

import java.util.List;

public interface BoardGameDao {

    int getGameCount();
    List<String> getGameNames();

    List<BoardGame> getBoardGames();





}
