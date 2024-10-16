package org.example.view;

import java.util.Scanner;

public class Menu {

    private static final Scanner userInput = new Scanner(System.in);


    public void showAppName() {
        System.out.printf("*****************************************\n" +
                "*                                       *\n" +
                "*  +-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+  *\n" +
                "*        |Board| |Game| |Corner|        *\n" +
                "*  +-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+  *\n" +
                "*                                       *\n" +
                "*****************************************");
    }


    public String showMainMenu() {
        System.out.println();
        System.out.println("1. Show Board Games");
        System.out.println("2. Add Board Game");
        System.out.println("3. Delete Board Game");
        System.out.println("4. Quit");
        System.out.println("-->");
        return userInput.nextLine();
    }



    public String showBoardGameListMenu() {
        System.out.println("1. Sort by xxx");
        System.out.println("2. Sort by xxx");
        System.out.println("3. Delete Board Game");
        System.out.println("4. Return to Main Menu");
        return userInput.nextLine();
    }


}
