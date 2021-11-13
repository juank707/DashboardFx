/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidson28.module.controls;

import io.github.gleidson28.global.enhancement.ObserverView;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/11/2021
 */
public class CheckBoxController implements Initializable, ObserverView {

    @FXML private BorderPane        body;
    @FXML private ChoiceBox<String> chColor;
    @FXML private ChoiceBox<String> chSize;
    @FXML private Circle            cColor;
    @FXML private TextArea          styleArea;
    @FXML private TextArea          javaArea;
    @FXML private TextArea          fxmlArea;

    @FXML private ToggleGroup group;
    @FXML private ToggleGroup typeGroup;


    @FXML private CheckBox checkBox;

    private final ObservableList<String> styleClass   = FXCollections.observableArrayList();
    private final ObservableList<String> java         = FXCollections.observableArrayList();

    private void reset() {
        checkBox.setSelected(false);
        checkBox.setIndeterminate(false);
        checkBox.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//
//        PropertyAside propertyAside = new PropertyAside(checkBox);
//
//        ColorSelector colorSelector = new ColorSelector(checkBox, propertyAside);
//        colorSelector.addOptions("Primary", "Info", "Success", "Warning", "Danger", "Secondary");
//
//        StateSelector stateSelector = new StateSelector(checkBox, propertyAside);
//        stateSelector.addOptions("Selected", "Disabled", "Indeterminate");
//
//        TypeSelector typeSelector = new TypeSelector(checkBox, propertyAside);
//        typeSelector.addOptions("Default", "Rounded");
//
//        propertyAside.addSelectors(colorSelector, stateSelector, typeSelector);
////        propertyAside.addSelector(stateSelector);
//
//        body.setLeft(propertyAside);

//        typeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//
//            String newV = ( (ToggleButton) newValue).getText();
//
//            String oldV = null;
//
//            if (oldValue != null) {
//                oldV = ( (ToggleButton) oldValue).getText();
//            }
//
//            if(oldV != null) {
//
//                checkBox.getStyleClass().removeAll("check-" +  oldV.toLowerCase());
//                styleClass.removeAll("check-" + oldV.toLowerCase() + ", ");
//                java.add("check-" + oldV.toLowerCase() + ", ");
//
//            }
//
//            if (newV.equals("Rounded")) {
//
//                checkBox.getStyleClass().addAll("check-" + newV.toLowerCase());
//                styleClass.add("check-" + newV.toLowerCase() + ", ");
//                java.add("check-" + newV.toLowerCase() + ", ");
//            }
//
//            read();
//
//        });
//
//        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//              String newV = ( (RadioButton) newValue).getText();
//                switch (newV) {
//                    case "Default":
//                        reset();
//                        break;
//                    case "Indeterminate":
//                        reset();
//                        checkBox.setIndeterminate(true);
//                        break;
//                    case "Selected":
//                        reset();
//                        checkBox.setSelected(true);
//                        break;
//                    default:
//                        reset();
//                        checkBox.setDisable(true);
//                        break;
//            }
//        });
//
//        chColor.getItems().addAll(
//                "Danger",
//                "Info",
//                "Success"
//        );
//
//        chSize.getItems().addAll(
//                "Small", "Medium", "Large"
//        );
//
//        chColor.getSelectionModel().select("Info");
//        chSize.getSelectionModel().select("Small");
//
//        chColor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//
//            cColor.setStyle("-fx-fill : -" + newValue.toLowerCase());
//
//            if(oldValue != null) {
//                checkBox.getStyleClass().removeAll("check-" + oldValue.toLowerCase());
//                styleClass.removeAll("check-" + oldValue.toLowerCase() + ", ");
//            }
//
//            checkBox.getStyleClass().addAll("check-" + newValue.toLowerCase());
//            styleClass.add("check-" + newValue.toLowerCase() + ", ");
//            read();
//        });
//
//        chSize.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//
//            if(oldValue != null) {
//                checkBox.getStyleClass().removeAll("check-" + oldValue.toLowerCase());
//                styleClass.removeAll("check-" + oldValue.toLowerCase() + ", ");
//                java.removeAll("check-" + oldValue.toLowerCase() + ", ");
//            }
//
//            if (!newValue.equals("small")) {
//                checkBox.getStyleClass().addAll("check-" + newValue.toLowerCase());
//                styleClass.add("check-" + newValue.toLowerCase() + ", ");
//                java.add("check-" + newValue.toLowerCase() + ", ");
//            }
//
//            read();
//        });
//

    }

    private void read() {

        styleArea.setText(null);
        javaArea.setText(null);
        javaArea.setText(
                "CheckBox checkBox = new CheckBox(\"CheckBox\");" +
                        "\n checkBox.getStyleClass() \n .addAll("
        );

//        checkBox.getStyleClass().clear();

        for (String newValue : styleClass) {
            styleArea.appendText(newValue);
        }
        styleArea.replaceText(styleArea.getText().lastIndexOf(","), styleArea.getText().length(), "");

        for (String newValue : java) {
            javaArea.appendText(  newValue + "\n");
        }

        javaArea.replaceText(javaArea.getText().lastIndexOf(","), javaArea.getText().length(), "\");");


//        checkBox.getStyleClass().addAll(styleClass);

    }

    private ChangeListener<Number> resizeBlocks = (observable, oldValue, newValue) -> {
          if(newValue.doubleValue() < 500) {

          }
    };

    @Override
    public List<ChangeListener<Number>> getListeners() {
        return Arrays.asList(resizeBlocks);
    }
}
