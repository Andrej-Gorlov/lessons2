package Domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @param name   Наименование
 * @param type   Тип
 * @param volume Объем тары (литры)
 * @param price  Цена
 */ // Напиток
public record Drink(String name, String type, double volume, BigDecimal price) {
    public Drink(String name, String type, double volume, BigDecimal price) {
        this.name = Objects.requireNonNull(name, "Наименование не может быть пустым");
        this.type = Objects.requireNonNull(type, "Тип не может быть пустым");
        this.volume = volume;
        this.price = Objects.requireNonNull(price, "Цена не может быть пустой")
                .setScale(2, RoundingMode.HALF_UP); // Установка масштаба для цены

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
    }
}
