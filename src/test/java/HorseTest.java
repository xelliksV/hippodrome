import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class HorseTest {
    @Test
     void nullTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse(null, 0);
            }
        });
    }
    @Test
    void messageNullTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        try {
            Horse h = new Horse(null, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Name cannot be null.", outputStream.toString().trim());
    }
    @ParameterizedTest
    @CsvSource(
            {",   ,\t"}
    )
    void invalidNameTest(String empty, String space, String special) {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse(empty, 0);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse(space, 0);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse(special, 0);
            }
        });
    }
    @ParameterizedTest
    @CsvSource(
            {",   ,\t\\"}
    )
    void messageInvalidTest(String empty, String space, String special) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);
        try {
            Horse h = new Horse(empty, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Name cannot be blank.", outputStream.toString().trim());
        outputStream.reset();
        try {
            Horse h = new Horse(space, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Name cannot be blank.", outputStream.toString().trim());
        outputStream.reset();
        try {
            Horse h = new Horse(special, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Name cannot be blank.", outputStream.toString().trim());
    }
    @Test
    void negativeSpeedTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse("name", -1);
            }
        });
    }
    @Test
    void negativeSpeedMessageTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        try {
            Horse h = new Horse("n", -1);
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Speed cannot be negative.", outputStream.toString().trim());
    }
    @Test
    void negativeDistanceTest() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Horse("n", 0, -1);
            }
        });
    }
    @Test
    void negativeDistanceMessageTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        System.setOut(stream);

        try {
            Horse h = new Horse("n", 0, -1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Distance cannot be negative.", out.toString().trim());
    }
    @Test
    void nameTest() {
        Horse h = new Horse("name", 0);
        assertEquals("name", h.getName());
    }
    @Test
    void speedTest() {
        Horse h = new Horse("n", 0);
        assertEquals(0, h.getSpeed());
    }
    @Test
    void distanceTest() {
        Horse h = new Horse("n", 0, 10);
        assertEquals(10, h.getDistance());
    }
    @Test
    void noDistanceTest() {
        Horse h = new Horse("n", 0);
        assertEquals(0, h.getDistance());
    }
    @Test
    void moveTest() {
        Horse h = new Horse("n", 1, 1);
        try(MockedStatic<Horse> hs = Mockito.mockStatic(Horse.class)) {
            h.move();
            hs.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @CsvSource(
            {"1.0, 2.0, 3.0"}
    )
    void paramMoveTest(double a, double b, double c) {
        try (MockedStatic<Horse> hm = Mockito.mockStatic(Horse.class)) {
            hm.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(a);
            Horse h = new Horse("n", 1, 1);
            double result = h.getDistance() + (h.getSpeed() * a);
            h.move();
            assertEquals(result, h.getDistance());
        }
        try (MockedStatic<Horse> hm = Mockito.mockStatic(Horse.class)) {
            hm.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(b);
            Horse h = new Horse("n", 1, 1);
            double result = h.getDistance() + (h.getSpeed() * b);
            h.move();
            assertEquals(result, h.getDistance());
        }
        try (MockedStatic<Horse> hm = Mockito.mockStatic(Horse.class)) {
            hm.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(c);
            Horse h = new Horse("n", 1, 1);
            double result = h.getDistance() + (h.getSpeed() * c);
            h.move();
            assertEquals(result, h.getDistance());
        }
    }
}
