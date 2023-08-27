package org.shopnow.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String WHITE = "\u001B[37m";
    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void log(String Color, String msgType, String msg) {
        String timestamp = timestampFormat.format(new Date());
        String logMessage = "[" + timestamp + "] ::: [" + msgType + "] ::: " + msg + RESET;
        System.out.println(logMessage);
    }

    public static void Log(Object msg) {
        log(GREEN,"INFO", msg.toString());
    }

    public static void Log(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log(GREEN,"INFO", formattedMsg);
    }

    public static void Error(Object msg) {
        log(RED,"ERROR", msg.toString());
    }

    public static void Error(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log(RED,"ERROR", formattedMsg);
    }

    public static void Trace(Object msg) {
        log(WHITE,"TRACE", msg.toString());
    }

    public static void Trace(String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log(WHITE,"TRACE", formattedMsg);
    }

    public static void Except(Exception e) {
        log(YELLOW,"EXCEPTION", e.toString());
    }

    public static void Except(Exception e, String format, Object... args) {
        String formattedMsg = String.format(format, args);
        log(YELLOW,"EXCEPTION", e.toString() + " - " + formattedMsg);
    }

    public static void Heading(String title) {
        String hashLine = "#".repeat(15);
        log(WHITE, "HEAD", String.format("%s %s %s", hashLine, title, hashLine));
    }
}
