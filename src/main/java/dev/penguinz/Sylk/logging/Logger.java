package dev.penguinz.Sylk.logging;

import dev.penguinz.Sylk.Application;

public class Logger {

    private LogLevel currentLogLevel;

    public Logger(LogLevel currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

    public void logLoad(String message) {
        log(LogLevel.LOAD, message);
    }

    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    public void logWarning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }


    public void log(LogLevel logLevel, String message) {
        if(logLevel.getLevel() > this.currentLogLevel.getLevel())
            return;
        System.out.println(logLevel.getPrefix()+" "+message+ANSIColor.ANSI_RESET);
    }

    public void setCurrentLogLevel(LogLevel currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

    public static Logger getLogger() {
        return Application.getInstance().getLogger();
    }

}
