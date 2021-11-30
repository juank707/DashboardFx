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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/11/2021
 */
public class SizeSelector extends Selector {

    private Control control;
    private ToggleGroup group   = new ToggleGroup();
    private HBox        content = new HBox();

    private PropertyAside aside;
    private String _default;

    public SizeSelector(Control control, String _default) {

        this.control = control;
        this._default = _default;

        Label title = new Label("Size");
        title.getStyleClass().add("h5");

        this.setSpacing(10D);
        content.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(createSeparator(), title, content);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                if (newValue instanceof ToggleButton) {
                        String newV = (String) newValue.getUserData();

                        if(oldValue != null ) {
                            if (aside != null) {
                                String oldV = (String) oldValue.getUserData();
                                control.getStyleClass().removeAll("input-" + oldV.toLowerCase());
                                aside.removeStyleClass("input-" + oldV.toLowerCase() + ", ");
                            }
                        }

                        if(!newV.equalsIgnoreCase(_default) && aside != null) {
                            control.getStyleClass().addAll("input-" + newV.toLowerCase());
                            aside.addStyleClass("input-" + newV.toLowerCase() + ", ");
                        }
                }
            }
        });
        addOptions("Small", "Medium", "Large");
    }

    public void addOptions(String... options) {

        for (String i : options) {

            ToggleButton typeButton = new ToggleButton(i);
            typeButton.setUserData(i);
            typeButton.setToggleGroup(group);

            typeButton.setMinSize(40, 40);
            typeButton.setPrefWidth(80);

            typeButton.setContentDisplay(ContentDisplay.TEXT_ONLY);

            typeButton.getStyleClass().addAll("border", "toggle-light");
            typeButton.setStyle("-fx-border-width : 1px;");

            if(i.equalsIgnoreCase("small")) {
                typeButton.getStyleClass().addAll("toggle-left");
            } else if(i.equalsIgnoreCase("medium")) {
                typeButton.getStyleClass().addAll("toggle-center");
            } else {
                typeButton.getStyleClass().addAll("toggle-right");
            }


            if(i.equalsIgnoreCase(_default)) {
                typeButton.setSelected(true);
            }
//            color.setStyle("-base : -" + i.toLowerCase() + ";");

            content.getChildren().add(typeButton);
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
