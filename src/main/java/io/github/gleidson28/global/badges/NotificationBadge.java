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
package io.github.gleidson28.global.badges;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/10/2021
 */
public class NotificationBadge extends StackPane {

    public NotificationBadge() {
        Button action = new Button();
        action.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        action.getStyleClass().add("btn-transparent");

        action.setMinHeight(30);

        Label info = new Label("13");
        info.setAlignment(Pos.CENTER);
        info.setPrefSize(16,16);
        info.getStyleClass().addAll("round","success", "info");
        info.setStyle("-fx-font-weight : bold; -fx-text-fill : white; -fx-font-size : 8;");

        SVGPath icon = new SVGPath();
        icon.setContent("M14.4 6L14 4H5v17h2v-7h5.6l.4 2h7V6z");
        icon.setStyle("-fx-fill : -icon-color;");

        this.getChildren().addAll(action, info);
        this.setAlignment(Pos.TOP_RIGHT);

        action.setGraphic(icon);

    }
}
