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

import animatefx.animation.BounceIn;
import animatefx.animation.BounceOut;
import io.github.gleidson28.App;
import io.github.gleidson28.decorator.GNDecorator;
import io.github.gleidson28.global.util.Positioner;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * System creator of popups and drawers-popups
 * The drawer has animated from time lines and
 * popups with animation fx.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2020
 *
 * Refactor 03/02/21
 */
public enum AlertCreator {

    INSTANCE;

    private StackPane   foreground;
    private AnchorPane  foreContent;

    private final StackPane   root    = new StackPane();
    private final VBox        content = new VBox();

    private final GNDecorator decorator;
    private final Positioner  positioner;
    private final Scene       scene;

    private final Stage         stage         = new Stage();
    private final ColorAdjust   colorAdjust   = new ColorAdjust();

    AlertCreator() {

        decorator = App.INSTANCE.getDecorator();

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(decorator.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);

        getContents();

        positioner = new Positioner(stage, decorator);

        root.setStyle("-fx-background-color : transparent;");
        content.setStyle("-fx-background-color : -background-color; -fx-background-radius : 10 10 0 0;");

        root.setPrefSize(500,300);
//        content.setMaxWidth(200);
        content.setAlignment(Pos.CENTER);
        root.getChildren().addAll(content);
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        colorAdjust.setBrightness(0.24);
    }

    public void createAlert(AlertType alertType, String title, String message, Button... actions) {
//        reset();

        getContents();

        if(decorator.getWidth() < 600) {
            content.setMaxWidth(decorator.getWidth() - 20);
        }

        content.getChildren().setAll(
                createAlertHeader(alertType),
                createContent(title, message),
                createActions(alertType, actions)
        );


        if(root.getScene() == null) {
            stage.setScene(scene);
        } else stage.setScene(root.getScene());

        stage.getScene().getStylesheets().setAll(decorator.getStylesheets());

        stage.setX(decorator.getX());
        stage.setY(decorator.getY());

        positioner.setPosition(root, Pos.CENTER);

        foreground.toFront();
        foreContent.setVisible(false);
        stage.show();

//        organizeInCenter();

        BounceIn bounceIn = new BounceIn(root);
        foreground.toFront();
        bounceIn.play();
//
        foreground.setOnMouseClicked(null);


    }

    private VBox createAlertHeader(AlertType type){
//        App.getDecorator().block();
        VBox header = new VBox();
//        header.minWidthProperty().bind(customDialog.minWidthProperty());
        header.setMinHeight(120);
        header.setAlignment(Pos.CENTER);
//        VBox.setVgrow(header, Priority.ALWAYS);

        ImageView icon = null;
        Color color = null;

        switch (type){
            case INFO:
                color = Color.web("#33B5E5");
                icon = new ImageView(new Image("/theme/img/info_48dp.png"));
                break;
            case WARNING:
                color = Color.web("#FC6E51");
                icon = new ImageView(new Image("/theme/img/warning_48dp.png"));

                break;
            case ERROR:
                color = Color.web("#ED5565");
                icon = new ImageView(new Image("/theme/img/error_48dp.png"));
                break;
            case DONE:
                color = Color.web("#02C852");
                icon = new ImageView(new Image("/theme/img/done_48dp.png"));
                break;
        }

        header.setEffect(colorAdjust);

        header.setBackground(new Background(new BackgroundFill(color, new CornerRadii(
                10,false), Insets.EMPTY)));

        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setFitWidth(151);
        icon.setFitHeight(78);

        header.getChildren().add(icon);
        return header;
    }

    private static VBox  createContent(String title, String message){
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20D);

        VBox.setVgrow(container, Priority.ALWAYS);

        VBox.setMargin(container, new Insets(10,0,0,0));

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("h2");
        lblTitle.setStyle("-fx-text-fill : -text-color; -fx-font-weight : bold;");

        Label text = new Label();
        text.setWrapText(true);
        text.setText(message);
        text.setAlignment(Pos.CENTER);
        lblTitle.setStyle("-fx-text-fill : -text-color;");

        container.getChildren().addAll(lblTitle, text);

        return container;
    }

    private HBox createActions(AlertType alertType, Button... actions){
        HBox _actions = new HBox();
        _actions.setMinSize(100, 73);
        _actions.setAlignment(Pos.CENTER);
        VBox.setMargin(_actions, new Insets(10, 10, 10, 10));
        _actions.setSpacing(5D);

        _actions.getChildren().setAll(actions);

        _actions.getChildren().stream().filter(p -> p instanceof Button).map( c -> (Button) c).forEach(b -> b.setPrefWidth(100));
        _actions.getChildren().forEach( e -> e.addEventFilter(ActionEvent.ACTION, event -> {
            BounceOut bounceOut = new BounceOut(root);
            bounceOut.getTimeline().setOnFinished(ev -> close());
            bounceOut.play();
        }));



        switch (alertType){
            case ERROR:
                _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-danger"));
                break;
            case WARNING:
                _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-warning"));
                break;
            case INFO:
                _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-info"));
                break;
            case DONE:
                _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-success"));
                break;
        }

        _actions.getChildren().forEach( c -> c.setEffect(colorAdjust));

        _actions.getChildren().stream().filter(e -> e instanceof Button).forEach(e -> ((Button) e).setPrefWidth(80));
        return _actions;
    }

    public void close() {
        stage.close();
        foreContent.setVisible(true);
        foreground.toBack();
    }

    private void getContents(){
        foreground = (StackPane) decorator.lookup("#foreground");

        foreContent = (AnchorPane) foreground.lookup("#fore-content");
//        StackPane customDialog = (StackPane) foreground.lookup("#custom-dialog");
    }
}