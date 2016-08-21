package by.interoct.generator.ui.constant;

import by.interoct.generator.ui.controller.Updatable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListenersUtil {
    private ListenersUtil() {
    }

    public static ChangeListener<Boolean> pathValidationListener(final Updatable parent, final TextField tf, final String extension) {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        Path path = Paths.get(tf.getText());
                        if (tf.getText().endsWith(extension) && Files.exists(path) && !Files.isDirectory(path)) {
                            tf.setEffect(EffectsUtil.OK_HIGHLIGHT);
                        } else {
                            tf.setEffect(EffectsUtil.ERROR_HIGHLIGHT);
                        }
                    } catch (InvalidPathException e) {
                        tf.setEffect(EffectsUtil.ERROR_HIGHLIGHT);
                    }

                }
                parent.update();
            }
        };
    }

}
