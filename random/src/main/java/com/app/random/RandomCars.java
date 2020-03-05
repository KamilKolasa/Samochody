package com.app.random;

import com.app.conventers.CarsJsonConverter;
import com.app.model.Car;
import com.app.model.Colors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCars {

    private List<Car> cars = new ArrayList<>();

    public RandomCars(String filename) {
        CarsJsonConverter carsJsonConverter = new CarsJsonConverter(filename);
        carsJsonConverter.toJson(get());
    }

    private static List<Car> get() {
        Random rnd = new Random();
        int size = rnd.nextInt(8) + 3;
        List<Car> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateCar());
        }
        return result;
    }

    private static Car generateCar() {
        Random rnd = new Random();
        return new Car.CarBuilder().model(generateModel()).mileage(rnd.nextInt(100001)).color(generateColor()).price(BigDecimal.valueOf(rnd.nextInt(809001) + 1000)).components(generateComponents()).build();
    }

    private static Colors generateColor() {
        Random rnd = new Random();
        Colors[] color = Colors.values();
        return color[rnd.nextInt(color.length)];
    }

    private static String generateModel() {
        Random rnd = new Random();
        String[] models = {"BMW", "MERCEDES", "VOLKSWAGEN", "AUDI", "SEAT", "SKODA", "RENAULT", "FORD", "OPEL", "CITROEN", "PEUGEOT"};
        return models[rnd.nextInt(models.length)];
    }

    private static List<String> generateComponents() {
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        List<Integer> listRandom = new ArrayList<>();
        String[] components = {"RADIO", "AIR CONDITIONING", "ABS", "GPS", "PARKING SUPPORT", "PLAYERS DVD", "HEATED STEERING WHEEL", "AUTOMATIC TRANSMISSION", "ALARM", "MASSAGE ARMCHAIRS"};
        int i = 0;
        do {
            int random = rnd.nextInt(components.length);
            if (!listRandom.contains(random)) {
                listRandom.add(random);
                result.add(components[random]);
                ++i;
            }
        } while (i < rnd.nextInt(3) + 2);
        return result;
    }
}
