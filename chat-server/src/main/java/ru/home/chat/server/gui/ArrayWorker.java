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
        if (input.size() == 0 || input.size()%2 > 0) {
            return false;
        }
        int sum = 0;
        for (Integer i: input) {
            if (i == 1) {
                sum += 1;
                continue;
            } else if (i == 4) {
                sum -= 1;
                continue;
            }
            return false;
        }
        return sum == 0;
    }
}
