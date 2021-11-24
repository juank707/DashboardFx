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

import io.github.gleidson28.global.popup.Popup;
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
public class MessageBadge extends StackPane {

    public MessageBadge() {
        Button action = new Button();
        action.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        action.getStyleClass().add("btn-transparent");

        action.setMinHeight(30);

        Label info = new Label("8");
        info.setAlignment(Pos.CENTER);
        info.setPrefSize(16,16);
        info.getStyleClass().addAll("round", "info");
        info.setStyle("-fx-text-fill : white; -fx-font-size : 8;");

        SVGPath icon = new SVGPath();
        icon.setContent("M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z");
        icon.setStyle("-fx-fill : -icon-color;");

        this.getChildren().addAll(action, info);
        this.setAlignment(Pos.TOP_RIGHT);

        action.setGraphic(icon);

        this.setOnMouseClicked(event -> {
            Popup dashPopup = new Popup(new BadgeCellMessage());
            dashPopup.showBottomRight(this);
        });
    }
}
