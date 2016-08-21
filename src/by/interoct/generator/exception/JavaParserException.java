package by.interoct.generator.exception;

/**
 * Created by Branavets_AY on 7/29/2016.
 */
public class JavaParserException extends Exception {
    public JavaParserException() {
    }

    public JavaParserException(String message) {
        super(message);
    }

    public JavaParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavaParserException(Throwable cause) {
        super(cause);
    }

    public JavaParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
