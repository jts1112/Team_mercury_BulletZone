package edu.unh.cs.cs619.bulletzone.util;

import org.slf4j.Logger;

public class LogUtil {
    public static void log(Logger logger, boolean useInfo, String message, Object... args) {
        if (useInfo) {
            logger.info(message, args);
        } else {
            logger.debug(message, args);
        }
    }
}
