package org.shopnow.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void log(String msgType, Object msg) {
        String timestamp = timestampFormat.format(new Date());
        String logMessage = "[" + timestamp + "] ::: [" + msgType + "] ::: " + msg;
        System.out.println(logMessage);
    }

    public static void Log(Object msg) {
        log("INFO", msg);
    }

    public static void Error(Object msg) {
        log("ERROR", msg);
    }

    public static void Trace(Object msg) {
        log("TRACE", msg);
    }

    public static void Except(Exception e) {
        log("EXCEPTION", e);
    }

    public static void Heading(String title) {
        String hashLine = "#".repeat(15);
        log("HEAD", String.format("%s %s %s", hashLine, title, hashLine));
    }
}
