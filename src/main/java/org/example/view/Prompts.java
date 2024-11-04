package org.example.view;

import org.example.dao.BoardGameDao;
import org.example.models.BoardGame;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Prompts {

    public static final Scanner userInput = new Scanner(System.in);



    public String promptForString(String prompt) {
        System.out.println(prompt);
        return userInput.nextLine();
    }

    public double promptForDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            String response = userInput.nextLine();
            try {
                return Double.parseDouble(response);
            } catch (NumberFormatException e) {
                if (response.isBlank()) {
                    return -1;
                } else {
                    displayError("**Numbers only**");
                }
            }
        }
    }

    public int promptForInt(String prompt) {
        return (int) promptForDouble(prompt);
    }

    public LocalDate promptForDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String response = userInput.nextLine();
            try {
                return LocalDate.parse(response);
            } catch (DateTimeParseException e) {
                if (response.isBlank()) {
                    return null;
                } else {
                    displayError("**Enter date as YYYY-MM-DD**");
                }
            }
        }
    }

    public void displayError(String message) {
        System.out.println();
        System.out.println(message);
    }



}
