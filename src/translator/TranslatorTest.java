package translator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class TranslatorTest {

    private Translator translator;

    @Before
    public void setUp() {
        translator = Translator.withDefaultWords();
    }

    @Test
    public void translatesWordsAndDropsPunctuation() {
        assertEquals("Привіт світ", translator.translate("Hello, world!"));
    }

    @Test
    public void skipsArticles() {
        assertEquals("Кіт п'є молоко", translator.translate("The cat drinks milk"));
    }

    @Test
    public void unknownWordStaysInBrackets() {
        assertEquals("Я люблю [physics]", translator.translate("I love physics"));
    }

    @Test
    public void lookupIsCaseInsensitive() {
        assertEquals(translator.translate("hello"), translator.translate("HELLO"));
    }

    @Test
    public void addPairAddsNewWord() {
        int before = translator.size();
        translator.addPair("Tree", "дерево");
        assertEquals(before + 1, translator.size());
        assertEquals("Дерево", translator.translate("tree"));
    }

    @Test
    public void addPairOverwritesExistingWord() {
        int before = translator.size();
        translator.addPair("cat", "котик");
        assertEquals(before, translator.size());
        assertEquals("Котик", translator.translate("cat"));
    }

    @Test
    public void addPairRejectsEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> translator.addPair("", "щось"));
        assertThrows(IllegalArgumentException.class, () -> translator.addPair("word", "  "));
        assertThrows(IllegalArgumentException.class, () -> translator.addPair(null, "щось"));
    }

    @Test
    public void emptyPhraseIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> translator.translate("   "));
        assertThrows(IllegalArgumentException.class, () -> translator.translate(null));
    }
}
