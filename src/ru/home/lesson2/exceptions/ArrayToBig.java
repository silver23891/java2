package ru.home.lesson2.exceptions;

public class ArrayToBig extends Exception{
    @Override
    public String toString() {
        return "Array must be 4x4";
    }
}