import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class HorseTest {


    Horse horse;
    Horse horse2;

    @BeforeEach
    void initialize() {
        horse = new Horse("Test", 5, 3);
        horse2 = new Horse("Test", 5);
    }

    @Test
    public void constFirstArgNotNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 3, 4);
        });
    }


    @Test
    public void constSecondArgNotNullText() {

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 3, 4);
        });

        assertEquals("Name cannot be null.", e.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n", "\t", "   "})
    public void constSecondArgNotNullText(String text) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(text, 3, 4);
        });

    }

    @Test
    public void constNameNotBlank() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(" ", 3, 4);
        });
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void negativeSecondArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Speedy", -1, 4);
        });
    }

    @Test
    public void negativeSecondArgExceptionText() {
        assertEquals("Speed cannot be negative.",
                assertThrows(IllegalArgumentException.class, () -> {
                    new Horse("Speedy", -1, 4);
                }).getMessage());
    }

    @Test
    public void negativeThirdArgException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Speedy", 1, -3);
        });
    }

    @Test
    public void negativeThirdArgExceptionText() {
        assertEquals("Distance cannot be negative.",
                assertThrows(IllegalArgumentException.class, () -> {
                    new Horse("Speedy", 1, -3);
                }).getMessage());
    }


    @Test
    public void getNameTest() {
        String name = horse.getName();
        assertEquals("Test", name);

    }

    @Test
    public void getSpeedTest() {
        double speed = horse.getSpeed();
        assertEquals(5, speed, 0);
    }

    @Test
    public void getDistanceTest() {
        double distance = horse.getDistance();
        assertEquals(3, distance, 0);
    }

    @Test
    public void getDistance2Null() {
        double distance = horse2.getDistance();
        assertEquals(0, distance, 0);
    }


    @Test
    public void move() {
        try (MockedStatic<Horse> ms = Mockito.mockStatic(Horse.class)) {
            ms.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.3);
            horse.move();
            ms.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    static Stream<Arguments> valuesSpeedDistanceList() {
        return Stream.of(
                Arguments.of(10.0, 5.0),
                Arguments.of(15.0, 10.0),
                Arguments.of(20.0, 15.0)
        );
    }

    @ParameterizedTest
    @MethodSource("valuesSpeedDistanceList")
    public void moveRightDistance(double distance, double speed) {
        try (MockedStatic<Horse> ms = Mockito.mockStatic(Horse.class)) {
            ms.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.2);
            Horse horse1 = new Horse("Test", speed, distance);

            double expectDistance =  horse1.getDistance() + horse1.getSpeed() * 0.2;

            horse1.move();
            assertEquals(expectDistance, horse1.getDistance());

        }
    }
}
