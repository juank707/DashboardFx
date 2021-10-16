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
package io.github.gleidson28.global.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Auxiliar Class for convert to the BigDecimal.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/01/2019
 */
public class MoneyUtil {

    private static DecimalFormat formatter;

    public static String format(String value) {
        return format(get(value));
    }

    public static String format(BigDecimal value) {
        configFormatter();
        return formatter.format(value);
    }

    public static String parse(BigDecimal value) {
        System.out.println(value);
        return getPure(value);
    }

    public static BigDecimal get(String value) {
        value = value.replaceAll("[^0-9]", "");
        value = value.replaceAll("([0-9])([0-9]{2})$", "$1.$2");
        return value.length() == 0 ? new BigDecimal(0) : new BigDecimal(value);
    }

    private static String getPure(BigDecimal value) {
        configFormatter();
        return formatter.format(value).replaceAll("[^0-9.,]", "");
    }

    // was used in preferences
    public static String getSymbol() {
        configFormatter();
        return formatter.getDecimalFormatSymbols().getCurrencySymbol();
    }

    private static void configFormatter() {
        formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
    }
}
