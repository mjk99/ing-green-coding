package org.example.atm;

public final class RequestPriority {
    public static int of(String status) {
        return switch (status) {
            case "STANDARD" -> 0;
            case "SIGNAL_LOW" -> 1;
            case "PRIORITY" -> 2;
            case "FAILURE_RESTART" -> 3;
            default -> 0;
        };
    }
}
