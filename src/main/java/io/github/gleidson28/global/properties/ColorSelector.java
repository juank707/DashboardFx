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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class ColorSelector extends Selector {

    private Control control;

    private ToggleGroup group = new ToggleGroup();

    private PropertyAside aside;

    private GridPane content = new GridPane();

    public ColorSelector(Control control, String... items) {

        this.control = control;

        Label title = new Label("Color");
        title.getStyleClass().add("h5");

        content.setAlignment(Pos.CENTER_LEFT);

        Circle color = new Circle();
        color.setStroke(Color.TRANSPARENT);
        color.setRadius(11D);

        this.getChildren().addAll(createSeparator(), title, content);

        VBox.setMargin(title, new Insets(5,0,0,0));

        content.setHgap(5D);
        content.setVgap(5D);
        content.setPadding(new Insets(5D));

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null) {
                String newV = (String) newValue.getUserData();

                color.setStyle("-base : -" + newV.toLowerCase());

                if(oldValue != null) {
                    String oldV = (String) oldValue.getUserData();
                    control.getStyleClass().removeAll(control.getProperties().get("prefix")   + oldV.toLowerCase());
                    aside.removeStyleClass(control.getProperties().get("prefix")  + oldV.toLowerCase() + ", ");
                }

                if (aside != null) {
                    control.getStyleClass().addAll(control.getProperties().get("prefix") + newV.toLowerCase());
                    aside.addStyleClass(control.getProperties().get("prefix") + newV.toLowerCase() + ", ");
                }
            }
        });

        addOptions(items);
    }

    public void addOptions(String... items) {

        int _count = 0;
        int _row = 0;
        int _column = 0;

        for (String i : items) {

            if(_count == 5) {
                _row += 1;
                _column = 0;
            }

            ToggleButton color = new ToggleButton();
            color.setUserData(i);
            color.setToggleGroup(group);

            color.setMinSize(30, 30);

            color.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            color.getStyleClass().addAll("rounded");
            color.setStyle("-base : -" + i.toLowerCase() + ";");

            content.getChildren().add(color);
            GridPane.setConstraints(color, _column, _row );

            if(i.equalsIgnoreCase("info"))
                group.selectToggle(color);

            _count += 1;
            _column += 1;
        }

//        chColor.getSelectionModel().selectFirst();
    }

    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {

    }
}
