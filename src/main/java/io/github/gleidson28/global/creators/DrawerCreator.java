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
import io.github.gleidson28.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

    private       boolean show      = false;

    public void createDrawerRight(Node content){
        createDrawerRight(content, 250d);
    }

    public void createDrawerRight(Node content, double size) {
        reset();

        show = true;

        customDialog.setPrefWidth(size);

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
                    show = false;
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

        show = true;

        customDialog.setPrefWidth(minDrawerSize);

        customDialog.getChildren().setAll(content);


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
                    show = false;
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

    private void organizeInCenter(){

        double width = App.INSTANCE.getDecorator().getWidth();
        double height = App.INSTANCE.getDecorator().getHeight();

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

    private void organizeInLeft() {
        AnchorPane.clearConstraints(customDialog);
        AnchorPane.setTopAnchor(customDialog, 0D);
        AnchorPane.setLeftAnchor(customDialog, 0D);
        AnchorPane.setBottomAnchor(customDialog, 0D);
    }

    private void organizeInBottom() {

        AnchorPane.clearConstraints(customDialog);
        AnchorPane.setLeftAnchor(customDialog, 0D);
        AnchorPane.setRightAnchor(customDialog, 0D);
        AnchorPane.setBottomAnchor(customDialog, 5D);

    }

    private void defineContents(){
        foreground = (StackPane)  App.INSTANCE.getDecorator().lookup("#foreground");
        foreContent = (AnchorPane) foreground.lookup("#fore-content");
        customDialog = (StackPane) foreground.lookup("#custom-dialog");
    }

    public void closePopup() {

        close.setOnFinished(event -> {
            foreground.toBack();
        });

        unlock();
        close.play();

    }

    public void closePopup(Button hamburger) {
        foreContent.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            hamburger.setVisible(true);
            hamburger.toBack();

        });
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

    public boolean isShow() {
        return show;
    }

    public void createTrayNotification(String title, String msg) {

        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);

        HBox rootNode = new HBox();
        StackPane root = new StackPane(rootNode);
        rootNode.setPrefSize(420,90);

        root.setStyle("-fx-border-width : 1 1 1 10; -fx-border-color : -success;");

        stage.setScene(new Scene(root));

        stage.getScene().getStylesheets().addAll(
                DrawerCreator.class.getResource("/theme/css/typographic.css")
                        .toExternalForm(),
                DrawerCreator.class.getResource("/theme/css/fonts.css")
                        .toExternalForm(),
                DrawerCreator.class.getResource("/theme/css/material-color.css")
                        .toExternalForm()
        );

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double x = screenBounds.getMinX() + screenBounds.getWidth() - rootNode.getPrefWidth() - 2.0D;
        double y = screenBounds.getMinY() + screenBounds.getHeight() - rootNode.getPrefHeight() - 2.0D;

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().addAll("h3");
        Label lblMessage = new Label(msg);
        lblMessage.getStyleClass().addAll("h4");

        Label lblClose = new Label("Close");

        StackPane imageIcon = new StackPane();
        imageIcon.setStyle("-fx-background-color : -success; -fx-shape : 'M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z';");
        imageIcon.setPrefSize(65, 90);

        GridPane.setMargin(imageIcon, new Insets(20));

        GridPane grid = new GridPane();
//        grid.setGridLinesVisible(true);
        grid.setHgap(20);

        rootNode.getChildren().addAll(grid);

        grid.add(imageIcon, 0, 0, 1, 2);
        grid.add(lblTitle, 1, 0);

        GridPane.setConstraints(lblTitle, 1, 0, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        grid.add(lblMessage, 1, 1);

        GridPane.setConstraints(lblMessage, 1, 1, 1, 1, HPos.LEFT, VPos.TOP, Priority.ALWAYS, Priority.ALWAYS);

        HBox.setHgrow(grid, Priority.ALWAYS);

        stage.setAlwaysOnTop(true);

        stage.setX(x);
        stage.setY(y);

        stage.show();
    }



}
