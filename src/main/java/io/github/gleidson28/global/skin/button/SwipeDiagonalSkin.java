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
package io.github.gleidson28.global.skin.button;

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/12/2018
 */
public class SwipeDiagonalSkin extends ButtonSkin {

    private Paint firstColor;

    private final StackPane rect = new StackPane();

    private final ObjectProperty<Duration> velocity =
            new SimpleObjectProperty<>(this, "velocity",
            Duration.millis(200)
    );

    private static final PseudoClass ANIMATED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("animated");

    private final BooleanProperty animated = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return SwipeDiagonalSkin.this;
        }

        @Override public String getName() {
            return "animated";
        }
    };

    private StyleableObjectProperty<Paint> transitionColor ;

    public SwipeDiagonalSkin(Button control) {
        super(control);


        SVGPath shape = new SVGPath();
        shape.setContent("M 250 200 L 250 300 L 500 300 L 450 200 L 250 200 ");
        rect.setShape(shape);

        rect.setPrefWidth(Region.USE_COMPUTED_SIZE);
        rect.setMaxWidth(Region.USE_COMPUTED_SIZE);

        rect.setPrefHeight(Region.USE_COMPUTED_SIZE);
        rect.setMaxHeight(Region.USE_COMPUTED_SIZE);

//        rect.setTranslateX(0);

//        rect.setPrefWidth(0);
//        rect.setMaxWidth(0);

        Rectangle clip = new Rectangle();
        clip.setArcWidth(0);
        clip.setArcHeight(0);
        getSkinnable().setClip(clip);

        clip.widthProperty().bind(getSkinnable().widthProperty());
        clip.heightProperty().bind(getSkinnable().heightProperty());

        getChildren().add(rect);

//        getChildren().add(title);
        Text title;
        if (getSkinnable().getChildrenUnmodifiable().get(1) instanceof Text)
            title = (Text) getSkinnable().getChildrenUnmodifiable().get(1);
        else title = (Text) getSkinnable().getChildrenUnmodifiable().get(0);
        rect.toBack();


        Timeline timeEntered = new Timeline();
        Timeline timeExited = new Timeline();

        firstColor = getSkinnable().getTextFill();

        getSkinnable().textFillProperty().addListener((observable, oldValue, newValue) -> {
            if(timeEntered.getStatus() == Animation.Status.STOPPED && timeExited.getStatus() == Animation.Status.STOPPED ) {
                firstColor = newValue;
            }
        });

//        rect.borderProperty().bind(getSkinnable().borderProperty());

        pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, animated.get());

        getSkinnable().setOnMouseEntered(event -> {
            timeEntered.getKeyFrames().clear();

            System.out.println("time entered");

            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, true);

            timeEntered.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(rect.translateXProperty(), rect.getTranslateX())),
//                    new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill())),

                    new KeyFrame(velocity.get(), new KeyValue(rect.translateXProperty(), rect.getWidth()))
//                    new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), ((Button) getSkinnable()).getTransitionText()))
            );

            if (timeExited.getStatus() == Animation.Status.RUNNING){
                timeExited.stop();
            }

            timeEntered.play();

        });

        getSkinnable().setOnMouseExited(event -> {
            timeExited.getKeyFrames().clear();
            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, false);
            timeExited.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(rect.translateXProperty(), rect.getTranslateX())),

                    new KeyFrame(Duration.ZERO, new KeyValue(getSkinnable().textFillProperty(), getSkinnable().getTextFill())),

                    new KeyFrame(velocity.get(),
                            new KeyValue(rect.translateXProperty(), 0D)),
                    new KeyFrame(velocity.get(), new KeyValue(getSkinnable().textFillProperty(), firstColor))

            );

            if (timeEntered.getStatus() == Animation.Status.RUNNING){
                timeEntered.stop();
            }

            timeExited.play();
        });

        this.transitionColor = new SimpleStyleableObjectProperty<Paint>(TRANSITION_COLOR, this, "transitionColor");

        transitionColorProperty().addListener((observable, oldValue, newValue) -> {
            rect.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
        layoutInArea(rect, (contentWidth * -1) -(contentWidth / 4), contentY, contentWidth + (contentWidth / 4) , contentHeight, 0,
                HPos.LEFT,  VPos.CENTER);
//        layoutInArea(
//                rect,
//                contentX - (snappedLeftInset() + rect.getBorder().getInsets().getRight()),
//                contentY - snappedTopInset() ,
//                contentWidth + (snappedRightInset() + snappedLeftInset()),
//                contentHeight + (snappedBottomInset() + snappedTopInset()) + bottomLabelPadding(),
//                0,
//                HPos.LEFT,  VPos.CENTER);

    }

    private static final CssMetaData<Button, Paint> TRANSITION_COLOR =
            new CssMetaData<Button, Paint>("-gn-transition-color", PaintConverter.getInstance(), Color.RED) {
                @Override
                public boolean isSettable(Button styleable) {
                    return ( (SwipeDiagonalSkin) styleable.getSkin()).transitionColor == null ||
                            !( (SwipeDiagonalSkin) styleable.getSkin()).transitionColor.isBound();
                }

                @Override
                public StyleableProperty<Paint> getStyleableProperty(Button styleable) {
                    if (styleable.getSkin() instanceof SwipeDiagonalSkin) {
                        return ( (SwipeDiagonalSkin) styleable.getSkin() ).transitionColorProperty();
                    } else return null;
                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static  {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(SkinBase.getClassCssMetaData());
        styleables.add(TRANSITION_COLOR);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

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
}