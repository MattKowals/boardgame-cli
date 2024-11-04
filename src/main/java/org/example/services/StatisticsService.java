package org.example.services;

import org.example.dao.BoardGameDao;

import java.util.*;

public class StatisticsService {

    BoardGameDao boardGameDao;

    public StatisticsService(BoardGameDao boardGameDao) {
        this.boardGameDao = boardGameDao;
    }

    public List<Map.Entry<String, Integer>> bubbleSortDescending(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
        int n = entryList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (entryList.get(j).getValue() < entryList.get(j + 1).getValue()) {
                    Collections.swap(entryList, j, j + 1);
                }
            }
        }
        return entryList;
    }

    public Map<String, Integer> numberOfGamesPerPublisher(List<String> publisherList) {
        Map<String, Integer> publisherCount = new HashMap<>();
        for (String publisher : publisherList) {
            if (publisherCount.containsKey(publisher)) {
                int currentValue = publisherCount.get(publisher);
                int newValue = currentValue + 1;
                publisherCount.put(publisher, newValue);
            } else {
                publisherCount.put(publisher, 1);
            }
        }
        return publisherCount;
    }

    public void bubbleSortGamesPerPublisher() {
        Map<String, Integer> publisherFullCount = numberOfGamesPerPublisher(boardGameDao.getPublisherList());
        List<Map.Entry<String, Integer>> totalCountMap = bubbleSortDescending(publisherFullCount);
        Map<String, Integer> publisherBaseGameCount = numberOfGamesPerPublisher(boardGameDao.getPublisherListForBaseGames());
        Map<String, Integer> publisherExpansionCount = numberOfGamesPerPublisher(boardGameDao.getPublisherListForExpansions());
        System.out.println();
        System.out.printf("%-18s | %-12s | %-5s | %-6s ", "Publisher", "Total Games", "Base", "Expansion");
        System.out.println();
        for (Map.Entry<String, Integer> entry : totalCountMap) {
            int baseCount = publisherBaseGameCount.get(entry.getKey());
            int expansionCount = 0;
            if (publisherExpansionCount.get(entry.getKey()) != null) {
                expansionCount = publisherExpansionCount.get(entry.getKey());
            }
            System.out.printf("%-18s | %-12s | %-5s | %-6s%n" , entry.getKey(), entry.getValue(), baseCount, expansionCount);
        }
    }

    public void displayTotalPrices() {
        double prices = boardGameDao.getGamePrices();
        System.out.println();
        System.out.println("Total prices of games collection: $" + prices);
    }

    public void displayGameTypeCount() {
        System.out.printf("| %-16s | %-10s | %-10s |%n", "Total Game Count", "Base Games", "Expansions");
        System.out.printf("| %-16s | %-10s | %-10s |%n", boardGameDao.getGameCount(), boardGameDao.getBaseGameCount(),
                boardGameDao.getExpansionGameCount());
    }


}
