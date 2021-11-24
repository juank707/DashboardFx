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

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class TypeSelector extends Selector {

    private Control control;
    private PropertyAside aside;
    private final VBox content = new VBox();
    private final ToggleGroup group = new ToggleGroup();

    private String oldText;

    public TypeSelector(Control control, String... options) {

        this.control = control;

        if(control instanceof Button) {
            oldText = ((Button) control).getText();
        }

        Label title = new Label("Type");
        title.getStyleClass().add("h5");

        this.setSpacing(10D);
        content.setAlignment(Pos.CENTER_LEFT);

        RadioButton _default = new RadioButton("Default");
        _default.setToggleGroup(group);
        content.getChildren().addAll(_default);

        this.getChildren().addAll(createSeparator(), title, content);

        group.selectToggle(_default);

        content.setSpacing(2D);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            String newV = ( (ToggleButton) newValue).getText();

            String oldV = null;

            if (oldValue != null) {
                oldV = ( (ToggleButton) oldValue).getText();
            }

            if(oldV != null ) {

                String finalOldV = oldV;
                Platform.runLater( () -> {

                    control.getStyleClass().removeAll(control.getProperties().get("prefix") +  finalOldV.toLowerCase());
                    aside.removeStyleClass(control.getProperties().get("prefix")  + finalOldV.toLowerCase() + ", ");
                });

            }

            if (!newV.equals("Default") && aside != null ) {

                if(control instanceof Button) {
                    if(newV.equalsIgnoreCase("round")) {
                        ((Button) control).setText("R");
                        control.setMinWidth(40);
                        control.setPrefWidth(40);
                    } else {
                        ((Button) control).setText(oldText);
                        control.setMinWidth(Region.USE_COMPUTED_SIZE);
                        control.setPrefWidth(80);
                    }

                }

                Platform.runLater(() -> {
                    control.getStyleClass().addAll(control.getProperties().get("prefix")  + newV.toLowerCase());
                    aside.addStyleClass(control.getProperties().get("prefix")  + newV.toLowerCase() + ", ");
                });

            }


        });

        addOptions(options);
    }

    public void addOptions(String... options) {
        for (String r : options) {
            RadioButton radioButton = new RadioButton(r);
            radioButton.setToggleGroup(group);
            content.getChildren().addAll(radioButton);
        }
    }


    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {

    }
}
