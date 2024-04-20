package edu.unh.cs.cs619.bulletzone.util;

import org.slf4j.Logger;


public class LogUtil {
    /**
     * Log a message either as info or debug. Helps set many log messages to debug or info at a time
     * @param logger Logger to use.
     * @param useInfo If true, use info, else use debug.
     *                Using debug means the message will get lost in a sea of other debug messages
     *                Using info means the message will always print, even when the logger is set
     *                to low verbosity, so it will actually be seen.
     * @param message Message to log
     * @param args Arguments to the message
     */
    public static void log(Logger logger, boolean useInfo, String message, Object... args) {
        if (useInfo) {
            logger.info(message, args);
        } else {
            logger.debug(message, args);
        }
    }
}
