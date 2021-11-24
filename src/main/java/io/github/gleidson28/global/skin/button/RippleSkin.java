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
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/12/2018
 */
public class RippleSkin extends ButtonSkin {

    private Paint firstColor;

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
            return RippleSkin.this;
        }

        @Override public String getName() {
            return "animated";
        }
    };

    private StyleableObjectProperty<Paint> rippleFill;
    private StyleableObjectProperty<Number> rippleOpacity;

    public RippleSkin(Button control) {
        super(control);

        control.getStyleClass().add("ripple-button");

        Timeline timeEntered = new Timeline();

        pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, animated.get());

        Rectangle clip = new Rectangle();
        clip.setArcWidth(0);
        clip.setArcHeight(0);
        getSkinnable().setClip(clip);

        clip.widthProperty().bind(getSkinnable().widthProperty());
        clip.heightProperty().bind(getSkinnable().heightProperty());

        getSkinnable().setOnMouseClicked(event -> {

            if (timeEntered.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, true);
            Circle circle = new Circle();
            circle.setRadius(0);

            circle.setFill(rippleFill.get());

            circle.setLayoutX(event.getX());
            circle.setLayoutY(event.getY());

            circle.setOpacity(0.5);
            circle.setMouseTransparent(true);
            getChildren().add(circle);

            timeEntered.getKeyFrames().setAll(

                    new KeyFrame(Duration.ZERO, new KeyValue(circle.radiusProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(circle.radiusProperty(), control.getWidth()))
            );



            timeEntered.play();

            timeEntered.setOnFinished( e -> {
                pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, false);
                getChildren().removeAll(circle);
            });

        });

        this.rippleFill = new SimpleStyleableObjectProperty<Paint>(RIPPLE_FILL, this, "rippleFill");
        this.rippleOpacity = new SimpleStyleableObjectProperty<Number>(RIPPLE_OPACITY, this, "rippleOpacity");

    }

    private static final CssMetaData<Button, Paint> RIPPLE_FILL =
            new CssMetaData<Button, Paint>("-gn-ripple-fill", PaintConverter.getInstance(), Color.WHITE) {
                @Override
                public boolean isSettable(Button styleable) {
//                     ( ( (RippleSkin) getClass()) styleable.getSkin()). rippleFill;
                    if (styleable.getSkin() instanceof RippleSkin)
                    return ( (RippleSkin) styleable.getSkin()).rippleFill == null ||
                            !( (RippleSkin) styleable.getSkin()).rippleFill.isBound();
                    else return false;
                }

                @Override
                public StyleableProperty<Paint> getStyleableProperty(Button styleable) {
                    if (styleable.getSkin() instanceof RippleSkin) {
                        return ( (RippleSkin) styleable.getSkin() ).rippleFillProperty();
                    } else return null;
                }
            };

    private static final CssMetaData<Button, Number> RIPPLE_OPACITY =
            new CssMetaData<Button, Number>("-gn-ripple-opacity", StyleConverter.getSizeConverter(),0.5D) {
                @Override
                public boolean isSettable(Button styleable) {
                    return ( (RippleSkin) styleable.getSkin()).rippleOpacity == null ||
                            !( (RippleSkin) styleable.getSkin()).rippleOpacity.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(Button styleable) {
                    if (styleable.getSkin() instanceof RippleSkin) {
                        return ( (RippleSkin) styleable.getSkin() ).rippleOpacityProperty();
                    } else return null;
                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static  {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(SkinBase.getClassCssMetaData());
        styleables.add(RIPPLE_FILL);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public Paint getRippleFill() {
        return rippleFill.get();
    }

    public StyleableObjectProperty<Paint> rippleFillProperty() {
        return rippleFill;
    }

    public void setRippleFill(Paint rippleFill) {
        this.rippleFill.set(rippleFill);
    }

    public Number getRippleOpacity() {
        return rippleOpacity.get();
    }

    public StyleableObjectProperty<Number> rippleOpacityProperty() {
        return rippleOpacity;
    }

    public void setRippleOpacity(Number rippleOpacity) {
        this.rippleOpacity.set(rippleOpacity);
    }
}