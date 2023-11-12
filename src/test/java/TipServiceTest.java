import lombok.extern.java.Log;

import org.ot5usk.Tester;

@Log
public class TipServiceTest {

    public void log(Tester helper, String requirement) {
        AssertionError assertionError = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            helper.test(requirement);
        } catch (AssertionError aErr) {
            assertionError = aErr;
        } finally {
            if (assertionError == null) {
                log.info("[TEST WAS SUCCESSFUL]: " + "\"" + requirement + "\" : " + stackTraceElements[2] + "\n");
            } else {
                log.warning("[TEST WAS FAILED]: " + "\"" + requirement + "\" : " + stackTraceElements[2] + "\n");
                throw assertionError;
            }
        }
    }
}