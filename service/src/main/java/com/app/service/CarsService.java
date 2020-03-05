package com.app.service;

import com.app.conventers.CarsJsonConverter;
import com.app.exceptions.MyException;
import com.app.model.Car;
import com.app.model.Colors;
import com.app.model.SortCriterium;
import com.app.validation.CarValidator;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CarsService {

    private final List<Car> cars;

    public CarsService(String filename) {
        this.cars = getCarsFromFile(filename);
    }

    private List<Car> getCarsFromFile(String filename) {
        if (filename == null) {
            throw new MyException("FILENAME IS NULL");
        }

        CarValidator carValidator = new CarValidator();

        return new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new MyException("CAR SERVICE - JSON CONVERSION"))
                .stream()
                .filter(car -> {
                    if (carValidator.hasErrors()) {
                        System.out.println("ERRORS FOR CAR: " + car);
                    }
                    carValidator.validate(car).forEach((k, v) -> System.out.println(k + " " + v));
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toList());
    }

    public List<Car> sort(SortCriterium sortCriterium, Boolean descending) {
        if (sortCriterium == null || descending == null) {
            throw new MyException("CRITERIUM SORT IS NOT CORRECT");
        }

        Stream<Car> carStream;
        switch (sortCriterium) {
            case COLOR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
                break;
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
                break;
            case MILEAGE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
                break;
            default: // price
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
        }

        List<Car> sortedCars = carStream.collect(Collectors.toList());

        if (descending) {
            Collections.reverse(sortedCars);
        }

        return sortedCars;
    }


    public List<Car> minMileage(Integer mileage) {
        if (mileage == null || mileage < 0) {
            throw new MyException("MILEAGE IS NOT CORRECT");
        }
        return cars
                .stream()
                .filter(x -> x.getMileage() > mileage)
                .collect(Collectors.toList());
    }

    public Map<Colors, Long> colorCar() {
        return cars
                .stream()
                .map(Car::getColor)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    public Map<String, Car> mostExpensiveModel() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().max(Comparator.comparing(Car::getPrice)).orElseThrow(IllegalStateException::new)
                ));
    }


    public void statistics() {
        DoubleSummaryStatistics mileageStatistics = cars.stream().collect(Collectors.summarizingDouble(Car::getMileage));
        System.out.println("MIN MILEAGE: " + mileageStatistics.getMin());
        System.out.println("MAX MILEAGE: " + mileageStatistics.getMax());
        System.out.println("AVG MILEAGE: " + mileageStatistics.getAverage());

        BigDecimalSummaryStatistics priceStatistics = cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice));
        System.out.println("MIN PRICE: " + priceStatistics.getMin());
        System.out.println("MAX PRICE: " + priceStatistics.getMax());
        System.out.println("AVG PRICE: " + priceStatistics.getAverage());
    }

    public List<Car> maxPrice() {
        return cars
                .stream()
                .max(Comparator.comparing(Car::getPrice))
                .flatMap(car -> Optional.of(cars.stream().filter(c -> c.getPrice().compareTo(car.getPrice()) == 0)))
                .orElseThrow(() -> new MyException("EXCEPTION WHILE GET CARS WITH MAX PRICE"))
                .collect(Collectors.toList());
    }

    public List<Car> sortComponent() {
        return cars
                .stream()
                .peek(c -> c.setComponents(c.getComponents().stream().sorted().collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public Map<String, List<Car>> componentCar() {
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    public List<Car> price(BigDecimal priceMin, BigDecimal priceMax) {
        if (priceMin == null || priceMax == null || priceMin.compareTo(priceMax) >= 0) {
            throw new MyException("PRICE IS NOT CORRECT");
        }

        return cars
                .stream()
                .filter(x -> x.getPrice().compareTo(priceMin) >= 0 && x.getPrice().compareTo(priceMax) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cars
                .stream()
                .map(c -> c.getModel() + " " + c.getColor() + " price: " + c.getPrice() + " mileage: " + c.getMileage() + " " + c.getComponents())
                .collect(Collectors.joining("\n"));
    }
}
