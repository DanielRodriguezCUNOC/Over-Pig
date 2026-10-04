package com.piglatin.common.infrastructure.adapters;

import com.piglatin.common.application.ports.output.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Show messages with info of all actions.
 */
public class LoggerAdapter implements Logger {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String name;

    public LoggerAdapter(Class<?> clase) {
        this.name = clase.getSimpleName();
    }

    public LoggerAdapter(String name) {
        this.name = name;
    }

    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String output = String.format("[%s] [%s] [%s] %s", timestamp, level, name, message);
        if (level.equals("ERROR") || level.equals("WARN")) {
            System.err.println(output);
        } else {
            System.out.println(output);
        }
    }

    @Override
    public void trace(String message) {
        log("TRACE", message);
    }

    @Override
    public void debug(String message) {
        log("DEBUG", message);
    }

    @Override
    public void info(String message) {
        log("INFO ", message);
    }

    @Override
    public void warn(String message) {
        log("WARN ", message);
    }

    @Override
    public void error(String message) {
        log("ERROR", message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log("ERROR", message);
        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }
}
