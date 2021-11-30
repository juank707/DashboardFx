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
package io.github.gleidson28.module.app;

import javafx.beans.property.ReadOnlyDoubleProperty;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/11/2021
 */
public class App {

    public static ReadOnlyDoubleProperty widthProperty() {
        return ConfigApp.INSTANCE.getDecorator().widthProperty();
    }

    public static ReadOnlyDoubleProperty heightProperty() {
        return ConfigApp.INSTANCE.getDecorator().heightProperty();
    }

    public static double getWidth() {
        return ConfigApp.INSTANCE.getDecorator().getWidth();
    }

    public static double getHeight() {
        return ConfigApp.INSTANCE.getDecorator().getHeight();
    }

    public static void openLink(String link) {
        ConfigApp.INSTANCE.openLink(link);
    }


}
