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

import animatefx.animation.AnimationFX;
import animatefx.animation.Bounce;
import animatefx.animation.Pulse;
import io.github.gleidson28.App;
import io.github.gleidson28.decorator.GNDecorator;
import io.github.gleidson28.global.util.Positioner;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * System creator of popups and drawers-popups
 * The drawer has animated from time lines and
 * popups with animation fx.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2020
 */
public enum PopupCreator {

    INSTANCE;

    private final GNDecorator   decorator;
    private final StackPane     foreground;
    private final AnchorPane    foreContent;
    private final Positioner    positioner;

    private final Stage stage = new Stage();

    private StackPane     _root = null;


    PopupCreator () {
        decorator = App.INSTANCE.getDecorator();

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(decorator.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);

        foreground = (StackPane) App.INSTANCE.getDecorator().lookup("#foreground");
        foreContent = (AnchorPane) foreground.lookup("#fore-content");
        positioner = new Positioner(stage, decorator);
    }

    public void createPopup(Parent content) {
        createPopup(new StackPane(content), Pos.CENTER, new Bounce());
    }

    public void createPopup(StackPane content) {
        createPopup(content, Pos.CENTER, new Bounce());
    }

    public void createPopup(StackPane content, AnimationFX animation) {
        createPopup(content, Pos.CENTER, animation);
    }

    public void createPopup(StackPane content, Pos pos) {
        createPopup(content, pos, new Pulse());
    }

    public void createPopup(StackPane content, Pos pos, EventHandler<WindowEvent> event) {
        stage.setOnShown(event);
        createPopup(content, pos, new Pulse());
    }

    public void createPopup(StackPane content, Pos pos, AnimationFX animation) {
        createPopup(content, pos, animation, 2.0F);
    }

    public void createPopup(StackPane root, Pos pos, AnimationFX animation, float speed) {

        if(stage.isShowing()) return;

        stage.requestFocus();

        if(root.getScene() == null) {
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } else stage.setScene(root.getScene());

        animation.setNode(root);

        _root = root;

        if(root.getUserData() == null) {
            root.setUserData(root.getPrefWidth());
        }

        if(decorator.getWidth() < 600) {
            root.setMaxWidth(decorator.getWidth() - 20);
        }

        if(root.getPrefWidth() > decorator.getWidth()) {
            root.setPrefWidth(decorator.getWidth() - 20);
        } else {
            root.setPrefWidth( (double) root.getUserData());
        }

        stage.setX(decorator.getX());
        stage.setY(decorator.getY());

        positioner.setPosition(root, pos);

        foreground.toFront();
        foreContent.setVisible(false);
        stage.show();
        animation.setSpeed(speed);
        animation.play();
//
        foreground.setOnMouseClicked( event -> {
            close();
        });
    }

    public void updateContent(StackPane content) {
        updateContent(content, new Pulse(content));
    }

    public void updateContent(StackPane content, AnimationFX animationFX) {

        stage.close();

        if(content.getScene() == null) {
            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } else stage.setScene(content.getScene());

        stage.setX(decorator.getX());
        stage.setY(decorator.getY());

        _root = content;

        if(_root.getUserData() == null) {
            content.setUserData(content.getPrefWidth());
        }

        if(_root.getPrefWidth() > decorator.getWidth()) {
            _root.setPrefWidth(decorator.getWidth() - 20);
        } else {
            _root.setPrefWidth( (double) _root.getUserData());
        }


        positioner.setPosition(content, Pos.CENTER);

        foreground.toFront();
        foreContent.setVisible(false);
        stage.show();

        animationFX.setNode(content);
        animationFX.play();
    }

    public void close () {
        stage.close();
        foreContent.setVisible(true);
        foreground.toBack();

        if(_root.getUserData() != null) {
            _root.setPrefWidth( (double) _root.getUserData());
        }
    }

    public boolean isShowing () {
        return stage.isShowing();
    }

    public void setOnClosed(EventHandler<WindowEvent> event) {
        stage.setOnHiding(event);
    }

    public void setOnShow(EventHandler<WindowEvent> event) {
        stage.setOnShown(event);
    }

}