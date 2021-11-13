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
package io.github.gleidson28.global.creators;

import io.github.gleidson28.App;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/10/2021
 */
public enum SnackBarCreator {

    INSTANCE;

    private StackPane  scene;

    private final Label lblMessage = new Label();

    private final Timeline open   = new Timeline();
    private final Timeline close  = new Timeline();

    SnackBarCreator() {
        defineContents();
    }

    private void defineContents() {
        scene = (StackPane)  App.INSTANCE.getDecorator().lookup("#scene");
    }

    public void createSnackBar(String message, SnackType type) {

        reset();

        lblMessage.setText(message);
        scene.setAlignment(Pos.BOTTOM_CENTER);

        lblMessage.setMinHeight(50);
        lblMessage.setPadding(new Insets(10));

        StackPane.setMargin(lblMessage, new Insets(0,0,0,10));

        open.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(lblMessage.translateYProperty(),
                                (lblMessage.getMinHeight()))),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(lblMessage.translateYProperty(), -10 ) )
        );


        close.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(lblMessage.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(lblMessage.translateYProperty(),  lblMessage.getMinHeight()))
        );

        SVGPath icon = new SVGPath();
        icon.setStyle("-fx-fill : white;");

        switch (type) {
            case ERROR:
                lblMessage.setStyle("-fx-background-color : -grapefruit; " +
                        "-fx-text-fill : white; -fx-background-radius : 10px;");

                icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z");
                break;
            case SUCCESS:
                lblMessage.setStyle("-fx-background-color : -success; " +
                        "-fx-text-fill : white; -fx-background-radius : 10px;");

                icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z");

                break;
            case WARNING:
                lblMessage.setStyle("-fx-background-color : -warning; " +
                        "-fx-text-fill : white; -fx-background-radius : 10px;");

                icon.setContent("M12 22c1.1 0 2-.9 2-2h-4c0 1.1.89 2 2 2zm6-6v-5c0-3.07-1.64-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.63 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z");
                break;
            case INFO:
                lblMessage.setStyle("-fx-background-color : -info; " +
                        "-fx-text-fill : white; -fx-background-radius : 10px;");

                icon.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z");
                break;
        }

        lblMessage.setGraphicTextGap(10D);
        lblMessage.setGraphic(icon);

        if(!scene.getChildren().contains(lblMessage)) {
            scene.getChildren().add(lblMessage);
        }

        if(open.getStatus() != Animation.Status.RUNNING &&
                close.getStatus() != Animation.Status.RUNNING)
            open.play();

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(open.getStatus() != Animation.Status.RUNNING &&
                        close.getStatus() != Animation.Status.RUNNING)
                    close.play();
            }
        };

        timer.schedule(timerTask, 1000);

    }

    public void createSnackBar(String message) {
        createSnackBar(message, SnackType.SUCCESS);
    }

    private void reset(){

    }
}
