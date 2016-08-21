package by.interoct.generator.ui.helper.exception;

/**
 * @author aleks on 22.08.2016.
 */
public class ScenePoolException extends RuntimeException {
    public ScenePoolException() {
    }

    public ScenePoolException(String message) {
        super(message);
    }

    public ScenePoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScenePoolException(Throwable cause) {
        super(cause);
    }

    public ScenePoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
