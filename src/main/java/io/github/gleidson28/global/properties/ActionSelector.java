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

import io.github.gleidson28.global.skin.textField.ActionButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class ActionSelector extends Selector {

    private Control control;
    private PropertyAside aside;
    private final VBox content = new VBox();
    private final ToggleGroup group = new ToggleGroup();

    private String oldText;

    private final ObservableList<String> _class = FXCollections.observableArrayList();

    public ActionSelector(Control control, PropertyAside aside) {
        this.control = control;
        this.aside = aside;

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        for (ActionButtonType c : ActionButtonType.values()) {

            if (c.equals(ActionButtonType.NULL) || c.equals(ActionButtonType.NONE)) break;

            choiceBox.getItems().add(c.name());
        }

        Label title = new Label("Action Type");
        title.getStyleClass().add("h5");

        VBox box = new VBox(choiceBox);
        box.setAlignment(Pos.CENTER);
        choiceBox.setPrefWidth(300);
        box.setMinHeight(50);

        getChildren().addAll(createSeparator(), title, box);

        VBox.setMargin(title, new Insets(10,0,0,0));

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue != null) {
                removeClass(control, oldValue.toLowerCase());
            }

            if (newValue != null) {
                 addClass(control, newValue.toLowerCase() );
            }

        });

        choiceBox.getSelectionModel().selectFirst();

    }

    private void removeClass(Control control, String _class) {

        control.getStyleClass().removeAll( "action-" +
                _class);

        aside.removeStyleClass( "action-" +
                _class + ", ");
    }

    private void addClass(Control control, String _clazz) {

        control.getStyleClass().addAll( "action-" +
                _clazz);

        if (aside != null)
        aside.addStyleClass( "action-" +
                _clazz + ", ");

        if(!_class.contains("action" + _class)) {
            _class.add(_clazz);
        }
    }

    public ObservableList<String> getClazz() {
        return _class;
    }

    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {
        for (String cl : _class) {
            removeClass(control, cl);
        }
    }
}
