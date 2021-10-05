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
import animatefx.animation.BounceIn;
import animatefx.animation.BounceOut;
import animatefx.animation.Pulse;
import io.github.gleidson28.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * System creator of popups and drawers-popups
 * The drawer has animated from time lines and
 * popups with animation fx.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2020
 */
public enum DrawerCreator {

    INSTANCE;

    DrawerCreator() {
        defineContents();
    }

    private StackPane   foreground;
    private AnchorPane  foreContent;
    private StackPane   customDialog;

    private final Timeline open   = new Timeline();
    private final Timeline close  = new Timeline();

    private final int minDrawerSize = 250;
    private final int velocity      = 200;
    private final double barSize    = 40;
    private       boolean autoClose = true;

    public void createDrawerRight(Node content){

        reset();

//        customDialog.setStyle("-fx-background-color : -foreground-color; -fx-background-radius :  0;");
//        foreground.getChildren().clear();
//        foreground.getChildren().add(foreContent);
//        foreContent.getChildren().clear();
//        foreContent.getChildren().add(customDialog);

        customDialog.setPrefWidth(minDrawerSize);

        customDialog.getChildren().setAll(content);

//        if(!customDialog.getChildren().contains(content)) {
//            customDialog.getChildren().clear();
//            customDialog.getChildren().add(content);
//        }

        organizeInRight();

        open.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(customDialog.translateXProperty(), minDrawerSize)),
                new KeyFrame(Duration.millis(velocity), new KeyValue(customDialog.translateXProperty(), 0))
        );

        close.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(customDialog.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(velocity), new KeyValue(customDialog.translateXProperty(), minDrawerSize) )
        );

        foreground.toFront();
        open.play();

        foreContent.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof AnchorPane){
                if(((AnchorPane) event.getTarget()).getId().equals("fore-content")) {
                    close.play();
                }
            }
        });

        close.setOnFinished(event -> {
            foreground.toBack();
//            App.decorator.showCustomControls();
////            App.decorator.unblockControls();
        });
//        App.decorator.blockControls();
//        App.decorator.hideCustomControls();

    }

    public void createDrawerLeft(Menu hamb, Node content){
        createDrawerLeft(content);
        hamb.getGraphic().setVisible(false);
        close.setOnFinished(event -> {
            hamb.getGraphic().setVisible(true);
            foreground.toBack();
//            App.decorator.showCustomControls();
////            App.decorator.unblockControls();
        });
    }

    public void createDrawerLeft(Button hamb, Node content){
        createDrawerLeft(content);
        hamb.getGraphic().setVisible(false);
        close.setOnFinished(event -> {
            hamb.getGraphic().setVisible(true);
            foreground.toBack();
//            App.decorator.showCustomControls();
////            App.decorator.unblockControls();
        });
    }

    public void createDrawerLeft(Node content){
        reset();
//        foreground.getChildren().clear();
//        foreground.getChildren().add(foreContent);
//        foreContent.getChildren().clear();
//        foreContent.getChildren().add(customDialog);

        customDialog.setPrefWidth(minDrawerSize);

        customDialog.getChildren().setAll(content);

//        if(!customDialog.getChildren().contains(content)) {
//            customDialog.getChildren().clear();
//            customDialog.getChildren().add(content);
//        }

        organizeInLeft();

        open.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(customDialog.translateXProperty(), minDrawerSize * -1)),
                new KeyFrame(Duration.millis(velocity), new KeyValue(customDialog.translateXProperty(), 0))
        );

        close.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(customDialog.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(velocity), new KeyValue(customDialog.translateXProperty(), minDrawerSize * -1) )
        );

        foreground.toFront();
        open.play();

        foreContent.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof AnchorPane){
                if(((AnchorPane) event.getTarget()).getId().equals("fore-content")) {
                    close.play();
                }
            }
        });

        close.setOnFinished(event -> {
            foreground.toBack();
//            App.decorator.showCustomControls();
//            App.decorator.unblockControls();
        });
