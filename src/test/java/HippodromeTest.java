import org.apache.logging.log4j.util.PropertySource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {


    Hippodrome hippodrome;

    @Mock
    List<Horse> horses;

    @Test
    public void testConstructorArgNotNull() {

        assertThrows(IllegalArgumentException.class, () -> { new Hippodrome(null);});

    }
    @Test
    public void testConstructorArgNotNullRightException() {

        Exception e = assertThrows(IllegalArgumentException.class, () -> { new Hippodrome(null);});

        assertEquals("Horses cannot be null.", e.getMessage());

    }

    @Test
    public void testConstructorEmptyHorseList() {
        Mockito.when(horses.isEmpty()).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> { new Hippodrome(horses);});
    }

    @Test
    public void testConstructorEmptyHorseListRightException() {
        Mockito.when(horses.isEmpty()).thenReturn(true);
        Exception e = assertThrows(IllegalArgumentException.class, () -> { new Hippodrome(horses);});
        assertEquals("Horses cannot be empty.", e.getMessage());
    }


    @Test
    public void testGetHorses() {

        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("" + i,i*2,i*3));
        }


        assertEquals(horses, new Hippodrome(horses).getHorses());

    }

    @Test
    public void moveAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        for (Horse horse : horses) {
            horse.move();
        }


        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }

    }



    @Test
    public void testGetWinner() {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("test1",1,3));
        horses.add(new Horse("test2",1,4));
        horses.add(new Horse("test3",1,5));
        Horse bestHorse = horses.stream().max(Comparator.comparingDouble(Horse::getDistance))
                .orElse(null);

        assertEquals(bestHorse, new Hippodrome(horses).getWinner());

    }

}
