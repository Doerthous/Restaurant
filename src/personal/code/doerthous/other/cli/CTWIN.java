package personal.code.doerthous.other.cli;

import java.io.IOException;

public class CTWIN { // command test for window os
    public static void shutdown(int time) throws IOException {
        if(time >= 0) {
            Runtime.getRuntime().exec("shutdown /s /t " + time);
        } else {
            Runtime.getRuntime().exec("shutdown /a");
        }
    }
}
