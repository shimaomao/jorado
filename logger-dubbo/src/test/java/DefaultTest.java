import com.jorado.logger.EventClient;
import org.junit.Test;

public class DefaultTest {

    @Test
    public void test() throws Exception {

        doLog();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> doLog());
            thread.start();
        }
        Thread.sleep(1000);
    }

    @Test
    public void test1() throws Exception {
        EventClient.getDefault().asyncSubmitLog("test");
        Thread.sleep(10000);
    }

    public void doLog() {
        for (int i = 0; i < 5; i++) {
            EventClient.getDefault().asyncSubmitLog("test1");
        }
    }
}
