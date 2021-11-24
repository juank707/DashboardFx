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

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import io.github.gleidson28.global.skin.icon.Icon;
import io.github.gleidson28.global.skin.icon.Icons;
import io.github.gleidson28.global.skin.textField.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/11/2018
 * Version 1.0
 */
public class GNTextFieldSkin extends TextFieldSkin {

    private final Label lbl = new Label("Error label");

    private Icon leadIcon = new Icon();
    private Icon actionIcon = new Icon();
    private Button  actionButton = new Button();
    private Button  leadButton = new Button();

    private final FieldFooter fieldFooter;

    private final Label prompt = new Label("Prompt Text");

    private final Pane pane;

    private final Timeline animation = new Timeline();

    private StyleableObjectProperty<ActionButtonType> actionButtonType;
    private StyleableObjectProperty<LeadIconType> leadIconType;

    private final StyleableObjectProperty<Boolean> leadIconVisible;
    private final StyleableObjectProperty<Boolean> actionButtonVisible;
    private final StyleableObjectProperty<Boolean> maxLengthVisible;
    private final StyleableObjectProperty<Number> maxLength;


    private IntegerProperty size = new SimpleIntegerProperty();

    private double _iconSize = 20;
    private double _iconSpacing = 5;

    private boolean up = false;

    public GNTextFieldSkin(TextField textField) {
        super(textField);

        prompt.setText(textField.getPromptText());

        prompt.fontProperty().bind(textField.fontProperty());

        prompt.getStyleClass().add("prompt");

        actionIcon.setContent("M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z");

        actionIcon.setId("actionIcon");

        actionIcon.getStyleClass().add("icon");

        actionButton.getStyleClass().add("btn-flat");
        leadButton.getStyleClass().add("btn-flat");
//
        int _btnSize = 25;
        actionButton.setMaxSize(_btnSize,_btnSize);
        actionButton.setPrefSize(_btnSize,_btnSize);
        actionButton.setMinSize(_btnSize,_btnSize);
        leadButton.setMaxSize(_btnSize,_btnSize);
        leadButton.setPrefSize(_btnSize,_btnSize);
        leadButton.setMinSize(_btnSize,_btnSize);

        textField.setStyle("-fx-border-color : black;");

        actionButton.setGraphic(actionIcon);
        leadButton.setGraphic(leadIcon);
        actionButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        leadButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        actionButton.getStyleClass().add("action-button");
        leadButton.getStyleClass().add("lead-button");

        pane = (Pane) getChildren().get(0);

        textField.setStyle("-fx-prompt-text-fill : transparent;");

        pane.getStyleClass().add("content");
        getChildren().add(prompt);
        prompt.setPadding(new Insets(2));

        actionButton.setMaxWidth(_iconSize);
        actionButton.setPrefWidth(_iconSize);

        actionButton.setVisible(true);

        getSkinnable().focusedProperty().addListener((observable, oldValue, newValue) -> {

            actionButton.setVisible(newValue);

            if (animation.getStatus() == Animation.Status.RUNNING) {
                return;
            }

            if (newValue) {

                if (up) return;

                if(getSkinnable().getText() == null) {
                    upPrompt();
                    return;
                }

                if (!getSkinnable().getText().isEmpty()) {
                    if (up) return;
                }
                upPrompt();

            } else {
                if (animation.getStatus() == Animation.Status.RUNNING) {
                    return;
                }

                if (getSkinnable().getText() == null) {
                    downPrompt();
                    return;
                }

                if (getSkinnable().getText().isEmpty()) {
                    downPrompt();
                }
            }
        });

        Platform.runLater(() -> {
            if (getSkinnable().getText() != null) {
                if (!getSkinnable().getText().isEmpty())
                    upPrompt();
            }
        });

        this.leadIconVisible = new SimpleStyleableObjectProperty<>(
                LEAD_ICON_VISIBLE, false);
        this.actionButtonVisible = new SimpleStyleableObjectProperty<>(
                ACTION_BUTTON_VISIBLE, false);
        this.actionButtonType = new SimpleStyleableObjectProperty<>(
                ACTION_BUTTON_TYPE, ActionButtonType.CLEAR);
        this.maxLength = new SimpleStyleableObjectProperty<>(
                MAX_LENGTH, Double.POSITIVE_INFINITY);
        this.leadIconType = new SimpleStyleableObjectProperty<>(
                LEAD_ICON_TYPE, LeadIconType.FAVORITE);
        this.maxLengthVisible = new SimpleStyleableObjectProperty<>(
                MAX_LENGTH_VISIBLE, false);

        createClearAction();

        this.leadIconVisible.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                getChildren().add(leadButton);
            } else {
                getChildren().remove(leadButton);
            }
        });

        this.actionButtonVisible.addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                if(!getChildren().contains(actionButton))
                    getChildren().add(actionButton);
            } else {
                getChildren().remove(actionButton);
            }
        });

        this.actionButtonType.addListener(new ChangeListener<ActionButtonType>() {
            @Override
            public void changed(ObservableValue<? extends ActionButtonType> observable, ActionButtonType oldValue, ActionButtonType newValue) {
                switch (newValue) {
                    case CLEAR :
                        createClearAction();
                        break;
                }
            }
        });

        leadIcon.getStyleClass().add("icon");
        leadIcon.getStyleClass().add("lead-icon");

        this.leadIconType.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Icons ic : Icons.values()) {
                    if(newValue.toString().equals(ic.toString())) {
                        leadIcon.setContent(ic);
                    }
                }
                if (newValue == LeadIconType.MONETARY) {
                    leadIcon.setContent(Icons.MONETARY);
                    leadIcon.getStyleClass().add("monetary-icon");
                }
            }
        });


        fieldFooter = new FieldFooter();
        getChildren().add(fieldFooter);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !getChildren().contains(actionButton))  {
                getChildren().add(actionButton);
            };
        });

        getSkinnable().lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText().length() > getMaxLength().intValue()) {
                textField.setText(textField.getText().substring(0, getMaxLength().intValue()));
            } else  updateFooterSize(newValue.intValue());
        });

        if(getSkinnable().getText() != null) {
            updateFooterSize(textField.getLength());
        }

        maxLength.addListener((observable, oldValue, newValue) -> {
            updateFooterSize(textField.getLength());
        });

        fieldFooter.setVisibleCount(false);

        maxLengthVisible.addListener((observable, oldValue, newValue) ->
                fieldFooter.setVisibleCount(newValue));

        actionButton.setFocusTraversable(false);
    }

    private void updateFooterSize(int length) {
        String max = String.valueOf(getMaxLength().intValue());
        String size = String.valueOf(length);
        if(max.equals("0") || getMaxLength().equals(Double.POSITIVE_INFINITY)) max = "..";
        fieldFooter.getCount().setText(size + "/" + max);
    }

    private void createClearAction() {
        actionButton.setOnMouseClicked(event ->   {
            getSkinnable().clear();
            getChildren().remove(actionButton);
        });
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        TextField textField = getSkinnable();

        double characterWidth = fontMetrics.get().computeStringWidth("W");

        int columnCount = textField.getPrefColumnCount();
        return  columnCount * characterWidth + leftInset + rightInset + _iconSize;
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        if(getChildren().contains(actionButton) && getChildren().contains(leadButton)) {

            layoutInArea(leadButton,
                    x , y,
                    leadButton.getWidth(),
                    h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(actionButton, x + (w - actionButton.getWidth()), y, actionButton.getWidth(), h,
                    0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane, x + leadButton.getWidth(), y,
                    w - (actionButton.getWidth() + leadButton.getWidth()), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt, x + leadButton.getWidth(), y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if(getChildren().contains(actionButton)) {

            layoutInArea(actionButton, x, y, w , h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane, x, y,
                    w - actionButton.getWidth(), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt, x, y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else if(getChildren().contains(leadButton)) {

            layoutInArea(leadButton,
                    x , y,
                    leadButton.getWidth() ,
                    h, 0, HPos.RIGHT, VPos.CENTER);

            layoutInArea(pane,
                    x + leadButton.getWidth(), y,
                    w -  leadButton.getWidth(), h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x + leadButton.getWidth(), y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);

        } else {
            layoutInArea(pane, x, y, w, h,
                    0, HPos.LEFT, VPos.CENTER);

            layoutInArea(prompt,
                    x, y,
                    getSkinnable().getWidth(), h, 0, HPos.LEFT, VPos.CENTER);
        }

        layoutInArea(fieldFooter,
                x, h + 8,
                getSkinnable().getWidth() - _iconSpacing,
                fieldFooter.getTextFlow().getHeight(), 0,
                HPos.LEFT, VPos.TOP);

        fieldFooter.setCursor(Cursor.DEFAULT);

        updateCaretOff();
    }

    private void downPrompt() {
        animation.getKeyFrames().clear();

        up = false;
        animation.getKeyFrames().setAll(

                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateYProperty(), prompt.getTranslateY())
                ),

                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateXProperty(), leadButton.getWidth() * -1
                        )
                ),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(prompt.translateXProperty(),
                                0)),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(
                                prompt.translateYProperty(),
                                0)
                )

        );
        animation.play();
    }

    private void upPrompt() {
        double newY = ((getSkinnable().getHeight()) / 2);

        up = true;

        animation.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateYProperty(),
                                0)),

                new KeyFrame(Duration.ZERO,
                        new KeyValue(
                                prompt.translateXProperty(), 0
                        )
                ),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(prompt.translateXProperty(),
                                leadButton.getWidth() * -1)),

                new KeyFrame(Duration.millis(200),
                        new KeyValue(prompt.translateYProperty(),
                                newY * -1 ))

        );

        animation.play();
    }
