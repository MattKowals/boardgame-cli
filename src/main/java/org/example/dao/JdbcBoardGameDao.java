package org.example.dao;

import org.example.exception.DaoException;
import org.example.models.BoardGame;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
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
        return null;
    }

    @Override
    public List<String> getGameNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT name FROM board_games";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            names.add(results.getString("name"));
        }
        return names;
    }

    public BoardGame getGameById(int game_id) {
        BoardGame game = null;
        String sql = "SELECT game_id, name, publisher, year_published, date_purchased, price, time_to_teach_in_minutes, " +
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
    public BoardGame addBoardGame(BoardGame boardGame) {
        BoardGame newGame = null;
        String sql = "INSERT into board_games (name, publisher, year_published, date_purchased, price, time_to_teach_in_minutes, " +
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





    private BoardGame mapRowToGame(SqlRowSet rowSet) {
        BoardGame boardGame = new BoardGame();
        boardGame.setGame_id(rowSet.getInt("game_id"));
        boardGame.setName(rowSet.getString("name"));
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
