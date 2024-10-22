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
        System.out.println("2. Game Collection Statistics");
        System.out.println("3. Add Board Game");
        System.out.println("4. Edit Board Game");
        System.out.println("5. Delete Board Game");
        System.out.println("6. Quit");
        System.out.print("--> ");
        return userInput.nextLine();
    }



    public String showBoardGameListMenu() {
        System.out.println();
        System.out.println();
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by xxx");
        System.out.println("3. Delete Board Game");
        System.out.println("4. Return to Main Menu");
        return userInput.nextLine();
    }

    public String editGameChoiceMenu() {
        System.out.println();
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Publisher");
        System.out.println("3. Year Published");
        System.out.println("4. Date Purchased");
        System.out.println("5. Price");
        System.out.println("6. Time to teach the game (in minutes)");
        System.out.println("7. Minimum time to play (in minutes)");
        System.out.println("8. Maximum time to play (in minutes)");
        System.out.println("9. Minimum number of players");
        System.out.println("10. Maximum number of players");
        System.out.println("11. Switch expansion status");
        System.out.println("12. Description");
        return userInput.nextLine();
    }

}
