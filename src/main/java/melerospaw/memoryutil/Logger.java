package melerospaw.memoryutil;

import android.util.Log;

/**
 * Created by Juan José Melero on 22/08/2016.
 */
class Logger {

    public static boolean loggingEnabled = true;

    static void setLoggingEnabled(boolean enabled){
        loggingEnabled = enabled;
    }

    static boolean isLoggingEnabled(){
        return loggingEnabled;
    }

    static void log(String tag, String message, boolean isSuccess) {
        if (isLoggingEnabled()) {
            if (isSuccess) {
                Log.i(tag, message);
            } else {
                Log.e(tag, message);
            }
        }
    }
}
