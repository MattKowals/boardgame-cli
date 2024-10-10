package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.dao.JdbcBoardGameDao;
import org.example.view.Menu;

import javax.sql.DataSource;
import java.util.Scanner;

public class BoardGameCLI {

    private Menu menu;
    private BoardGameDao boardGameDao;

    private Scanner userInput = new Scanner(System.in);

    public BoardGameCLI(DataSource dataSource, Menu menu) {
        boardGameDao = new JdbcBoardGameDao(dataSource);
        this.menu = menu;
    }


    // makes ApplicationCLI runnable
    public static void main(String[] args) {
        Menu menu = new Menu();


        // Connect to database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/boardgames_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Beta150@");   // TODO: 10/10/2024 Hide password

        BoardGameCLI cli = new BoardGameCLI(dataSource, menu);
        cli.run();
    }


    // App starts here!
    public void run() {

        menu.showAppName();

        // TODO add a try catch


        while (true) {


            String userSelection = menu.showMainMenu();
            if (userSelection.equals("1")) {
                System.out.println("Selected 1 - Show board games");
                displayGameCount();
                displayGameNames();
            } else if (userSelection.equals("2")) {
                System.out.println("Selected 2 - Add game");
            } else if (userSelection.equals("3")) {
                System.out.println("Selected 3 - Delete game");
            } else if (userSelection.equals("4")) {
                break;
            }

        }

    }

    private void displayGameCount() {
        System.out.println("Number of games: " + boardGameDao.getGameCount());
        System.out.println();
    }

    private void displayGameNames() {
        System.out.println("Games in your collection: ");
        for (String name : boardGameDao.getGameNames()) {
            System.out.println(name);
        }
    }


}