//
    private static final CssMetaData<TextField, Boolean> LEAD_ICON_VISIBLE =
            new CssMetaData<TextField, Boolean>(
                    "-gn-lead-icon-visible",
                    StyleConverter.getBooleanConverter(), false

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).leadIconVisible == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).leadIconVisible.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).leadIconVisibleProperty();
                    } else return null;

                }
            };

    private static final CssMetaData<TextField, Boolean> ACTION_BUTTON_VISIBLE =
            new CssMetaData<TextField, Boolean>(
                    "-gn-action-button-visible",
                    StyleConverter.getBooleanConverter(), false

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).actionButtonVisible == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).actionButtonVisible.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).actionButtonVisibleProperty();
                    } else return null;

                }
            };

    private static final CssMetaData<TextField, Boolean> MAX_LENGTH_VISIBLE =
            new CssMetaData<TextField, Boolean>(
                    "-gn-max-length-visible",
                    StyleConverter.getBooleanConverter(), false

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).maxLengthVisible == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).maxLengthVisible.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).maxLengthVisibleProperty();
                    } else return null;

                }
            };

    private static final CssMetaData<TextField, ActionButtonType> ACTION_BUTTON_TYPE =
            new CssMetaData<TextField, ActionButtonType>(
                    "-gn-action-button-type",
                    ActionButtonTypeConverter.getInstance(), ActionButtonType.CLEAR

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).actionButtonType == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).actionButtonType.isBound();
                }

                @Override
                public StyleableProperty<ActionButtonType> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).actionButtonTypeProperty();
                    } else return null;

                }
            };

    private static final CssMetaData<TextField, LeadIconType> LEAD_ICON_TYPE =
            new CssMetaData<TextField, LeadIconType>(
                    "-gn-icon-type",
                    LeadIconTypeConverter.getInstance(), LeadIconType.FAVORITE

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).leadIconType == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).leadIconType.isBound();
                }

                @Override
                public StyleableProperty<LeadIconType> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).leadIconTypeProperty();
                    } else return null;

                }
            };

    private static final CssMetaData<TextField, Number> MAX_LENGTH =
            new CssMetaData<TextField, Number>(
                    "-gn-max-length",
                    StyleConverter.getSizeConverter(), 0

            ) {
                @Override
                public boolean isSettable(TextField styleable) {
                    return ((GNTextFieldSkin) styleable.getSkin()).maxLength == null ||
                            !((GNTextFieldSkin) styleable.getSkin()).maxLength.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(TextField styleable) {
                    if (styleable.getSkin() instanceof GNTextFieldSkin) {
                        return ((GNTextFieldSkin) styleable.getSkin()).maxLengthProperty();
                    } else return null;

                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(TextFieldSkin.getClassCssMetaData());
        styleables.add(MAX_LENGTH_VISIBLE);
        styleables.add(ACTION_BUTTON_TYPE);
        styleables.add(MAX_LENGTH);
        styleables.add(LEAD_ICON_TYPE);
        styleables.add(ACTION_BUTTON_VISIBLE);
        styleables.add(LEAD_ICON_VISIBLE);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }


    public void setErrorVisible(Boolean errorVisible) {
        this.fieldFooter.setErrorVisible(errorVisible);
    }

    public void setErrorMessage(String message) {
        this.fieldFooter.setMessage(message);
    }

    public StyleableObjectProperty<Boolean> maxLengthVisibleProperty() {
        return maxLengthVisible;
    }

    public StyleableObjectProperty<Boolean> leadIconVisibleProperty() {
        return leadIconVisible;
    }

    public StyleableObjectProperty<Boolean> actionButtonVisibleProperty() {
        return actionButtonVisible;
    }

    public StyleableObjectProperty<ActionButtonType> actionButtonTypeProperty() {
        return actionButtonType;
    }

    public StyleableObjectProperty<LeadIconType> leadIconTypeProperty() {
        return leadIconType;
    }

    public StyleableObjectProperty<Number> maxLengthProperty() {
        return maxLength;
    }

    public Number getMaxLength() {
        return maxLength.get();
    }

    protected Button getActionButton() {
        return actionButton;
    }

    protected Icon getActionIcon() {
        return actionIcon;
    }

    protected ActionButtonType getActionButtonType() {
        return actionButtonType.get();
    }

    public void setActionButtonType(ActionButtonType actionButtonType) {
        this.actionButtonType.set(actionButtonType);
    }
}
