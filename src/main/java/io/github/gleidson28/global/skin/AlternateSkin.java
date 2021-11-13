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

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class AlternateSkin extends ButtonSkin {

    private final StackPane first  =  new StackPane();
    private final StackPane second = new StackPane();
    private final StackPane third = new StackPane();
    private final StackPane fourth = new StackPane();

    private Label title = new Label("Button");

    private ObjectProperty<Duration> velocity =
            new SimpleObjectProperty<>(this, "velocity", Duration.millis(200));

    private StyleableObjectProperty<Paint> transitionColor;

    private Paint firstColor;

    public AlternateSkin(Button button) {

        super(button);

        first.setShape(null);

        first.setPrefHeight(0);
        first.setMaxHeight(0);

        first.setPrefWidth(Region.USE_COMPUTED_SIZE);
        first.setMaxWidth(Region.USE_COMPUTED_SIZE);

        second.setPrefWidth(Region.USE_COMPUTED_SIZE);
        second.setMaxWidth(Region.USE_COMPUTED_SIZE);
        third.setPrefWidth(Region.USE_COMPUTED_SIZE);
        third.setMaxWidth(Region.USE_COMPUTED_SIZE);
        fourth.setPrefWidth(Region.USE_COMPUTED_SIZE);
        fourth.setMaxWidth(Region.USE_COMPUTED_SIZE);

        second.setMaxHeight(0);
        second.setPrefHeight(0);
        third.setMaxHeight(0);
        third.setPrefHeight(0);
        fourth.setMaxHeight(0);
        fourth.setPrefHeight(0);

//        getChildren().clear();

        getChildren().add(first);
        getChildren().add(second);
        getChildren().add(third);
        getChildren().add(fourth);
//        getChildren().add(title);


//        velocity.bind(
////                ( (AlternateSkin) getSkinnable().getSkin()).velocityProperty()
//                ( (AlternateSkin) getSkinnable().getSkin()).velocityProperty()
//        );

        title.textProperty().bind(getSkinnable().textProperty());
        title.fontProperty().bind(getSkinnable().fontProperty());
        title.textFillProperty().bind(getSkinnable().textFillProperty());
        title.underlineProperty().bind(getSkinnable().underlineProperty());
        title.textAlignmentProperty().bind(getSkinnable().textAlignmentProperty());
        title.contentDisplayProperty().bind(getSkinnable().contentDisplayProperty());
        title.ellipsisStringProperty().bind(getSkinnable().ellipsisStringProperty());
        title.backgroundProperty().bind(getSkinnable().backgroundProperty());
        title.alignmentProperty().bind(getSkinnable().alignmentProperty());
        title.textOverrunProperty().bind(getSkinnable().textOverrunProperty());


        Rectangle clip = new Rectangle();
        clip.setArcWidth(0);
        clip.setArcHeight(0);
        getSkinnable().setClip(clip);

        clip.widthProperty().bind(getSkinnable().widthProperty());
        clip.heightProperty().bind(getSkinnable().heightProperty());

        Timeline timeEntered = new Timeline();
        Timeline timeExited = new Timeline();

        firstColor = getSkinnable().getTextFill();

        first.setBackground(new Background(new BackgroundFill(
               Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY
        )));

        second.setBackground(new Background(new BackgroundFill(
//                ((Button)getSkinnable()).getTransitionColor(), CornerRadii.EMPTY, Insets.EMPTY
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY
        )));

        third.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY
//                ((Button)getSkinnable()).getTransitionColor(), CornerRadii.EMPTY, Insets.EMPTY
        )));

        fourth.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY
