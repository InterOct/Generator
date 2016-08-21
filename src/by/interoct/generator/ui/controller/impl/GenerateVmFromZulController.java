package by.interoct.generator.ui.controller.impl;

import by.interoct.generator.entity.ClassModel;
import by.interoct.generator.entity.Variable;
import by.interoct.generator.exception.GeneratorException;
import by.interoct.generator.logic.VMGenerator;
import by.interoct.generator.ui.controller.ControllerBase;
import by.interoct.parser.dom.entity.Element;
import by.interoct.parser.dom.exception.ReadSourceException;
import by.interoct.parser.dom.helper.ReadSourceFactory;
import by.interoct.parser.dom.logic.ReadSource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Branavets_AY on 8/4/2016.
 */
public class GenerateVmFromZulController extends ControllerBase {

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
        colType.setCellValueFactory(new PropertyValueFactory<Variable, String>(TYPE));
        colName.setCellValueFactory(new PropertyValueFactory<Variable, String>(NAME));
        Element root;
        try (ReadSource readSource = ReadSourceFactory.getInstance()
                .getReadSource("D:\\workspaces\\cbt-nominated\\mca-staff-customer-ams\\mca-staff-customer-ams-composites\\src\\main\\resources\\web\\view\\customer\\product\\profile\\productProfile.zul")) {
            ClassModel classModel = VMGenerator.getInstance().generate(readSource);
            table.setItems(FXCollections.observableList(classModel.getFields()));
        } catch (ReadSourceException | GeneratorException e) {
            e.printStackTrace();
        }
    }

    public void postInit() {


    }

    public void back(ActionEvent actionEvent) {
    }

    public void save(ActionEvent actionEvent) {

    }
}
