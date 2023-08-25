package org.shopnow.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void log(String msgType, String msg) {
        String timestamp = timestampFormat.format(new Date());
        String logMessage = "[" + timestamp + "] ::: [" + msgType + "] ::: " + msg;
        System.out.println(logMessage);
    }

    public static void Log(Object msg) {
        log("INFO", msg.toString());
    }

    public static void Log(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log("INFO", formattedMsg);
    }

    public static void Error(Object msg) {
        log("ERROR", msg.toString());
    }

    public static void Error(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log("ERROR", formattedMsg);
    }

    public static void Trace(Object msg) {
        log("TRACE", msg.toString());
    }

    public static void Trace(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log("TRACE", formattedMsg);
    }

    public static void Except(Exception e) {
        log("EXCEPTION", e.toString());
    }

    public static void Except(Exception e, String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log("EXCEPTION", e.toString() + " - " + formattedMsg);
    }

    public static void Heading(String title) {
        String hashLine = "#".repeat(15);
        log("HEAD", String.format("%s %s %s", hashLine, title, hashLine));
    }
}
