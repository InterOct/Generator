package by.interoct.generator.ui.controller.impl.preview.zul;

import by.interoct.generator.ui.controller.BaseData;

/**
 * @author aleks on 22.08.2016.
 */
public class ZULPreviewData extends BaseData {
    private String path;

    public String getPath() {
        return path;
    }

    public ZULPreviewData setPath(String path) {
        this.path = path;
        return this;
    }
}
