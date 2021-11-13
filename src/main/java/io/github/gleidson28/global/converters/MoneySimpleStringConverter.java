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
package io.github.gleidson28.global.converters;

import io.github.gleidson28.global.util.MoneyUtil;
import javafx.util.StringConverter;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/01/2021
 */
public class MoneySimpleStringConverter extends StringConverter<BigDecimal> {

    @Override
    public String toString(BigDecimal object) {


        if (object != null) {
            System.out.println("object = " + MoneyUtil.parse(object));
            return MoneyUtil.parse(object);
        } else {
            return MoneyUtil.parse(BigDecimal.ZERO);
        }
    }

    @Override
    public BigDecimal fromString(String string) {


        if (string != null && !string.isEmpty()) {
            System.out.println("ssss = " + MoneyUtil.get(string));
            return MoneyUtil.get(string);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
