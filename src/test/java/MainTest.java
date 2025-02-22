import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    @Test
    @Timeout(22L)
    @Disabled
    void mainTest() throws Exception {
        Main.main(new String[10]);
    }
}
