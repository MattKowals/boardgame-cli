package org.example.view;

import java.util.Scanner;

public class Menu {

    private static final Scanner userInput = new Scanner(System.in);


    /*********
     * Banners
     *********/

    public void showAppName() {
        System.out.printf("*****************************************\n" +
                "*                                       *\n" +
                "*  +-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+  *\n" +
                "*        |Board| |Game| |Corner|        *\n" +
                "*  +-+-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+  *\n" +
                "*                                       *\n" +
                "*****************************************");
    }

    public void showCollectionBanner() {
        System.out.println("·····································································\n" +
                ":__   __                  ____      _ _           _   _             :\n" +
                ":\\ \\ / /__  _   _ _ __   / ___|___ | | | ___  ___| |_(_) ___  _ __  :\n" +
                ": \\ V / _ \\| | | | '__| | |   / _ \\| | |/ _ \\/ __| __| |/ _ \\| '_ \\ :\n" +
                ":  | | (_) | |_| | |    | |__| (_) | | |  __/ (__| |_| | (_) | | | |:\n" +
                ":  |_|\\___/ \\__,_|_|     \\____\\___/|_|_|\\___|\\___|\\__|_|\\___/|_| |_|:\n" +
                "·····································································");
    }

    public void showSortBanner() {
        System.out.println("··························································\n" +
                ": ____             _      ____                           :\n" +
                ":/ ___|  ___  _ __| |_   / ___| __ _ _ __ ___   ___  ___ :\n" +
                ":\\___ \\ / _ \\| '__| __| | |  _ / _` | '_ ` _ \\ / _ \\/ __|:\n" +
                ": ___) | (_) | |  | |_  | |_| | (_| | | | | | |  __/\\__ \\:\n" +
                ":|____/ \\___/|_|   \\__|  \\____|\\__,_|_| |_| |_|\\___||___/:\n" +
                "··························································");
    }

    public void showStatsBanner() {
        System.out.println("···········································\n" +
                ": ____  _        _   _     _   _          :\n" +
                ":/ ___|| |_ __ _| |_(_)___| |_(_) ___ ___ :\n" +
                ":\\___ \\| __/ _` | __| / __| __| |/ __/ __|:\n" +
                ": ___) | || (_| | |_| \\__ \\ |_| | (__\\__ \\:\n" +
                ":|____/ \\__\\__,_|\\__|_|___/\\__|_|\\___|___/:\n" +
                "···········································");
    }

    public void showAddGameBanner() {
        System.out.println("···················································\n" +
                ":    _       _     _    ____                      :\n" +
                ":   / \\   __| | __| |  / ___| __ _ _ __ ___   ___ :\n" +
                ":  / _ \\ / _` |/ _` | | |  _ / _` | '_ ` _ \\ / _ \\:\n" +
                ": / ___ \\ (_| | (_| | | |_| | (_| | | | | | |  __/:\n" +
                ":/_/   \\_\\__,_|\\__,_|  \\____|\\__,_|_| |_| |_|\\___|:\n" +
                "···················································");
    }

    public void showEditGameBanner() {
        System.out.println("·················································\n" +
                ": _____    _ _ _      ____                      :\n" +
                ":| ____|__| (_) |_   / ___| __ _ _ __ ___   ___ :\n" +
                ":|  _| / _` | | __| | |  _ / _` | '_ ` _ \\ / _ \\:\n" +
                ":| |__| (_| | | |_  | |_| | (_| | | | | | |  __/:\n" +
                ":|_____\\__,_|_|\\__|  \\____|\\__,_|_| |_| |_|\\___|:\n" +
                "·················································");
    }

    public void showDeleteGameBanner() {
        System.out.println("··························································\n" +
                ": ____       _      _          ____                      :\n" +
                ":|  _ \\  ___| | ___| |_ ___   / ___| __ _ _ __ ___   ___ :\n" +
                ":| | | |/ _ \\ |/ _ \\ __/ _ \\ | |  _ / _` | '_ ` _ \\ / _ \\:\n" +
                ":| |_| |  __/ |  __/ ||  __/ | |_| | (_| | | | | | |  __/:\n" +
                ":|____/ \\___|_|\\___|\\__\\___|  \\____|\\__,_|_| |_| |_|\\___|:\n" +
                "··························································");
    }

    /*******
     * Menus
     *******/

    public String showMainMenu() {
        System.out.println();
        System.out.println("1. Show Board Games");
        System.out.println("2. Sort Games");
        System.out.println("3. Game Collection Statistics");
        System.out.println("4. Add Board Game");
        System.out.println("5. Edit Board Game");
        System.out.println("6. Delete Board Game");
        System.out.println("7. Quit");
        System.out.println("9. Export Database to CSV File");
        System.out.println("0. Get a random game");
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
