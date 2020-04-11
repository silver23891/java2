package ru.home.lesson3;

import ru.home.lesson3.phonebook.Person;
import ru.home.lesson3.phonebook.PhoneBook;

import java.util.*;

public class Main {
    public static final String INIT_TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod \n" +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, \n" +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo \n" +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse \n" +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non \n" +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    public static void main(String[] args) {
        String[] text = convertTextToArray(INIT_TEXT, true);
        printUniqueWords(text);
        printMap(getWordsCount(text));
        System.out.printf("");
        Person[] persons = {
                new Person("Ivanov", "111", "ivanov@mail.ru"),
                new Person("Petrov", "222", "petrov@mail.ru"),
                new Person("ivanov", "333", "ivanov2@mail.ru"),
                new Person("petrov", "444", "petrov2@mail.ru"),
                new Person("Kuznetsov", "555", "kuznetsov@mail.ru"),
                new Person("vasiliev", "  ", "")
        };
        PhoneBook book = new PhoneBook(persons);
        System.out.println(book);
        System.out.println(book.getBySurname("ivanov", book.PHONE_KEY));
        System.out.println(book.getBySurname("ivanov", book.EMAIL_KEY));
    }

    /**
     * Получить массив строк из строки
     * @param text       Исходная строка
     * @param ignoreCase Признак игнорирования регистра слов
     * @return массив строк
     */
    public static String[] convertTextToArray(String text, boolean ignoreCase) {
        if (!ignoreCase) {
            return removePunctuation(text.toLowerCase()).split(" ");
        }
        return removePunctuation(text).split(" ");
    }

    /**
     * Удалить знаки препинания из строки
     * @param text Исходная строка
     * @return Строка без знаков препинания
     */
    public static String removePunctuation(String text) {
        return text.replaceAll("\\.|,|\\!|\\?|\n", "");
    }

    /**
     * Вывести в консоль уникальные слова из массива строк
     * @param text массив строк
     */
    public static void printUniqueWords(String[] text) {
        HashSet<String> set = new HashSet<>(Arrays.asList(text));
        System.out.println(set);
    }

    /**
     * Получить коллекцию Слово - Кол-во вхождений слова в массив
     * @param text массив строк
     * @return коллекция
     */
    public static HashMap<String, Integer> getWordsCount(String[] text) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < text.length; i++) {
            if (hashMap.containsKey(text[i])) {
                hashMap.put(text[i], hashMap.get(text[i])+1);
            } else {
                hashMap.put(text[i], 1);
            }
        }
        return hashMap;
    }

    /**
     * Вывести в консоль элементы коллекции
     * @param map коллекция
     */
    public static void printMap(HashMap<String, Integer> map) {
        Set<HashMap.Entry<String, Integer>> set = map.entrySet();
        for (HashMap.Entry<String, Integer> entry : set) {
            System.out.printf("%s: %d\n", entry.getKey(), entry.getValue());
        }
    }
}