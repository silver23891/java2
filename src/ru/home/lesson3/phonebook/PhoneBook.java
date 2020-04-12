package ru.home.lesson3.phonebook;

import java.util.*;

public class PhoneBook {
    public final String PHONE_KEY = "phone";
    public final String EMAIL_KEY = "email";
    private HashMap<String, HashSet<Person>> book = new HashMap<>();

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
            book.put(person.getSurname(), new HashSet<>(Arrays.asList(person)));
        } else {
            book.get(person.getSurname()).add(person);
        }
    }

    /**
     * Найти телефон по фамилии
     * @param surname Фамилия для поиска
     * @return Список значений
     */
    public ArrayList<String> getPhoneBySurname(String surname) {
        return getBySurname(surname, PHONE_KEY);
    }

    /**
     * Найти email по фамилии
     * @param surname Фамилия для поиска
     * @return Список значений
     */
    public ArrayList<String> getEmailBySurname(String surname) {
        return getBySurname(surname, EMAIL_KEY);
    }

    /**
     * Найти запись по фамилии
     * @param surname Фамилия для поиска
     * @param column  Требуемое поле
     * @return Список значений
     */
    private ArrayList<String> getBySurname(String surname, String column) {
        surname = surname.toUpperCase();
        if (!book.containsKey(surname) || (!column.equals(PHONE_KEY) && !column.equals(EMAIL_KEY))) {
            return new ArrayList<>();
        }
        ArrayList<String> out = new ArrayList<>();
        for (Person p : book.get(surname)) {
            if (column.equals(PHONE_KEY)) {
                out.add(p.getPhone());
            } else {
                out.add(p.getEmail());
            }
        }
        return out;
    }
}
