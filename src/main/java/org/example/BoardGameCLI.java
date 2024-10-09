package org.example;


import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BoardGameDao;
import org.example.view.Menu;

import javax.sql.DataSource;
import java.util.Scanner;

public class BoardGameCLI {

    private Menu menu;
    private BoardGameDao boardGameDao;

    private Scanner input;

    public BoardGameCLI(Menu menu) {
        this.menu = menu;
    }

    public BoardGameCLI(DataSource dataSource) {
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/boardgame_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        BoardGameCLI app = new BoardGameCLI(dataSource);
        app.run();
    }


    // App starts here!
    public void run() {

        menu.showAppName();

        // TODO add a try catch


        while (true) {


            String userSelection = menu.showMainMenu();
            if (userSelection.equals("1")) {
                System.out.println("Selected 1 - Show board games");
            } else if (userSelection.equals("2")) {
                System.out.println("Selected 2 - Add game");
            } else if (userSelection.equals("3")) {
                System.out.println("Selected 3 - Delete game");
            } else if (userSelection.equals("4")) {
                break;
            }

        }

    }



    // makes ApplicationCLI runnable
    public static void main(String[] args) {
        Menu menu = new Menu();
        BoardGameCLI cli = new BoardGameCLI(menu);
        cli.run();
    }

}