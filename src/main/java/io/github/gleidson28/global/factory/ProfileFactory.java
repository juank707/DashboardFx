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
package io.github.gleidson28.global.factory;

import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/09/2021
 */
public class ProfileFactory<E extends User> implements Callback<TableColumn<E, E>, TableCell<E, E>> {


    @Override
    public TableCell<E, E> call(TableColumn<E, E> param) {

        return new TableCell<E, E>() {

            @Override
            protected void updateItem(E item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {

                    HBox box = new HBox();
                    box.setAlignment(Pos.CENTER_LEFT);
                    box.setSpacing(10);

                    Circle clip = new Circle();

                    clip.setStroke(Color.WHITE);
                    clip.setStrokeWidth(0.6);

                    ImagePattern imagePattern = new ImagePattern(item.getAvatar("50"));

                    clip.setFill(imagePattern);
                    double frac = 16;

                    clip.setRadius(frac);
                    clip.setCenterX(frac);
                    clip.setCenterY(frac);

                    box.getChildren().addAll(clip, new Label(item.getName() + " " + item.getLastName()));

                    setItem(item);
                    setGraphic(box);
                    setText(null);

                } else {
                    setItem(null);
                    setGraphic(null);
                    setText(null);
                }
            }
        };
    }
}
