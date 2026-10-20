package translator;

import java.util.Map;
import java.util.Scanner;

/** Консольний інтерфейс: словник наповнюється з клавіатури, вводиться фраза й показується переклад. */
public class TranslatorConsole {

    private final Scanner in;
    private final Translator translator = Translator.withDefaultWords();

    public TranslatorConsole(Scanner in) {
        this.in = in;
    }

    public void run() {
        System.out.println("Початковий словник містить слів: " + translator.size());
        while (true) {
            System.out.println("\n=== Перекладач з англійської ===");
            System.out.println("1. Перекласти фразу");
            System.out.println("2. Додати пару слів у словник");
            System.out.println("3. Показати словник");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            if (!in.hasNextLine()) {
                return;
            }
            switch (in.nextLine().trim()) {
                case "1" -> translatePhrase();
                case "2" -> addPair();
                case "3" -> showDictionary();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Невідомий пункт меню.");
            }
        }
    }

    private void translatePhrase() {
        System.out.print("Введіть фразу англійською: ");
        String phrase = in.nextLine();
        try {
            System.out.println("Переклад: " + translator.translate(phrase));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void addPair() {
        System.out.print("Англійське слово: ");
        String english = in.nextLine();
        System.out.print("Переклад українською: ");
        String ukrainian = in.nextLine();
        try {
            translator.addPair(english, ukrainian);
            System.out.println("Додано. Слів у словнику: " + translator.size());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void showDictionary() {
        Map<String, String> entries = translator.entries();
        System.out.println("Словник (" + entries.size() + "):");
        entries.forEach((english, ukrainian) -> System.out.println("  " + english + " = " + ukrainian));
    }
}
