package by.interoct.generator.ui.controller.impl.preview.vm;

import by.interoct.generator.entity.Variable;
import by.interoct.generator.ui.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Branavets_AY on 8/4/2016.
 */
public class VmPreviewCtrl extends BaseController<VmPreviewData> {

    private static final String TYPE = "type";
    private static final String NAME = "name";

    @FXML
    public Button btnSave;
    @FXML
    public TableView<Variable> table;
    @FXML
    public TableColumn<Variable, String> colType;
    @FXML
    public TableColumn<Variable, String> colName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sInitialize() {


    }

    public void back(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {

    }
}
