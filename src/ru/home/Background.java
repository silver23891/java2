package ru.home;

import java.awt.*;

/**
 * Класс для формирования цвета заднего фона
 * Предполагалось сделать нечто похожее на смену дня и ночи
 */

public class Background {
    final int CYCLE_DURATION = 10;
    final int[] CYCLE_COLORS = {
            13859840,
            16775680,
            16740864,
            13840384,
            2039733
    };

    int getSeconds(long time) {
        int seconds = (int) (time/Math.pow(10, 9));
        int cycleCheck = CYCLE_DURATION * CYCLE_COLORS.length;
        if (seconds >= cycleCheck) {
            int multiple = seconds / cycleCheck;
            seconds -= multiple*cycleCheck;
        }
        return seconds;
    }

    int getCycleFromSeconds(int seconds) {
        return seconds/CYCLE_DURATION;
    }

    Color getBackgroundColor(long time) {
        return new Color(CYCLE_COLORS[getCycleFromSeconds(getSeconds(time))]);
    }
}