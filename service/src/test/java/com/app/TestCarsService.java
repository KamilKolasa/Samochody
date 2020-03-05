package com.app;

import com.app.exceptions.MyException;
import com.app.model.Colors;
import com.app.model.SortCriterium;
import com.app.service.CarsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@MockitoSettings
public class TestCarsService {

    private static CarsService carsService;

    @BeforeAll
    public static void fileData(){
        carsService = new CarsService("src/test/resources/dataTest.json");
    }

    @Test
    public void mostExpensiveModel1() {
        var cars = carsService.mostExpensiveModel();
        Assertions.assertEquals(3, cars.size(), "Test mostExpensiveModel 1 is failed");
    }

    @Test
    @DisplayName("price1 - when there are cars inside the price range")
    public void price1() {
        var cars = carsService.price(BigDecimal.valueOf(50000), BigDecimal.valueOf(100000));
        Assertions.assertEquals(2, cars.size(), "Test price 1.1 is failed");
        Assertions.assertEquals("CITROEN", cars.get(0).getModel(), "Test price 1.2 is failed");
    }

    @Test
    @DisplayName("price2 - when there are no cars inside the price range")
    public void price2() {
        var cars = carsService.price(BigDecimal.valueOf(100), BigDecimal.valueOf(500));
        Assertions.assertTrue(cars.isEmpty(), "Test price 2 is failed");
    }

    @Test
    @DisplayName("price3 - when the price range is not correct")
    public void price3() {
        var error = Assertions.assertThrows(
                MyException.class,
                () -> carsService.price(BigDecimal.valueOf(500), BigDecimal.valueOf(100)));

        Assertions.assertEquals("PRICE IS NOT CORRECT", error.getMessage());
    }

    @Test
    @DisplayName("price4 - when arguments are null")
    public void price4() {
        var error1 = Assertions.assertThrows(
                MyException.class,
                () -> carsService.price(null, BigDecimal.valueOf(10)));
        var error2 = Assertions.assertThrows(
                MyException.class,
                () -> carsService.price(BigDecimal.valueOf(10), null));

        Assertions.assertEquals("PRICE IS NOT CORRECT", error1.getMessage());
        Assertions.assertEquals("PRICE IS NOT CORRECT", error2.getMessage());
    }

    @Test
    public void maxPrice1(){
        var cars = carsService.maxPrice();
        Assertions.assertEquals(1, cars.size(), "Test maxPrice 1.1 is failed");
        Assertions.assertEquals("CITROEN", cars.get(0).getModel(), "Test maxPrice 1.2 is failed");
    }

    @Test
    @DisplayName("minMileage1 - when there are cars inside the mileage range")
    public void minMileage1() {
        var cars = carsService.minMileage(70000);
        Assertions.assertEquals(2, cars.size(), "Test minMileage 1.1 is failed");
        Assertions.assertEquals("AUDI", cars.get(0).getModel(), "Test minMileage 1.2 is failed");
    }

    @Test
    @DisplayName("minMileage2 - when there are not cars inside the mileage range")
    public void minMileage2() {
        var cars = carsService.minMileage(100000000);
        Assertions.assertTrue(cars.isEmpty(), "Test minMileage 2 is failed");
    }

    @Test
    @DisplayName("minMileage3 - when the price range is not correct")
    public void minMileage3() {
        var error = Assertions.assertThrows(
                MyException.class,
                () -> carsService.minMileage(-10));

        Assertions.assertEquals("MILEAGE IS NOT CORRECT", error.getMessage());
    }

    @Test
    @DisplayName("minMileage4 - when argument is null")
    public void minMileage4() {
        var error = Assertions.assertThrows(
                MyException.class,
                () -> carsService.minMileage(null));

        Assertions.assertEquals("MILEAGE IS NOT CORRECT", error.getMessage());
    }

    @Test
    @DisplayName("sort1 - checking for proper sorting")
    public void sort1() {
        var cars1 = carsService.sort(SortCriterium.COLOR, true);
        var cars2 = carsService.sort(SortCriterium.COLOR, false);
        var cars3 = carsService.sort(SortCriterium.MODEL, false);
        var cars4 = carsService.sort(SortCriterium.MILEAGE, false);
        var cars5 = carsService.sort(SortCriterium.PRICE, false);
        Assertions.assertEquals("CITROEN", cars1.get(0).getModel(), "Test sort 1.1 is failed");
        Assertions.assertEquals("SKODA", cars2.get(0).getModel(), "Test sort 1.2 is failed");
        Assertions.assertEquals("AUDI", cars3.get(0).getModel(), "Test sort 1.3 is failed");
        Assertions.assertEquals("CITROEN", cars4.get(0).getModel(), "Test sort 1.4 is failed");
        Assertions.assertEquals("CITROEN", cars5.get(0).getModel(), "Test sort 1.5 is failed");
    }

    @Test
    @DisplayName("sort2 - when arguments are null")
    public void sort2() {
        var error1 = Assertions.assertThrows(
                MyException.class,
                () -> carsService.sort(null, false));
        var error2 = Assertions.assertThrows(
                MyException.class,
                () -> carsService.sort(SortCriterium.COLOR, null));

        Assertions.assertEquals("CRITERIUM SORT IS NOT CORRECT", error1.getMessage());
        Assertions.assertEquals("CRITERIUM SORT IS NOT CORRECT", error2.getMessage());
    }

    @Test
    public void componentCar1() {
        var cars = carsService.componentCar();
        Assertions.assertEquals(7, cars.size(), "Test componentCar 1.1 is failed");
        Assertions.assertEquals(3, cars.get("MASSAGE ARMCHAIRS").size(), "Test componentCar 1.2 is failed");
    }

    @Test
    public void colorCar1() {
        var cars = carsService.colorCar();
        Assertions.assertEquals(2, cars.get(Colors.GREEN).longValue(), "Test colorCar 1 is failed");
    }
}