package by.interoct.generator.ui.constant;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class EffectsUtil {

    public static final Effect ERROR_HIGHLIGHT;
    public static final Effect OK_HIGHLIGHT;

    static {
        ERROR_HIGHLIGHT = new Lighting();
        Light.Distant light = new Light.Distant();
        light.setColor(Color.LIGHTPINK);
        ((Lighting) ERROR_HIGHLIGHT).setLight(light);
    }

    static {
        OK_HIGHLIGHT = new Lighting();
        Light.Distant light = new Light.Distant();
        light.setColor(Color.LIGHTGREEN);
        ((Lighting) OK_HIGHLIGHT).setLight(light);
    }

    private EffectsUtil() {
    }
}
