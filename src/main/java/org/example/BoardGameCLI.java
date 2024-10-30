package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.dao.JdbcBoardGameDao;
import org.example.models.BoardGame;
import org.example.services.CrudService;
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
    private CrudService crudService;


    public BoardGameCLI(DataSource dataSource) {
        boardGameDao = new JdbcBoardGameDao(dataSource);
        this.menu = new Menu();
        this.prompt = new Prompts();
    }
    
    public static void main(String[] args) {

        // Connect to database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/boardgames_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        BoardGameCLI cli = new BoardGameCLI(dataSource);
        cli.run();
    }


    public void run() {

        menu.showAppName();

        while (true) {

            String userSelection = menu.showMainMenu();
            if (userSelection.equals("1")) {
                menu.showCollectionBanner();
                sortingService.printGameList(boardGameDao.getBoardGames());
            } else if (userSelection.equals("2")) {
                sortingService.printGameList(boardGameDao.sortByBaseGameAndName());
                sortingService.pickNumberOfPlayers();
            } else if (userSelection.equals("3")) {
                menu.showStatsBanner();
                System.out.println();
                statisticsService.displayGameTypeCount();
                statisticsService.displayTotalPrices();
                statisticsService.bubbleSortGamesPerPublisher();
            } else if (userSelection.equals("4")) {
                menu.showAddGameBanner();
                crudService.addNewGame();
            } else if (userSelection.equals("5")) {
                menu.showEditGameBanner();
                crudService.editGame();
            } else if (userSelection.equals("6")) {
                menu.showDeleteGameBanner();
                crudService.promptForDeleteGame();
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




}