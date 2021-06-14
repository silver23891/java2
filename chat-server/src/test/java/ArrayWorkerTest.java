import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.home.chat.server.gui.ArrayWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ArrayWorkerTest {
    ArrayWorker arrayWorker;
    @BeforeEach
    void init() {
        arrayWorker = new ArrayWorker();
    }

    @Test
    @DisplayName("Тест getArrayTail при передаче пустого массива и массива без 4")
    void getArrayTailEmptyTest() {
        Assertions.assertThrows(RuntimeException.class, (()->arrayWorker.getArrayTail(new ArrayList<>())));
        Assertions.assertThrows(RuntimeException.class, (()->arrayWorker.getArrayTail(List.of(1, 2, 3))));
    }

    @ParameterizedTest
    @MethodSource("getArrayTailTestData")
    @DisplayName("Тест getArrayTail при передаче непустых массивов")
    void getArrayTailTest(List<Integer> input, List<Integer> output) {
        Assertions.assertEquals(output, arrayWorker.getArrayTail(input));
    }

    static Stream<Arguments> getArrayTailTestData() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.arguments(List.of(1, 2, 3, 4), new ArrayList<Integer>()));
        arguments.add(Arguments.arguments(List.of(4, 3, 2, 1), List.of(3, 2, 1)));
        arguments.add(Arguments.arguments(List.of(1, 2, 4, 4, 2, 3, 4, 1, 7), List.of(1, 7)));
        return arguments.stream();
    }

    @Test
    @DisplayName("Тест arrayCorrect при передачи пустых и непустых массивов")
    void arrayCorrectTest() {
        Assertions.assertFalse(arrayWorker.arrayCorrect(new ArrayList<Integer>()));
        Assertions.assertFalse(arrayWorker.arrayCorrect(List.of(2, 3)));
        Assertions.assertTrue(arrayWorker.arrayCorrect(List.of(1, 2, 3)));
        Assertions.assertTrue(arrayWorker.arrayCorrect(List.of(2, 3, 4)));
        Assertions.assertTrue(arrayWorker.arrayCorrect(List.of(1, 2, 3, 4)));
    }
}
