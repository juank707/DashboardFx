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
public class AlertBadge extends StackPane {

    public AlertBadge() {

        Button action = new Button();
        action.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        action.getStyleClass().add("btn-transparent");

        action.setMaxHeight(20);
        action.setMouseTransparent(true);

        Label info = new Label("18");
        info.setAlignment(Pos.CENTER);
        info.setPrefSize(15,15);
        info.getStyleClass().addAll("round","danger", "info");
        info.setStyle("-fx-text-fill : white; -fx-font-size : 8;");
        info.setMouseTransparent(true);
        SVGPath icon = new SVGPath();
        icon.setContent("M12 22c1.1 0 2-.9 2-2h-4c0 1.1.89 2 2 2zm6-6v-5c0-3.07-1.64-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.63 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z");
        icon.setStyle("-fx-fill : -icon-color;");

        this.getChildren().addAll(action, info);
        this.setAlignment(Pos.TOP_RIGHT);

        action.setGraphic(icon);

    }
}
