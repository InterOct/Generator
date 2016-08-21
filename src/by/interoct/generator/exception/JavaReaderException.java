package by.interoct.generator.exception;

/**
 * Created by Branavets_AY on 7/29/2016.
 */
public class JavaReaderException extends Exception {
    public JavaReaderException() {
    }

    public JavaReaderException(String message) {
        super(message);
    }

    public JavaReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavaReaderException(Throwable cause) {
        super(cause);
    }

    public JavaReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