//
//        App.decorator.unblockControls();
//        App.decorator.hideCustomControls();

    }

    public enum AlertType {
        DONE, INFO, WARNING, ERROR
    }

    public void createDialog(String title, String message, Button... actions){
        reset();
        VBox body = new VBox();
        Label _title = new Label(title);
        _title.setWrapText(true);
        Text _message = new Text(message);
        TextFlow textFlow = new TextFlow(_message);
        HBox _actions = new HBox(actions);

        _title.getStyleClass().add("h3");
        _message.getStyleClass().add("h4");

        body.getChildren().setAll(_title, textFlow, _actions);

        body.setSpacing(5D);
        body.setPadding(new Insets(20D, 5, 20,20));

        VBox.setVgrow(textFlow, Priority.ALWAYS);
        VBox.setMargin(textFlow, new Insets(10, 0, 0, 10));
        _actions.setSpacing(5D);
        _actions.setAlignment(Pos.CENTER_RIGHT);

        BounceOut bounceOut = new BounceOut(customDialog);
        bounceOut.getTimeline().setOnFinished(event1 -> foreground.toBack());

        _actions.getChildren().stream().map(e -> (Button) e).forEach(c -> {
            c.setPrefWidth(100);
            c.addEventFilter(ActionEvent.ACTION, event -> bounceOut.play());
        });

        foreground.setOnMouseClicked(null);

        customDialog.getChildren().setAll(body);
        customDialog.setPrefSize(400, 200);

        for(Button action : actions){
            action.getStyleClass().add("btn-flat");
//            action.setStyle("-fx-text-fill : -text-color;");
        }

        organizeInCenter();

        BounceIn bounceIn = new BounceIn(customDialog);
        foreground.toFront();
        bounceIn.play();

    }


    public void createAlert(AlertType alertType, String title, String message, Button... actions) {
        reset();
        VBox content = new VBox();
        content.setPrefSize(500,300);
//        content.setMaxWidth(200);
        content.setAlignment(Pos.CENTER);

        content.getChildren().setAll(
                createAlertHeader(alertType),
                createContent(title, message),
                createActions(alertType, actions)
        );

        customDialog.setStyle("-fx-background-color : -foreground-color; -fx-background-radius : 10 10 10 10;");

        customDialog.setPrefSize(content.getPrefWidth(), content.getPrefHeight());
//        customDialog.setMinSize(content.getPrefWidth(), content.getPrefHeight());
//        customDialog.setMaxSize(content.getPrefWidth(), content.getPrefHeight());

        customDialog.getChildren().setAll(content);

        organizeInCenter();

        BounceIn bounceIn = new BounceIn(customDialog);
        foreground.toFront();
        bounceIn.play();

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

        Label text = new Label();
        text.setWrapText(true);
        text.setText(message);
        text.setAlignment(Pos.CENTER);

        container.getChildren().addAll(lblTitle, text);

        return container;
    }

    private HBox createActions(AlertType alertType, Button... actions){
        HBox _actions = new HBox();
        _actions.setMinSize(50, 73);
        _actions.setAlignment(Pos.CENTER);
        VBox.setMargin(_actions, new Insets(10, 10, 10, 10));
        _actions.setSpacing(5D);

        _actions.getChildren().setAll(actions);


        List result = _actions.getChildren().stream()                // convert list to stream
                .filter(line -> line instanceof Button)     // we dont like mkyong
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        result.forEach(
                c -> {

                    Button b = (Button) c;
                    if(b.getId() != null) {
                        if(b.getId().equals("defaultButton")) {

                                // reset default button
                                b.setDefaultButton(false);
                                b.setDefaultButton(true);
                                b.requestFocus();

                            b.requestLayout();
                            b.requestFocus();
                        }
                    }
                }
        );                //output : spring, node

        _actions.getChildren().forEach( e -> e.addEventFilter(ActionEvent.ACTION, event -> {
            BounceOut bounceOut = new BounceOut(customDialog);

            bounceOut.getTimeline().setOnFinished(ev -> {
                foreground.toBack();
////                App.decorator.unblockControls();
            });
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

        _actions.getChildren().stream().filter(e -> e instanceof Button).forEach(e -> ((Button) e).setPrefWidth(60));
        return _actions;
    }


    public void createPopup(StackPane content, boolean autoClose){
        createPopup(content);

        if(autoClose){
            foreContent.setOnMouseClicked(event -> {
                if (event.getTarget() instanceof AnchorPane){
                    if(((AnchorPane) event.getTarget()).getId().equals("fore-content")) {
                        close.play();
                    }
                }

                unlock();
            });
        }
    }

    public void createPopup(StackPane content){
        reset();
        customDialog.getChildren().setAll(content);
        customDialog.setPrefSize(content.getPrefWidth(), content.getPrefHeight());
        organizeInCenter();
        foreground.toFront();

        open.getKeyFrames().clear();
        close.getKeyFrames().clear();

//        App.INSTANCE.getDecorator().lockControls();

//        content.requestLayout();
        content.requestFocus();
        open.play();

    }

    public void createPopup(StackPane content, AnimationFX animationFX, Button defaultButton) {
        createPopup(content, animationFX);
        defaultButton.setDefaultButton(true);
    }

    public void createPopup(StackPane content, AnimationFX animationFX){
        reset();

        customDialog.getChildren().clear();
        customDialog.getChildren().add(content);

        customDialog.setPrefSize(content.getPrefWidth(), content.getPrefHeight());
        organizeInCenter();

        animationFX.setNode(content);

        if(animationFX instanceof Pulse) {
            animationFX.setSpeed(1.8);
        } else animationFX.setSpeed(1.2);

        foreground.toFront();

        content.requestFocus();
        content.requestLayout();

        if(content.lookup("#search") != null) {
            TextField field = (TextField) content.lookup("#search");
            field.requestFocus();
        }

        animationFX.play();

        foreground.requestFocus();
        foreground.requestLayout();


        if(content.lookup("#defaultButton") != null) {

            Button btn =  ((Button) content.lookup("#defaultButton"));
            btn.requestFocus();
            btn.requestLayout();
            // reset default button
            btn.setDefaultButton(false);
            btn.setDefaultButton(true);
            btn.requestFocus();
        }

    }

    private void organizeInCenter(){

        double width = App.INSTANCE.getDecorator().getWidth();
        double height = App.INSTANCE.getDecorator().getHeight();

//        if(width < GridFX.Type.XS.getValue()){
////            customDialog.setMaxWidth(250);
//            customDialog.setPrefWidth(250);
////            customDialog.setMinWidth(250);
//        }

        double x = ( width / 2) - ( customDialog.getPrefWidth() / 2);
        double y = ( height / 2 ) - (customDialog.getPrefHeight() / 2);

        AnchorPane.clearConstraints(customDialog);

        customDialog.setTranslateX(0D);
        customDialog.setTranslateY(0D);

        AnchorPane.setTopAnchor(customDialog, y);
        AnchorPane.setLeftAnchor(customDialog, x);
    }

    private void organizeInRight() {
        AnchorPane.clearConstraints(customDialog);
        AnchorPane.setTopAnchor(customDialog, 0D);
        AnchorPane.setRightAnchor(customDialog, 0D);
        AnchorPane.setBottomAnchor(customDialog, 0D);

    }

    private void organizeInLeft(){
        AnchorPane.clearConstraints(customDialog);
        AnchorPane.setTopAnchor(customDialog, 0D);
        AnchorPane.setLeftAnchor(customDialog, 0D);
        AnchorPane.setBottomAnchor(customDialog, 0D);
    }

    private void defineContents(){
        foreground = (StackPane)  App.INSTANCE.getDecorator().lookup("#foreground");
        foreContent = (AnchorPane) foreground.lookup("#fore-content");
        customDialog = (StackPane) foreground.lookup("#custom-dialog");
    }

    public void closePopup() {

        close.setOnFinished(event -> {
            foreground.toBack();
//            App.decorator.showCustomControls();
//            App.decorator.unblockControls();
        });

        unlock();
        close.play();

    }

    public void closePopup(AnimationFX animationFX){


        animationFX.setNode(customDialog.getChildren().get(0));

        animationFX.play();
        animationFX.getTimeline().setOnFinished(event -> foreground.toBack());

        unlock();
        close.play();

    }

    public void closePopup(StackPane node, AnimationFX animationFX){
//        App.decorator.unblockControls();
        animationFX.setNode(node);
        animationFX.play();
        animationFX.getTimeline().setOnFinished(event -> foreground.toBack());
//        close.play();

    }

    public void updateContent(StackPane content) {

        customDialog.getChildren().clear();
        customDialog.getChildren().add(content);

        customDialog.setPrefSize(content.getPrefWidth(), content.getPrefHeight());
        organizeInCenter();
    }

    private void unlock(){
         App.INSTANCE.getDecorator().unLockControls();
    }

    private void lock(){
        App.INSTANCE.getDecorator().lockControls();
    }

    private void requestLayout(){
//        System.out.println("PopupCreator.requestLayout");
//        StackPane root = (StackPane) ((GNDecorator) App.INSTANCE.getProperties().get("app.decorator")).lookup("#content");

    }

    /**
     * Some animations change the custom dialog format.
     */
    private void reset(){

        foreground.setOnMouseClicked(null);

        customDialog.setTranslateX(0D);
        customDialog.setTranslateY(0D);
        customDialog.setScaleX(1);
        customDialog.setScaleY(1);

        customDialog.setVisible(true);
        customDialog.setOpacity(1);
        customDialog.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        customDialog.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        customDialog.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        customDialog.setStyle("-fx-background-color : -foreground-color; -fx-background-radius :  0;");

    }
}
