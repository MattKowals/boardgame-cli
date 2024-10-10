package org.example.dao;

import org.example.models.BoardGame;
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

    private BoardGame mapRowToGame(SqlRowSet rowSet) {
        BoardGame boardGame = new BoardGame();
        boardGame.setGame_id(rowSet.getInt("game_id"));
        boardGame.setName(rowSet.getString("name"));
        boardGame.setPublisher(rowSet.getString("publisher"));
        boardGame.setYear_published(rowSet.getInt("year_published"));
        boardGame.setDate_purchased(rowSet.getDate("date_purchased"));
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
