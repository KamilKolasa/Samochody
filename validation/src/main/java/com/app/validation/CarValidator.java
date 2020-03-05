package com.app.validation;

import com.app.model.Car;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {

    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {

        errors.clear();

        if (car == null) {
            errors.put("car", "car object is null");
        }

        if (!isModelValid(car)) {
            errors.put("model", "model is not correct: " + car.getModel());
        }

        if (!isPriceValid(car)) {
            errors.put("price", "price is not correct: " + car.getPrice());
        }

        if (!isColorValid(car)) {
            errors.put("color", "color is not correct: " + car.getColor());
        }

        if (!isMileageValid(car)) {
            errors.put("mileage", "mileage is not correct: " + car.getMileage());
        }

        if (!areComponentsValid(car)) {
            errors.put("components", "components is not correct: " + car.getComponents());
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("[A-Z ]+");
    }

    private boolean isPriceValid(Car car) {
        return car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isColorValid(Car car) {
        return car.getColor() != null;
    }

    private boolean isMileageValid(Car car) {
        return car.getMileage() != null && car.getMileage() >= 0;
    }

    private boolean areComponentsValid(Car car) {
        return car.getComponents() != null && car.getComponents().stream().allMatch(x -> x.matches("[A-Z ]+"));
    }
}
