package ru.home.lesson3.phonebook;

import java.util.*;

public class PhoneBook {
    public final String PHONE_KEY = "phone";
    public final String EMAIL_KEY = "email";
    private HashMap<String, HashMap<String, HashSet<String>>> book = new HashMap<>();

    public PhoneBook() {}

    public PhoneBook(Person person) {
        addPerson(person);
    }

    public PhoneBook(Person[] person) {
        for (int i = 0; i < person.length; i++) {
            addPerson(person[i]);
        }
    }

    /**
     * Добавить запись в книгу
     * @param person
     */
    public void addPerson(Person person) {
        if (person.getSurname().trim().isEmpty()) {
            return;
        }
        if (!book.containsKey(person.getSurname())) {
            book.put(person.getSurname(), new HashMap<>());
        }
        addValue(person.getSurname(), PHONE_KEY, person.getPhone());
        addValue(person.getSurname(), EMAIL_KEY, person.getEmail());
    }

    /**
     * Добавить значение телефона или email для определенной фамилии
     * @param bookKey Фамилия
     * @param key     Тип значения (телефон или email)
     * @param value   Значение
     */
    private void addValue(String bookKey, String key, String value) {
        if (value.trim().isEmpty()) {
            return;
        }
        if (book.get(bookKey).containsKey(key)) {
            book.get(bookKey).get(key).add(value);
        } else {
            book.get(bookKey).put(key, new HashSet<>(Arrays.asList(value)));
        }
    }

    /**
     * Найти запись по фамилии
     * @param surname Фамилия для поиска
     * @param column  Значение, которое необходимо вернуть
     * @return Список значений
     */
    public ArrayList<String> getBySurname(String surname, String column) {
        surname = surname.toUpperCase();
        if (!book.containsKey(surname) || !book.get(surname).containsKey(column)) {
            return new ArrayList<>();
        }
        return new ArrayList<>((book.get(surname).get(column)));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (HashMap.Entry<String, HashMap<String, HashSet<String>>> e : book.entrySet()) {
            out.append(String.format("Фамилия: %s\n", e.getKey()));
            if (e.getValue().containsKey(PHONE_KEY)) {
                out.append(String.format("Телефон: %s\n", e.getValue().get(PHONE_KEY).toString()));
            } else {
                out.append("Телефон не указан\n");
            }
            if (e.getValue().containsKey(EMAIL_KEY)) {
                out.append(String.format("Email: %s\n", e.getValue().get(EMAIL_KEY).toString()));
            } else {
                out.append("Email не указан\n");
            }
            out.append("-----------------\n");
        }
        return out.toString();
    }
}
