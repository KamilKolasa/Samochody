package com.app.service;

import com.app.exceptions.MyException;
import com.app.model.SortCriterium;

import java.math.BigDecimal;

public class MenuService {

    private final CarsService carsService;
    private final UserDataService userDataService;

    public MenuService(String filename) {
        carsService = new CarsService(filename);
        userDataService = new UserDataService();
    }

    public void mainMenu() {
        while (true) {
            try {

                printMenu();
                int option = userDataService.getInt("Enter option number");

                switch (option) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;
                    case 10:
                        option10();
                        break;
                    case 11:
                        userDataService.close();
                        return;
                    default:

                }

            } catch (MyException e) {
                System.out.println("\n\n=========================== EXCEPTION ============================");
                System.out.println(e.getMessage());
                System.out.println("==================================================================\n\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Print");
        System.out.println("2. Sort");
        System.out.println("3. Cars with a high mileage");
        System.out.println("4. Count cars in color");
        System.out.println("5. The most expensive model");
        System.out.println("6. Statistics");
        System.out.println("7. The most expensive Car/s");
        System.out.println("8. Sort components");
        System.out.println("9. Cars with component");
        System.out.println("10. Car price range");
        System.out.println("11. Close");
    }

    private void option1() {
        System.out.println(carsService.toString());
    }

    private void option2() {
        SortCriterium criterium = userDataService.getSortCriterium();
        boolean descending = userDataService.getBoolean("Descending", 'y', 'n');
        carsService.sort(criterium, descending).forEach(System.out::println);
    }

    private void option3() {
        Integer mileage = userDataService.getMileage();
        carsService.minMileage(mileage).forEach(System.out::println);
    }

    private void option4() {
        carsService.colorCar().forEach((c, n) -> System.out.println(c + " - " + n + " car/s"));
    }

    private void option5() {
        carsService.mostExpensiveModel().forEach((m, c) -> System.out.println(m + " - the most expensive model: " + c));
    }

    private void option6() {
        System.out.println("statistics:");
        carsService.statistics();
    }

    private void option7() {
        System.out.print("The most expensive car/s: ");
        carsService.maxPrice().forEach(System.out::println);
    }


    private void option8() {
        carsService.sortComponent().forEach(System.out::println);
    }

    private void option9() {
        carsService.componentCar().forEach((x, y) -> System.out.println(x + " - " + y));
    }

    private void option10() {
        BigDecimal minPrice = userDataService.getMinPrice();
        BigDecimal maxPrice = userDataService.getMaxPrice(minPrice);
        carsService.price(minPrice, maxPrice).forEach(System.out::println);
    }
}