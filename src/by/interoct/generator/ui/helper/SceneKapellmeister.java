package by.interoct.generator.ui.helper;

import by.interoct.generator.ui.controller.BaseController;
import by.interoct.generator.ui.controller.BaseData;

/**
 * @author aleks on 22.08.2016.
 */
public class SceneKapellmeister {

    public static <T extends BaseData, V extends BaseController<T>, I> void pilot(T data, Class<V> clazz, Class<I> invokerClass) {
        SceneContainer<V> container = ScenePool.instance().get(clazz);
        V controller = container.getController();
        data.setInvoker(invokerClass);
        controller.setData(data);
        controller.sInitialize();
        container.getWindow().setScene(container.getScene());
    }

}
