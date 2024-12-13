package Service.Helpers;

import Domain.Drink;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DrinkStore {
    private static final Map<Integer, Drink> drinks = new HashMap<>();

    static {
        drinks.put(1, new Drink("Святой источник","Вода", 5.0, new BigDecimal("114.99")));
        drinks.put(2, new Drink("Шишкин лес","Вода", 1.75, new BigDecimal("67.99")));
        drinks.put(3, new Drink("Ессентуки","Вода минеральная", 0.450, new BigDecimal("77.99")));
        drinks.put(4, new Drink("Боржоми","Вода минеральная", 0.5, new BigDecimal("124.99")));
        drinks.put(5, new Drink("Rich апельсин-манго","Сок",0.900,new BigDecimal("159.99")));
        drinks.put(6, new Drink("Сады Придонья мультифруктовый","Сок",1.0, new BigDecimal("109.99")));
    }

    public static Map<Integer, Drink> getDrinks() {
        return drinks;
    }
}
