package com.app.service;

import com.app.exceptions.MyException;
import com.app.model.SortCriterium;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {
    private Scanner scanner = new Scanner(System.in);

    public int getInt(String message) {
        System.out.println(message);

        String text = scanner.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException("INT VALUE IS NOT CORRECT: " + text);
        }
        return Integer.parseInt(text);
    }

    public String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public SortCriterium getSortCriterium() {
        System.out.println("1 - model");
        System.out.println("2 - color");
        System.out.println("3 - price");
        System.out.println("4 - mileage");
        System.out.println("Enter sort criterium");
        String text = scanner.nextLine();

        if (!text.matches("\\d")) {
            throw new MyException("INVALID SORT CRITERIUM NUMBER");
        }

        int option = Integer.parseInt(text);
        if (option < 1 || option > 4) {
            throw new MyException("NO SORT CRITERIUM WITH NUMBER " + option);
        }

        return SortCriterium.values()[option - 1];
    }

    public boolean getBoolean(String message, char yes, char no) {
        System.out.println(message + "[" + yes + " / " + no + "]");
        String text = scanner.nextLine();
        return text.charAt(0) == yes;
    }

    public int getMileage() {
        System.out.println("Enter min mileage");
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("MILEAGE IS NOT NUMBER");
        }

        int mileage = Integer.parseInt(text);
        if (mileage < 0) {
            throw new MyException("MILEAGE IS BELOW ZERO");
        }

        return mileage;
    }

    public BigDecimal getMinPrice() {
        System.out.println("Enter min price");
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("MIN PRICE IS NOT NUMBER");
        }

        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(text));
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MyException("PRICE IS BELOW ZERO");
        }

        return price;
    }

    public BigDecimal getMaxPrice(BigDecimal minPrice) {
        System.out.println("Enter max price");
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("MAX PRICE IS NOT NUMBER");
        }

        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(text));
        if (price.compareTo(minPrice) < 0) {
            throw new MyException("PRICE IS BELOW MIN PRICE");
        }

        return price;
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
