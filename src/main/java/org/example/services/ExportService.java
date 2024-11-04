package org.example.services;

import org.example.dao.BoardGameDao;
import org.example.models.BoardGame;
import org.example.view.Prompts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService {

    private BoardGameDao boardGameDao;
    private Prompts prompt;

    public ExportService(BoardGameDao boardGameDao) {
        this.boardGameDao = boardGameDao;
        this.prompt = new Prompts();
    }

    public String promptForExportDatabase() {
        String csvFileName = prompt.promptForString("What do you want to call the new file: ") + ".csv";
        return csvFileName;
    }

    public void exportDatabaseToFile(String filePath) {
        String success = "Database not exported";
        List<BoardGame> boardGames = boardGameDao.getBoardGames();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("game_id,name,publisher,year_published,date_purchased,price,time_to_teach_in_minutes,time_to_play_in_minutes_min,time_to_play_in_minutes_max,min_players,max_players,expansion,description");
            writer.newLine();
            for (BoardGame game : boardGames) {
                writer.write(game.getGame_id() + "," +
                        game.getName() + "," +
                        game.getPublisher() + "," +
                        game.getYear_published() + "," +
                        game.getDate_purchased() + "," +
                        game.getPrice() + "," +
                        game.getTime_to_teach_in_minutes() + "," +
                        game.getTime_to_play_in_minutes_min() + "," +
                        game.getTime_to_play_in_minutes_max() + "," +
                        game.getMin_players() + "," +
                        game.getMax_players() + "," +
                        game.isExpansion() + "," +
                        game.getDescription());
                writer.newLine();
            }
            success = "Database exported to file: " + filePath;

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(success);
    }

}
