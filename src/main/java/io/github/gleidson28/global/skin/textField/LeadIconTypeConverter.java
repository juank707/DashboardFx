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
package io.github.gleidson28.global.skin.textField;

import com.sun.javafx.css.StyleConverterImpl;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class LeadIconTypeConverter extends StyleConverterImpl<String, LeadIconType> {

    private LeadIconTypeConverter() {
    }

    public static StyleConverter<String, LeadIconType> getInstance() {
        return LeadIconTypeConverter.Holder.INSTANCE;
    }

    public LeadIconType convert(ParsedValue<String, LeadIconType> value, Font notUsedFont) {

        String string = (String) value.getValue();

        try {
            return LeadIconType.valueOf(string.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException var5) {
            Logger.getLogger(TextFieldType.class.getName()).info(String.format("Invalid button type value '%s'", string));
            return LeadIconType.FAVORITE;
        }
    }

    public String toString() {
        return "LeadIconTypeConverter";
    }

    private static class Holder {

        static final LeadIconTypeConverter INSTANCE = new LeadIconTypeConverter();

        private Holder() {
            throw new IllegalAccessError("Holder class");
        }
    }
}
