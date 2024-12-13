package Service.Implementations;

import Output.Menu;
import Domain.Drink;
import Service.Helpers.DrinkStore;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VendingMachine implements Service.Interfaces.VendingMachine {

    private static BigDecimal balance = BigDecimal.ZERO;

    //region get set
    // Текущий баланс.
    public static BigDecimal getBalance() {
        return balance;
    }

    // Устанавливает значение баланса.
    private static void setBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Баланс не может быть отрицательным.");
        }
        balance = amount;
    }
    //endregion

    //region startInteraction
    /**
     * Взаимодействие с пользователем.
     *
     * Отображение меню напитков
     * Взаимодействие продолжается до тех пор, пока пользователь не выберет
     * опцию выхода.
     *
     * Алгоритм работы метода:
     * 1. Отображается меню напитков.
     * 2. Запрашивается выбор пользователя:
     *    - 1: Внести деньги
     *    - 2: Выбрать напиток
     *    - 0: Выйти из программы
     * 3. В зависимости от выбора пользователя выполняются соответствующие действия:
     *    - Если выбран 1, запрашивается сумма для внесения и вызывается метод
     *      добавления денег.
     *    - Если выбран 2, запрашивается номер напитка и вызывается метод
     *      выдачи напитка.
     *    - Если выбран 0, программа завершает работу.
     *    - В случае некорректного выбора выводится сообщение об ошибке.
     *
     */
    @Override
    public void startInteraction(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню напитков:");
            Menu.display();

            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addMoneyPrompt(scanner);
                    break;
                case 2:
                    selectDrinkPrompt(scanner);
                    break;
                case 0:
                    System.out.println("Выход из программы. Спасибо!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }
    //endregion

    // region userInteraction
    /**
     * Выводит инструкции.
     *
     * Доступные варианты выбора:
     * 1 - Внести деньги
     * 2 - Выбрать напиток
     * 0 - Выйти из программы
     *
     * @param scanner Объект Scanner для считывания ввода пользователя.
     * @return Целочисленный выбор пользователя (1, 2 или 0).
     */
    private int getUserChoice(Scanner scanner) {
        System.out.print("Введите 1, чтобы внести деньги, 2, чтобы выбрать напиток, 0, чтобы выйти: ");
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
                scanner.next(); // очистка некорректного ввода
            }
        }
    }

    /**
     * Запрос у пользователя внесения средств.
     *
     * @param scanner Объект Scanner считывание ввода пользователя.
     */
    private void addMoneyPrompt(Scanner scanner) {
        System.out.print("Введите сумму для внесения: ");
        BigDecimal amount = scanner.nextBigDecimal();
        addMoney(amount);
    }

    /**
     * Запрашивает у пользователя номер напитка.
     *
     * @param scanner Объект Scanner для считывания ввода пользователя.
     */
    private void selectDrinkPrompt(Scanner scanner) {
        System.out.print("Введите номер напитка: ");
        int drinkNumber = scanner.nextInt();
        dispenseDrink(drinkNumber);
    }
    //endregion

    // region addMoney
    /**
     * Пополнение баланса пользователя.
     *
     * Проверяет, является ли указанная сумма положительной и обновляет баланс.
     *
     * @param amount сумма, которая будет добавлена к балансу
     * @throws NullPointerException указанная сумма равна нулю
     * @throws ArithmeticException возникание арифметической ошибки
     */
    private void addMoney(BigDecimal amount) {
        try {

            if (amount == null)
                throw new NullPointerException("Сумма не может быть null.");

            // Проверка на положительное значение
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Сумма должна быть положительной.");
                return;
            }

            // Обновляем баланс
            balance = balance.add(amount);
            System.out.println("Вы внесли " + amount + " рублей. Текущий баланс: " + balance + " рублей.");

        } catch (NullPointerException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Ошибка при выполнении арифметической операции: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace(); // Выводим стек вызовов для отладки
        }
    }
    //endregion

    // region dispenseDrink
    /**
     * Подает напиток в соответствии с указанным количеством напитков.
     *
     * Проверка на существования указанного напитока, подтверждает, что у пользователя
     * достаточный баланс, и вычитает стоимость напитка из баланса пользователя.
     *
     * @param drinkNumber номер напитка
     * @throws NullPointerException объект неинициализирован
     * @throws IllegalArgumentException неверный аргумент
     */
    private void dispenseDrink(int drinkNumber) {
        try {

            Drink drink = DrinkStore.getDrinks().get(drinkNumber);

            // Проверка наличия напитка
            if (drink == null) {
                System.out.println("Выбранный номер напитка не существует.");
                return;
            }

            // Проверка баланса
            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("\n Вы не внесли деньги. Пожалуйста, внесите деньги перед выбором напитка.");
                return;
            }

            // Проверяем, достаточно ли средств
            if (balance.compareTo(drink.price()) < 0) {
                System.out.println("\n Недостаточно средств для покупки " + drink.name() + ". Необходимо " + drink.price() + " рублей.");
                return;
            }

            // Вычитаем цену напитка из баланса и выводим сообщение
            balance = balance.subtract(drink.price());
            System.out.println("\n Вы получили " + drink.name() + ". Остаток на счете: " + balance + " рублей.");

        } catch (NullPointerException e) {
            System.out.println("Ошибка: объект не инициализирован. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: неверный аргумент. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace(); // Выводим стек вызовов для отладки
        }
    }
    //endregion
}
