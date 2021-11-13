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
package io.github.gleidson28.module.layout;

import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/11/2021
 */
public class CreativeTab extends Tab {

    public CreativeTab(String title, Control control, String... clazz) {

        this.setText(title);

        TilePane body = new TilePane();
        VBox content = new VBox(body);
        content.getStyleClass().add("background-color");

        if(control instanceof Labeled) {
            Label btn = (Label) control;
            btn.setText(title);
            Button newButton = new Button("Alternate");
        }

    }

    private void createItem() {

    }
}
