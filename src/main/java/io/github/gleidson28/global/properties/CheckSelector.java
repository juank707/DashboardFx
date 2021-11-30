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

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class CheckSelector extends Selector {

    private final Control control;
    private PropertyAside aside;
    private final VBox content = new VBox();

    public CheckSelector(Control control, String... options) {

        this.control = control;
        Label title = new Label("Options");
        title.getStyleClass().add("h5");
        this.setSpacing(5D);
        content.setSpacing(5D);
        content.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(createSeparator(), title, content);
        content.setSpacing(2D);
        addOptions(options);
    }

    private IconSelector iconSelector;
    private ActionSelector typeSelector;

    public void addOptions(String... options) {
        for (String r : options) {
            CheckBox checkBox = new CheckBox(r);
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if(checkBox.getText().toLowerCase().
                            replaceAll(" ", "-")
                            .equals("lead-icon")) {
                        iconSelector = new IconSelector(control);
                        iconSelector.setAside(aside);
                        aside.addSelector(iconSelector);
                    } else if(checkBox.getText().toLowerCase()
                            .replaceAll(" ", "-").equals("action-button")) {
                        typeSelector = new ActionSelector(control, this.aside);
                        typeSelector.setAside(aside);
                        aside.addSelector(typeSelector);
                    } else {
                        addClass(control, checkBox);
                    }
                } else {
                    if(checkBox.getText().toLowerCase().
                            replaceAll(" ", "-")
                            .equals("lead-icon")) {
                        aside.removeSelector(iconSelector);
                    } else if(checkBox.getText().toLowerCase()
                        .replaceAll(" ", "-").equals("action-button")) {
                        aside.removeSelector(typeSelector);
                        removeClass(control, checkBox);
                    } else {
                        removeClass(control, checkBox);
                    }
                }
            });
            content.getChildren().addAll(checkBox);
        }
    }

    private void removeClass(Control control, CheckBox checkBox) {

        String _class = checkBox.getText().toLowerCase().replaceAll(" ", "-");

        control.getStyleClass().removeAll(_class);
        aside.removeStyleClass(
                _class + ",");

    }

    private void addClass(Control control, CheckBox checkBox) {

        control.getStyleClass().addAll(
                checkBox.getText().toLowerCase().replaceAll(" ", "-"));
        aside.addStyleClass(
                checkBox.getText().toLowerCase().toLowerCase().replaceAll(" ", "-") + ",");

    }

    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {

    }
}
