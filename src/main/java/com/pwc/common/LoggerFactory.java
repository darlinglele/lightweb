package com.pwc.common;

import java.util.logging.Logger;

public class LoggerFactory {

    public static Logger getLogger(Class aClass) {
        return Logger.getLogger(aClass.toString());
    }
}
