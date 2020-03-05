package com.app.model;

import com.app.exceptions.MyException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Car {
    private String model;
    private BigDecimal price;
    private Colors color;
    private Integer mileage;
    private List<String> components;

    private Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.price = carBuilder.price;
        this.color = carBuilder.color;
        this.mileage = carBuilder.mileage;
        this.components = carBuilder.components;
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public static class CarBuilder {
        private String model;
        private BigDecimal price;
        private Colors color;
        private Integer mileage;
        private List<String> components;

        private static final String validationModel = "[A-Z ]+";
        private static final String validationComponents = "[A-Z ]+";
        private static final BigDecimal validationMinPrice = BigDecimal.ZERO;
        private static final int validationMinMileage = 0;

        public CarBuilder model(String model) {
            if (model == null || !model.matches(validationModel)) {
                throw new MyException("MODEL IS NOT CORRECT");
            }
            this.model = model;
            return this;
        }

        public CarBuilder price(BigDecimal price) {
            if (price == null || price.compareTo(validationMinPrice) < 0) {
                throw new MyException("PRICE IS NOT CORRECT");
            }
            this.price = price;
            return this;
        }

        public CarBuilder color(Colors color) {
            if (color == null) {
                throw new MyException("COLOR IS NOT CORRECT");
            }
            this.color = color;
            return this;
        }

        public CarBuilder mileage(Integer mileage) {
            if (mileage < validationMinMileage) {
                throw new MyException("MILEAGE IS NOT CORRECT");
            }
            this.mileage = mileage;
            return this;
        }

        public CarBuilder components(List<String> components) {
            if (components == null || !components.stream().allMatch(c -> c.matches(validationComponents))) {
                throw new MyException("COMPONENTS IS NOT CORRECT");
            }
            this.components = components;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

}
