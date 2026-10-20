package translator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Перекладач з англійської мови на українську.
 *
 * Словник зберігається у колекції HashMap<String, String>:
 * ключ це англійське слово, значення це його український переклад.
 */
public class Translator {

    private final Map<String, String> dictionary = new HashMap<>();

    /** Додає пару "англійське слово, український переклад". Те саме слово можна перезаписати. */
    public void addPair(String english, String ukrainian) {
        if (english == null || english.isBlank() || ukrainian == null || ukrainian.isBlank()) {
            throw new IllegalArgumentException("Слово й переклад не можуть бути порожніми");
        }
        dictionary.put(english.trim().toLowerCase(), ukrainian.trim());
    }

    /**
     * Перекладає фразу слово за словом. Слово, якого немає у словнику, лишається
     * в оригіналі у квадратних дужках. Артиклі a, an, the пропускаються:
     * в українській мові їх немає.
     */
    public String translate(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            throw new IllegalArgumentException("Фраза порожня");
        }
        StringBuilder result = new StringBuilder();
        for (String token : phrase.trim().split("\\s+")) {
            // прибираємо розділові знаки навколо слова: "Hello," -> "hello"
            String word = token.replaceAll("[^\\p{L}']", "").toLowerCase();
            if (word.isEmpty() || word.equals("a") || word.equals("an") || word.equals("the")) {
                continue;
            }
            String translation = dictionary.get(word);
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(translation != null ? translation : "[" + word + "]");
        }
        if (result.length() == 0) {
            return "";
        }
        // перша літера результату велика, як на початку речення
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    public int size() {
        return dictionary.size();
    }

    /** Копія словника, відсортована за англійським словом (для виводу на екран). */
    public Map<String, String> entries() {
        return new TreeMap<>(dictionary);
    }

    /** Перекладач з початковим словником. */
    public static Translator withDefaultWords() {
        Translator translator = new Translator();
        String[][] pairs = {
                {"hello", "привіт"}, {"world", "світ"}, {"good", "добрий"}, {"morning", "ранок"},
                {"i", "я"}, {"you", "ти"}, {"we", "ми"}, {"love", "люблю"}, {"read", "читаю"},
                {"write", "пишу"}, {"cat", "кіт"}, {"dog", "пес"}, {"drinks", "п'є"}, {"milk", "молоко"},
                {"book", "книга"}, {"house", "дім"}, {"water", "вода"}, {"friend", "друг"},
                {"code", "код"}, {"programming", "програмування"}, {"computer", "комп'ютер"},
                {"and", "і"}, {"today", "сьогодні"}, {"big", "великий"}, {"new", "новий"},
        };
        for (String[] pair : pairs) {
            translator.addPair(pair[0], pair[1]);
        }
        return translator;
    }
}
