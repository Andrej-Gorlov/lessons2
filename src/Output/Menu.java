package Output;

import java.util.Map;
import Domain.Drink;
import Service.Helpers.DrinkStore;
import Service.Implementations.VendingMachine;

public class Menu {

    private static final Map<Integer, Drink> drinks = DrinkStore.getDrinks();

    /**
     * Отображение списка доступных напитков и текущий баланс.
     *
     * Формат вывода:
     * 1) НазваниеНапитка - ТипНапитка
     *    Объем - Цена рублей
     *
     * Баланс: 00 рублей
     */
    public static void display(){
        int index = 1; // Индекс номера напитка
        for (Map.Entry<Integer, Drink> entry : drinks.entrySet()) {
            Drink drink = entry.getValue(); // Получаем объект Drink
            System.out.println(index++ + ") " + drink.name() + " - " + drink.type() +
                    "\n   " + drink.volume() + " л - " + drink.price() + " рублей");
        }
        System.out.println("Баланс: " + VendingMachine.getBalance() + " рублей");
    }
}
