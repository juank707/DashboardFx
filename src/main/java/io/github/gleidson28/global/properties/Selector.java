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
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public abstract class Selector extends VBox {

    public abstract void setAside(PropertyAside aside);

    public abstract void reset();

    protected VBox createSeparator() {
        VBox separator = new VBox();

        separator.getStyleClass().addAll("border");
        separator.setStyle("-fx-background-color : -light-gray;");

        separator.setPrefHeight(1);

        VBox.setMargin(separator, new Insets(5,0,0,0));
        return separator;
    }


}
