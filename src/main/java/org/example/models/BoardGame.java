package org.example.models;

import java.time.LocalDate;
import java.util.Date;

public class BoardGame {

    /***********
     * Variables
     ***********/
    public int game_id;
    public String name;
    public String publisher;
    public int year_published;
    public LocalDate date_purchased;
    public double price;
    public int time_to_teach_in_minutes;
    public int time_to_play_in_minutes_min;
    public int time_to_play_in_minutes_max;
    public int min_players;
    public int max_players;
    public boolean expansion;
    public String description;



    /**************
     * Constructors
     **************/
    public BoardGame(int game_id, String name, String publisher,
                     int year_published, LocalDate date_purchased,
                     double price, int time_to_teach_in_minutes,
                     int time_to_play_in_minutes_min,
                     int time_to_play_in_minutes_max, int min_players,
                     int max_players, boolean expansion,
                     String description) {
        this.game_id = game_id;
        this.name = name;
        this.publisher = publisher;
        this.year_published = year_published;
        this.date_purchased = date_purchased;
        this.price = price;
        this.time_to_teach_in_minutes = time_to_teach_in_minutes;
        this.time_to_play_in_minutes_min = time_to_play_in_minutes_min;
        this.time_to_play_in_minutes_max = time_to_play_in_minutes_max;
        this.min_players = min_players;
        this.max_players = max_players;
        this.expansion = expansion;
        this.description = description;
    }

    public BoardGame() {

    }

    /**********************
     * Getters and Setters
     * ********************/

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public LocalDate getDate_purchased() {
        return date_purchased;
    }

    public void setDate_purchased(LocalDate date_purchased) {
        this.date_purchased = date_purchased;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTime_to_teach_in_minutes() {
        return time_to_teach_in_minutes;
    }

    public void setTime_to_teach_in_minutes(int time_to_teach_in_minute) {
        this.time_to_teach_in_minutes = time_to_teach_in_minute;
    }

    public int getTime_to_play_in_minutes_min() {
        return time_to_play_in_minutes_min;
    }

    public void setTime_to_play_in_minutes_min(int time_to_play_in_minutes_min) {
        this.time_to_play_in_minutes_min = time_to_play_in_minutes_min;
    }

    public int getTime_to_play_in_minutes_max() {
        return time_to_play_in_minutes_max;
    }

    public void setTime_to_play_in_minutes_max(int time_to_play_in_minutes_max) {
        this.time_to_play_in_minutes_max = time_to_play_in_minutes_max;
    }

    public int getMin_players() {
        return min_players;
    }

    public void setMin_players(int min_players) {
        this.min_players = min_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /************
     * To Strings
     ************/

    @Override
    public String toString() {
        return "BoardGame{" +
                "game_id=" + game_id +
                ", name='" + name + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year_published=" + year_published +
                ", date_purchased=" + date_purchased +
                ", price=" + price +
                ", time_to_teach_in_minutes=" + time_to_teach_in_minutes +
                ", time_to_play_in_minutes_min=" + time_to_play_in_minutes_min +
                ", time_to_play_in_minutes_max=" + time_to_play_in_minutes_max +
                ", min_players=" + min_players +
                ", max_players=" + max_players +
                ", expansion=" + expansion +
                ", description='" + description + '\'' +
                '}';
    }

//    @Override
//    public String toString() {
//        return "BoardGame{" +
//                "game_id=" + game_id +
//                ", Name='" + name + '\'' +
//                ", Publisher='" + publisher + '\'' +
//                ", Year Published=" + year_published +
//                ", Date Purchased=" + date_purchased +
//                ", Price=" + price +
//                ", Time to teach (minutes)=" + time_to_teach_in_minutes +
//                ", Minimum time to play (minutes)=" + time_to_play_in_minutes_min +
//                ", Maximum time to play (minutes)=" + time_to_play_in_minutes_max +
//                ", Minimum number of players=" + min_players +
//                ", Maximum number of players=" + max_players +
//                ", Is this an expansion=" + expansion +
//                ", Description='" + description + '\'' +
//                '}';
//    }


}
