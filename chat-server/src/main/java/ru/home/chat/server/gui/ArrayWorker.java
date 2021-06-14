package ru.home.chat.server.gui;

import java.util.List;

public class ArrayWorker {
    public List<Integer> getArrayTail(List<Integer> input) {
        if (!input.contains(4)) {
            throw new RuntimeException("There is no 4 digital in array");
        }
        return input.subList(input.lastIndexOf(4)+1, input.size());
    }

    public boolean arrayCorrect(List<Integer> input) {
        return (input.contains(1) || input.contains(4));
    }
}
