import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class HippodromeTest {
    @Test
    void nullTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Hippodrome(null);
            }
        });
    }
    @Test
    void nullMessageTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        System.setOut(stream);

        try {
            Hippodrome h = new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Horses cannot be null.", out.toString().trim());
    }
    @Test
    void emptyTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Hippodrome(new ArrayList<Horse>());
            }
        });
    }
    @Test
    void emptyMessageTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        System.setOut(stream);

        try {
            Hippodrome h = new Hippodrome(new ArrayList<Horse>());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Horses cannot be empty.", out.toString().trim());
    }
    @Test
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("h"+i, Horse.getRandomDouble(0, 1.0)));
        }
        Hippodrome h = new Hippodrome(horses);
        assertIterableEquals(new Iterable<Horse>() {
            @Override
            public Iterator<Horse> iterator() {
                return horses.iterator();
            }
        }, new Iterable<Horse>() {
            @Override
            public Iterator<Horse> iterator() {
                return h.getHorses().iterator();
            }
        });
    }
    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome h = new Hippodrome(horses);
        h.move();
        for (Horse horse : h.getHorses()) {
            Mockito.verify(horse).move();
        }
    }
    @Test
    void winnerTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("h"+i, 0, i));
        }
        Hippodrome h = new Hippodrome(horses);
        assertEquals(h.getWinner(), horses.get(29));
    }
}
