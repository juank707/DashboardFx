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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/11/2021
 */
public enum DialogCreator {

    INSTANCE;

    private final GNDecorator decorator;
    private StackPane foreground;
    private AnchorPane  foreContent;
    private StackPane   customDialog;

    DialogCreator() {
        decorator = App.INSTANCE.getDecorator();
        defineContents();
    }

    private void defineContents(){
        foreground = (StackPane)  App.INSTANCE.getDecorator().lookup("#foreground");
        foreContent = (AnchorPane) foreground.lookup("#fore-content");
        customDialog = (StackPane) foreground.lookup("#custom-dialog");
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


        for(Button action : actions){
            action.getStyleClass().add("btn-flat");
//            action.setStyle("-fx-text-fill : -text-color;");
        }


        if(decorator.getWidth() < 600) {
            customDialog.setPrefWidth(decorator.getWidth() - 20);
        } else {
            customDialog.setPrefSize(400, 200);
        }

        organizeInCenter();


        BounceIn bounceIn = new BounceIn(customDialog);
        foreground.toFront();
        bounceIn.play();

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
