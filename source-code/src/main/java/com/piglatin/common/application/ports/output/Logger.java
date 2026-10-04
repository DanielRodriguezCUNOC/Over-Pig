package com.piglatin.common.application.ports.output;

/**
 * Output port for logging
 */

public interface Logger {

    void trace (String message);
    void debug (String message);
    void info (String message);
    void warn (String message);
    void error (String message);
    void error (String message, Throwable throwable);
}
