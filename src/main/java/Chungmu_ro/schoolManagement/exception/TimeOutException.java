package Chungmu_ro.schoolManagement.exception;

public class TimeOutException extends RuntimeException {
    public TimeOutException() {
    }

    public TimeOutException(String message) {
        super(message);
    }

    public TimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutException(Throwable cause) {
        super(cause);
    }

    public TimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
