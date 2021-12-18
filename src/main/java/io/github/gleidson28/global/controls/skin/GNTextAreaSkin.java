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
package io.github.gleidson28.global.controls.skin;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import io.github.gleidson28.global.controls.control.GNTextArea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class GNTextAreaSkin extends TextAreaSkin {

    private GNTextArea textArea = null;

    private final Label prompt = new Label();
    private final Timeline animation = new Timeline();
    private final Rectangle border = new Rectangle();
    private final Button leadButton = new Button();

    private boolean up = false;

    private static final PseudoClass FLOAT_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("float");

    public GNTextAreaSkin(GNTextArea textArea) {
        super(textArea);

        this.textArea = textArea;

        config();

        if (textArea.getText() != null) {
            if (!textArea.getText().isEmpty()) {
                Platform.runLater(this::upPrompt);
            }
        }

        if (textArea.isFloatPrompt()) {
            if (textArea.getText() != null) {
                if (textArea.getText().isEmpty()) {
                    addFloating();
                }
            }
        } else removeFloating();

        hidePrompt();

        registerChangeListener(textArea.floatPromptProperty(), "FLOAT_PROMPT");
        registerChangeListener(textArea.fieldTypeProperty(), "FIELD_TYPE");

        textArea.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            textArea.requestLayout();
            caretPath.setVisible(true);

        });

//        Text group = (Text) region.getChildrenUnmodifiable().get(0);
//        System.out.println(group);

    }


    @Override
    protected void handleControlPropertyChanged(String propertyReference) {
        super.handleControlPropertyChanged(propertyReference);


        if ("FLOAT_PROMPT".equals(propertyReference)) {
            System.out.println("textArea = " + textArea.isFloatPrompt());
            if (textArea.isFloatPrompt()) {
                addFloating();
            }  else {
                removeFloating();
            }
        }
    }

    ScrollPane scrollPane;
    Region region;

    private void config() {

        prompt.setText(textArea.getPromptText());
        prompt.fontProperty().bind(textArea.fontProperty());
        prompt.getStyleClass().add("prompt-text");

        getChildren().add(prompt);
        prompt.setPadding(new Insets(0,2,0,2));
        prompt.setText(prompt.getText());

        prompt.setMouseTransparent(true);

        getChildren().add(border);

        scrollPane = (ScrollPane) textArea.getChildrenUnmodifiable().get(0);
        region = (Region) scrollPane.getContent();

        border.setArcWidth(5);
        border.setArcHeight(5);

        border.widthProperty().bind(getSkinnable().widthProperty());
        border.heightProperty().bind(getSkinnable().heightProperty().subtract(5));

        scrollPane.setPadding(new Insets(15,0,0,5));

        border.getStyleClass().add("border");

        border.setStrokeType(StrokeType.OUTSIDE);
        border.setStrokeLineCap(StrokeLineCap.ROUND);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        border.setSmooth(false);
        border.setStrokeMiterLimit(10);

        border.setMouseTransparent(true);
        border.setFocusTraversable(false);
        border.toBack();

//        prompt.toFront();

        leadButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        
        leadButton.getStyleClass().addAll("btn-flat", "flat");

        leadButton.setPadding(new Insets(0,3,0,3));

        textArea.focusedProperty().addListener(focusedListener);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.isEmpty()) {
                    if (textArea.isFloatPrompt()) {
                        upPrompt();
                    } else downPrompt();
                }
            } else downPrompt();
        });

//        scrollPane.setStyle("-fx-background-color : -medium-gray;");

    }

    ChangeListener<Boolean> focusedListener = (observable, oldValue, newValue) -> {

        caretPath.toFront();

        forwardBiasProperty().set(true);

        if (newValue) { // focused
            if (!up) {
                upPrompt();
            }
        } else { // no focus

            if (textArea.getText() != null) {
                if (textArea.getText().isEmpty()) {
                    downPrompt();
                }
            }
        }
    };

    private void addFloating() {
        hidePrompt();

        if (!getChildren().contains(prompt)) getChildren().add(prompt);

        if (textArea.getText() == null) return;
        if (textArea.getText().isEmpty()) return;

        upPrompt();
//        textField.setFloatPrompt(true);
    }

    private void removeFloating() {
        hidePrompt();

//        downPrompt();
        getChildren().remove(prompt);
//        textField.setFloatPrompt(false);

    }

    private void hidePrompt( ) {
        if (textArea.isFloatPrompt())
            textArea.setStyle("-fx-prompt-text-fill : transparent;");
        else {
            textArea.setStyle("-fx-prompt-text-fill : -prompt-fill;");
        }
    }

    public void upPrompt() {

        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (up) return;

        double translateY = (prompt.getFont().getSize() + scrollPane.getInsets().getTop()) -2;

        up = true;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), 0, Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), -translateY, Interpolator.EASE_BOTH));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 1));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 0.9));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(), prompt.getTranslateX()));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(),
                        (-leadButton.getWidth()) -10));

        animation.getKeyFrames().setAll(
                yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd, xInitLead, xEndLead);

        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, true);

        animation.play();

    }

    private void downPrompt() {

        if (animation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (!up) return;

        up = false;

        KeyFrame yIni = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateYProperty(), prompt.getTranslateY(), Interpolator.EASE_BOTH));

        KeyFrame yEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateYProperty(), 0));

        KeyFrame xsInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleXProperty(), 0.9));

        KeyFrame xsEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleXProperty(), 1));

        KeyFrame ysInit = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.scaleYProperty(), 0.9));
        KeyFrame ysEnd = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.scaleYProperty(), 1));

        KeyFrame xInitLead = new KeyFrame(Duration.ZERO,
                new KeyValue(prompt.translateXProperty(),  prompt.getTranslateX()));

        KeyFrame xEndLead = new KeyFrame(Duration.millis(50),
                new KeyValue(prompt.translateXProperty(), -5));


        animation.getKeyFrames().setAll(
                yIni, yEnd, xsInit, xsEnd, ysInit, ysEnd, xInitLead, xEndLead);

        pseudoClassStateChanged(FLOAT_PSEUDO_CLASS, false);

        animation.play();
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 50;
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 100;
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);


        layoutInArea(prompt,
                x + (region.getInsets().getLeft() + scrollPane.getInsets().getLeft() +3) ,
                y + scrollPane.getInsets().getTop() +3,
                getSkinnable().getWidth(),
                prompt.getHeight(),
                0,
                HPos.LEFT,
                VPos.TOP);

        layoutInArea(border,x - getSkinnable().getInsets().getLeft(), y,
                w ,
               h,
                0,
                HPos.LEFT, VPos.CENTER);

//        textArea.requestLayout();

        updateTextFill();
        updateHighlightFill();
        updateHighlightTextFill();


    }
}
