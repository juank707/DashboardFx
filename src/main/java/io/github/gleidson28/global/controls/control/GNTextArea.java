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
package io.github.gleidson28.global.controls.control;

import com.sun.javafx.css.converters.BooleanConverter;
import io.github.gleidson28.global.controls.converters.FieldTypeConverter;
import io.github.gleidson28.global.controls.skin.GNTextAreaSkin;
import io.github.gleidson28.global.controls.types.FieldType;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/12/2021fasd
 */
@DefaultProperty("control")
public class GNTextArea extends TextArea {

    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    private final StyleableBooleanProperty floatPrompt
            = new StyleableBooleanProperty(false) {

        @Override
        public CssMetaData<GNTextArea, Boolean> getCssMetaData() {
            return StyleableProperties.FLOAT_PROMPT;
        }

        @Override
        public Object getBean () {
            return this;
        }

        @Override
        public String getName () {
            return "FLOAT_PROMPT";
        }
    };

    private final StyleableObjectProperty<FieldType> fieldType
            = new StyleableObjectProperty<FieldType>(FieldType.OUTLINED)  {

        @Override
        public CssMetaData<? extends Styleable, FieldType> getCssMetaData() {
            return StyleableProperties.FIELD_TYPE;
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "FIELD_TYPE";
        }
    };

    public GNTextArea() {
        getStyleClass().add("gn-text-area");
        setWrapText(true);
    }

    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if (this.STYLEABLES == null) {
            List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(Control.getClassCssMetaData());
            this.STYLEABLES = Collections.unmodifiableList(styleables);

        }
        return this.STYLEABLES;
    }

    private static class StyleableProperties {

        private final static List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        private static final CssMetaData<GNTextArea, Boolean> FLOAT_PROMPT;
        private static final CssMetaData<GNTextArea, FieldType> FIELD_TYPE;

        @Contract(pure = true)
        private StyleableProperties() {}

        static {

            FLOAT_PROMPT = new CssMetaData<GNTextArea, Boolean>(
                    "-gn-float-prompt",
                            BooleanConverter.getInstance(),false) {

                        @Override
                        public boolean isSettable(GNTextArea styleable) {
                            return styleable.floatPrompt == null ||
                                    !styleable.floatPrompt.isBound();
                        }

                        @Override
                        @SuppressWarnings("unchecked")
                        public StyleableProperty<Boolean> getStyleableProperty(GNTextArea styleable) {
                            return (StyleableProperty<Boolean>)
                                    styleable.floatPromptProperty();
                        }
                    };

            FIELD_TYPE = new CssMetaData<GNTextArea, FieldType>(
                    "-gn-field-type",
                    FieldTypeConverter.getInstance()) {

                @Override
                public boolean isSettable(GNTextArea styleable) {
                    return styleable.fieldType == null ||
                            !styleable.fieldType.isBound();
                }

                @Override
                @SuppressWarnings("unchecked")
                public StyleableProperty<FieldType> getStyleableProperty(GNTextArea styleable) {
                    return (StyleableProperty<FieldType>) styleable.fieldTypeProperty();
                }
            };

            List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables, FLOAT_PROMPT, FIELD_TYPE);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Contract(pure = true)
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    @Override
    protected Skin<?> createDefaultSkin () {
//        return super.createDefaultSkin();
        return new GNTextAreaSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/theme/css/controls.css").toExternalForm();
    }

    public BooleanProperty floatPromptProperty () {
        return floatPrompt;
    }

    public boolean isFloatPrompt () {
        return floatPrompt.getValue();
    }

    public void setFloatPrompt (boolean value) {
        floatPrompt.set(value);
    }

    public FieldType getFieldType() {
        return fieldType.get();
    }

    public ObjectProperty<FieldType> fieldTypeProperty() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType.set(fieldType);
    }

}
