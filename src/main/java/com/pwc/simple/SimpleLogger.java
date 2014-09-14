package com.pwc.simple;
import java.util.logging.Logger;

public class SimpleLogger {
    public static Logger getLogger() {
        Logger logger = Logger.getLogger("StaticResourceHttpHandler");
//        try {
//            logger.addHandler(new FileHandler("ss"));
//        } catch (IOException e) {
//
//        }
        return logger;
    }
}
