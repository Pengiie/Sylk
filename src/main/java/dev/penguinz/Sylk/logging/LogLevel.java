package dev.penguinz.Sylk.logging;

/**
 * Determines the severity of the log message, the lower the number the more severe.
 */
public enum LogLevel {

    ERROR(1, ANSIColor.ANSI_RED+"[ERROR]"),
    WARNING(2, ANSIColor.ANSI_YELLOW+"[WARNING]"),
    INFO(3, ANSIColor.ANSI_WHITE+"[INFO]"),
    LOAD(4, ANSIColor.ANSI_WHITE+"[LOAD]");

    private final int level;

    private final String prefix;

    LogLevel(int level, String prefix) {
        this.level = level;
        this.prefix = prefix;
    }

    public int getLevel() {
        return level;
    }

    public String getPrefix() {
        return prefix;
    }

}
