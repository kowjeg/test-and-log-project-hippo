import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {


@Test
@Disabled
@Timeout(value = 22, unit = TimeUnit.SECONDS)
void failsIfExecutionTimeExceeds22Seconds() throws Exception {

    String args[] = {};
    Main.main(args);

}


}