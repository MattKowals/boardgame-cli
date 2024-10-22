package org.example.dao;

import org.example.exception.DaoException;
import org.example.models.BoardGame;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcBoardGameDao implements BoardGameDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardGameDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public int getGameCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM board_games";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            count = results.getInt("count");
        }
        return count;
    }

    @Override
    public List<BoardGame> getBoardGames() {
        List<BoardGame> gameCollection = new ArrayList<>();
        String sql = "SELECT game_id, game_name, publisher, year_published, date_purchased, price, time_to_teach_in_minutes, " +
                "time_to_play_in_minutes_min, time_to_play_in_minutes_max, min_players, max_players," +
                "expansion, description " +
                "From board_games";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            BoardGame boardGame = mapRowToGame(results);
            gameCollection.add(boardGame);
        }
        return gameCollection;
    }

    @Override
    public List<String> getGameNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT game_name FROM board_games";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            names.add(results.getString("game_name"));
        }
        return names;
    }

    public BoardGame getGameById(int game_id) {
        BoardGame game = null;
        String sql = "SELECT game_id, game_name, publisher, year_published, date_purchased, price, time_to_teach_in_minutes, " +
                "time_to_play_in_minutes_min, time_to_play_in_minutes_max, min_players, max_players," +
                "expansion, description " +
                "FROM board_games WHERE game_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, game_id);
            if (results.next()) {
                game = mapRowToGame(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        }
        return game;
    }

    @Override
    public void displayAllGamesData() {
        BoardGame[] games = getBoardGames().toArray(new BoardGame[0]);
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

    @Override
    public BoardGame addBoardGame(BoardGame boardGame) {
        BoardGame newGame = null;
        String sql = "INSERT into board_games (game_name, publisher, year_published, date_purchased, price, time_to_teach_in_minutes, " +
                "time_to_play_in_minutes_min, time_to_play_in_minutes_max, min_players, max_players," +
                "expansion, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING game_id";
        try {
            int newGameId = jdbcTemplate.queryForObject(sql, int.class,
                    boardGame.getName(), boardGame.getPublisher(), boardGame.getYear_published(), boardGame.getDate_purchased(),
                    boardGame.getPrice(), boardGame.getTime_to_teach_in_minutes(), boardGame.getTime_to_play_in_minutes_min(),
                    boardGame.getTime_to_play_in_minutes_max(), boardGame.min_players, boardGame.max_players, boardGame.isExpansion(),
                    boardGame.getDescription());

            newGame = getGameById(newGameId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newGame;
    }

    @Override
    public int deleteGameByName(String name) {
        int numberOfRows = 0;
        String sql = "DELETE FROM board_games WHERE game_name = ?;";
        try {
            numberOfRows = jdbcTemplate.update(sql, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        } catch (BadSqlGrammarException e) {
            throw new DaoException("SQL syntax error - Confirm name is exact", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    /***********************
     * CRUD Update/Edit functions
     ***********************/
    @Override
    public int updateGameNameByOldName(String oldName, String newName) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET game_name = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, newName, oldName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateGamePublisherByName(String name, String publisher) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET publisher = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, publisher, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateYearPublishedByName(String name, int year_published) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET year_published = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, year_published, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateDatePurchasedByName(String name, LocalDate date_purchased) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET date_purchased = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, date_purchased, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updatePriceByName(String name, double price) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET price = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, price, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateTimeToTeachByName(String name, int time_to_teach_in_minutes) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET time_to_teach_in_minutes = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, time_to_teach_in_minutes, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateTimeToPlayMinByName(String name, int time_to_play_in_minutes_min) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET time_to_play_in_minutes_min = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, time_to_play_in_minutes_min, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updateTimeToPlayMaxByName(String name, int time_to_play_in_minutes_max) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET time_to_play_in_minutes_max = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, time_to_play_in_minutes_max, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updatePlayersMinByName(String name, int min_players) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET min_players = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, min_players, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }

    @Override
    public int updatePlayersMaxByName(String name, int max_players) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET max_players = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, max_players, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }


    private boolean getExpansionStatus(String name) {
        String sql = "SELECT expansion FROM board_games WHERE game_name = ?";
        boolean expansionStatus = false;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
            while (results.next()) {
                expansionStatus = results.getBoolean("expansion");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return expansionStatus;
    }

    @Override
    public int updateExpansionStatusByName(String name) {
        int updatedRows = 0;
        boolean currentStatus = getExpansionStatus(name);
        if (currentStatus == true) {
            String sqlSetFalse = "UPDATE board_games SET expansion = false WHERE game_name = ?";
            try {
                updatedRows = jdbcTemplate.update(sqlSetFalse, name);
            } catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to database", e);
            }
        } else if (currentStatus == false) {
            String sqlSetTrue = "UPDATE board_games SET expansion = true WHERE game_name = ?";
            try {
                updatedRows = jdbcTemplate.update(sqlSetTrue, name);
            } catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to database", e);
            }
        }
        return updatedRows;
    }

    @Override
    public int updateDescriptionByName(String name, String description) {
        int updatedRows = 0;
        String sql = "UPDATE board_games SET description = ? WHERE game_name = ?";
        try {
            updatedRows = jdbcTemplate.update(sql, description, name);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database", e);
        }
        return updatedRows;
    }





    private BoardGame mapRowToGame(SqlRowSet rowSet) {
        BoardGame boardGame = new BoardGame();
        boardGame.setGame_id(rowSet.getInt("game_id"));
        boardGame.setName(rowSet.getString("game_name"));
        boardGame.setPublisher(rowSet.getString("publisher"));
        boardGame.setYear_published(rowSet.getInt("year_published"));
        boardGame.setDate_purchased(rowSet.getDate("date_purchased").toLocalDate());
        boardGame.setPrice(rowSet.getDouble("price"));
        boardGame.setTime_to_teach_in_minutes(rowSet.getInt("time_to_teach_in_minutes"));
        boardGame.setTime_to_play_in_minutes_min(rowSet.getInt("time_to_play_in_minutes_min"));
        boardGame.setTime_to_play_in_minutes_max(rowSet.getInt("time_to_play_in_minutes_max"));
        boardGame.setMin_players(rowSet.getInt("min_players"));
        boardGame.setMax_players(rowSet.getInt("max_players"));
        boardGame.setExpansion(rowSet.getBoolean("expansion"));
        boardGame.setDescription(rowSet.getString("description"));
        return boardGame;
    }

}