//                ((Button)getSkinnable()).getTransitionColor(), CornerRadii.EMPTY, Insets.EMPTY
        )));

        this.transitionColor = new SimpleStyleableObjectProperty<Paint>(TRANSITION_COLOR, this, "transitionColor", Color.web("33B5E5"));

        transitionColorProperty().addListener((observable, oldValue, newValue) -> {
            first.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
            second.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
            third.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
            fourth.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        title.textFillProperty().addListener((observable, oldValue, newValue) -> {
            if (timeEntered.getStatus() == Animation.Status.STOPPED && timeExited.getStatus() == Animation.Status.STOPPED) {
                firstColor = newValue;
            }
        });

        getSkinnable().setOnMouseEntered(event -> {
            timeEntered.getKeyFrames().clear();

            if(first == null) event.consume();

            timeEntered.getKeyFrames().addAll(

                    new KeyFrame(Duration.ZERO, new KeyValue(first.maxHeightProperty(), first.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(first.maxHeightProperty(), first.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(second.prefHeightProperty(), second.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(second.maxHeightProperty(), second.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(third.prefHeightProperty(), third.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(third.maxHeightProperty(), third.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(fourth.prefHeightProperty(), fourth.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(fourth.maxHeightProperty(), fourth.getHeight())),

                    new KeyFrame(velocity.get(), new KeyValue(first.prefHeightProperty(), getSkinnable().getHeight())),
                    new KeyFrame(velocity.get(), new KeyValue(first.maxHeightProperty(), getSkinnable().getHeight())),

                    new KeyFrame(velocity.get(), new KeyValue(second.prefHeightProperty(), getSkinnable().getHeight())),
                    new KeyFrame(velocity.get(), new KeyValue(second.maxHeightProperty(), getSkinnable().getHeight())),

                    new KeyFrame(velocity.get(), new KeyValue(third.prefHeightProperty(), getSkinnable().getHeight())),
                    new KeyFrame(velocity.get(), new KeyValue(third.maxHeightProperty(), getSkinnable().getHeight())),

                    new KeyFrame(velocity.get(), new KeyValue(fourth.prefHeightProperty(), getSkinnable().getHeight())),
                    new KeyFrame(velocity.get(), new KeyValue(fourth.maxHeightProperty(), getSkinnable().getHeight()))

//                    new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill()))
//                    new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), ( (AlternateSkin) getSkinnable().getSkin()).getTransitionText()))

            );

            if (timeExited.getStatus() == Animation.Status.RUNNING) {
                timeExited.stop();
            }

            timeEntered.play();

        });

        getSkinnable().setOnMouseExited(event -> {
            timeExited.getKeyFrames().clear();
            timeExited.getKeyFrames().addAll(

                    new KeyFrame(Duration.ZERO, new KeyValue(first.prefHeightProperty(), first.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(first.maxHeightProperty(), first.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(second.prefHeightProperty(), second.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(second.maxHeightProperty(), second.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(third.prefHeightProperty(), third.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(third.maxHeightProperty(), third.getHeight())),

                    new KeyFrame(Duration.ZERO, new KeyValue(fourth.prefHeightProperty(), fourth.getHeight())),
                    new KeyFrame(Duration.ZERO, new KeyValue(fourth.maxHeightProperty(), fourth.getHeight())),

                    new KeyFrame(velocity.getValue(), new KeyValue(first.prefHeightProperty(), 0D)),
                    new KeyFrame(velocity.get(), new KeyValue(first.maxHeightProperty(), 0D)),

                    new KeyFrame(velocity.get(), new KeyValue(second.prefHeightProperty(), 0D )),
                    new KeyFrame(velocity.get(), new KeyValue(second.maxHeightProperty(), 0D )),

                    new KeyFrame(velocity.get(), new KeyValue(third.prefHeightProperty(), 0D )),
                    new KeyFrame(velocity.get(), new KeyValue(third.maxHeightProperty(), 0D )),

                    new KeyFrame(velocity.get(), new KeyValue(fourth.prefHeightProperty(), 0D )),
                    new KeyFrame(velocity.get(), new KeyValue(fourth.maxHeightProperty(), 0D )),

                    new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill())),
                    new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), firstColor))

            );

            if (timeEntered.getStatus() == Animation.Status.RUNNING) {
                timeEntered.stop();
            }

            timeExited.play();
        });
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return title.minWidth(height);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return title.minHeight(width);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return title.prefWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return title.prefHeight(width) + topInset + bottomInset;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);

        layoutInArea(first, contentX, contentY, contentWidth / 4, contentHeight, 0,
                HPos.LEFT, VPos.TOP);

        layoutInArea(second, contentWidth / (getChildren().size() - 1), contentY, contentWidth / 4, contentHeight, 0,
                HPos.LEFT, VPos.BOTTOM);

        layoutInArea(third, (contentWidth / (getChildren().size() - 1)) * 2, contentY, contentWidth / 4, contentHeight, 0,
                HPos.LEFT, VPos.TOP);

        layoutInArea(fourth, (contentWidth / (getChildren().size() - 1)) * 3, contentY, contentWidth / 4, contentHeight, 0,
                HPos.LEFT, VPos.BOTTOM);


        layoutInArea(title, contentX, contentY, contentWidth, contentHeight, 0,
                title.getAlignment().getHpos(), title.getAlignment().getVpos());
    }

    /********** CSS Properties ****************/

    private static final CssMetaData<Button, Paint> TRANSITION_COLOR
            = new CssMetaData<Button, Paint>("-gn-transition-color", PaintConverter.getInstance(), Color.RED) {
                @Override
                public boolean isSettable(Button styleable) {
                    return ( (AlternateSkin) styleable.getSkin() ).transitionColor == null || !( (AlternateSkin) styleable.getSkin() ).transitionColor.isBound();
                }

                @Override
                public StyleableProperty<Paint> getStyleableProperty(Button styleable) {
                    if(styleable.getSkin() instanceof AlternateSkin) {
                        return  ( (AlternateSkin) styleable.getSkin() ).transitionColorProperty();
                    } else return null;
                }
            };

    /* Setup styleables for this Skin */
    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(SkinBase.getClassCssMetaData());
        styleables.add(TRANSITION_COLOR);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public Paint getTransitionColor() {
        return transitionColor.get();
    }

    public StyleableObjectProperty<Paint> transitionColorProperty() {
        return transitionColor;
    }

    public void setTransitionColor(Paint transitionColor) {
        this.transitionColor.set(transitionColor);
    }

    public Duration getVelocity() {
        return velocity.get();
    }

    public ObjectProperty<Duration> velocityProperty() {
        return velocity;
    }

    public void setVelocity(Duration velocity) {
        this.velocity.set(velocity);
    }


}
