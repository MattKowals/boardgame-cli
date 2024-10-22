package org.example.view;

import org.example.dao.BoardGameDao;
import org.example.models.BoardGame;

import java.util.Scanner;

public class Prompts {

    public BoardGameDao boardGameDao;

    public static final Scanner userInput = new Scanner(System.in);

    public void displayGameNames() {
        System.out.println("Games in your collection: ");
        for (String name : boardGameDao.getGameNames()) {
            System.out.println(name);
        }
    }


}
