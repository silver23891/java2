package ru.home.lesson2.exceptions;

public class StringNotInt extends Exception {
    @Override
    public String toString() {
        return "String must contain int";
    }
}