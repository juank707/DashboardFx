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
package io.github.gleidson28.global.properties;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/11/2021
 */
public class TypeSelector extends Selector {

    private Control control;
    private ToggleGroup group   = new ToggleGroup();
    private VBox        content = new VBox();

    private PropertyAside aside;
    private String _default;

//    public TypeSelector(Control control, String... options) {
//        this(control, "Type", options);
//    }

    public TypeSelector(Control control, String title, String... options) {
        this.control = control;

        Label _title = new Label(title);
        _title.getStyleClass().add("h5");

        this.setSpacing(5D);
        content.setSpacing(2D);
        content.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(createSeparator(), _title, content);


        addOptions(options);
    }


    public void addOptions(String... options) {

        RadioButton _default = new RadioButton("Default");
        _default.setContentDisplay(ContentDisplay.TEXT_ONLY);
        _default.setPrefWidth(80);

        _default.setToggleGroup(group);

        content.getChildren().add(_default);
        group.selectToggle(_default);

        for (String i : options) {

            RadioButton radioButton = new RadioButton(i);
            radioButton.setPrefWidth(80);

            radioButton.setToggleGroup(group);
            radioButton.setContentDisplay(ContentDisplay.TEXT_ONLY);

//            typeButton.getStyleClass().addAll("border", "toggle-light");
//            typeButton.setStyle("-fx-border-width : 1px;");

//            if(i.equalsIgnoreCase(_default)) {
//                typeButton.setSelected(true);
//            }
//            color.setStyle("-base : -" + i.toLowerCase() + ";");

            content.getChildren().add(radioButton);
        }

        group.selectedToggleProperty().addListener(this::changed);
    }


    private void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

        if (oldValue != null) {
            String _class = ((RadioButton) oldValue).getText();
            if (control instanceof TextInputControl) {
                removeClass(_class, "input-");
            } else {
                removeClass(_class);
            }
        }

        if (newValue != null) {
            String _class = ( (RadioButton) newValue).getText();

            if (!_class.equalsIgnoreCase("default")) {
                if (control instanceof TextInputControl) {
                    addClass(_class, "input-");
                } else {
                    addClass(_class);
                }
            }
        }

    }



    private void removeClass(String _class) {

        _class = _class.toLowerCase();

        control.getStyleClass().removeAll( control.getProperties().get("prefix") +
                _class);

        aside.removeStyleClass( control.getProperties().get("prefix") +
                _class + ", ");
    }

    private void removeClass(String _class, String prefix) {

        _class = _class.toLowerCase();

        control.getStyleClass().removeAll( prefix +
                _class);

        aside.removeStyleClass( prefix +
                _class + ", ");
    }

    private void addClass(String _class) {

        _class = _class.toLowerCase();

        control.getStyleClass().addAll( control.getProperties().get("prefix") +
                _class);

        if (aside != null)
            aside.addStyleClass( control.getProperties().get("prefix") +
                    _class + ", ");
    }

    private void addClass(String _class, String prefix) {

        _class = _class.toLowerCase();

        control.getStyleClass().addAll( prefix +
                _class);

        if (aside != null)
            aside.addStyleClass( prefix +
                    _class + ", ");
    }


    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {

    }
}
