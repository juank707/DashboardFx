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
package io.github.gleidson28.global.skin;

import io.github.gleidson28.global.model.Model;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/08/2021
 */
public class RingLoader<T extends Model> extends StackPane {

    private Circle first = new Circle();
    private Circle second = new Circle();
    private Circle third = new Circle();

    private StackPane root;

    private RingLoader(StackPane root) {
        this.root = root;
    }

    public RingLoader() {
        this.setAlignment(Pos.CENTER);

        first.setStyle("-fx-fill: transparent; -fx-stroke-dash-offset : 100; -fx-stroke-dash-array : 100; -fx-stroke : -base;");
        second.setStyle("-fx-fill: transparent; -fx-stroke-dash-offset : 50; -fx-stroke-dash-array : 50; -fx-stroke : -base;");
        third.setStyle("-fx-fill: transparent; -fx-stroke-dash-offset : 50; -fx-stroke-dash-array : 30; -fx-stroke : -base;");

        first.setRadius(100);
        second.setRadius(50);
        third.setRadius(20);

        this.getChildren().addAll(first, second, third);

        rotate(first, 360, 10);
        rotate(second, 180, 18);
        rotate(third, 145, 24);

    }

    private void rotate(Circle circle, int angle, int duration){
        RotateTransition rotate = new RotateTransition(Duration.seconds(duration), circle);


        rotate.setNode(circle);
        rotate.setAutoReverse(true);

        rotate.setByAngle(angle);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(3);
        rotate.setCycleCount(-1);
        rotate.play();
    }

    public void executeTask(Task<ObservableList<T>> task) {

    }
}
