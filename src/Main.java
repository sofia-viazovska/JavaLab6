import translator.TranslatorConsole;

import java.util.Scanner;

/** Лабораторна 6 (колекції), Simple Task: консольний перекладач з англійської на українську. */
public class Main {

    public static void main(String[] args) {
        new TranslatorConsole(new Scanner(System.in)).run();
    }
}
