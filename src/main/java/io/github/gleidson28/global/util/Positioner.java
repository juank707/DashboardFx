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

import io.github.gleidson28.decorator.GNDecorator;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/02/2021
 */
public class Positioner {

    private final GNDecorator   decorator;
    private final Stage         stage;

    public Positioner(Stage stage, GNDecorator decorator) {
        this.stage = stage;
        this.decorator = decorator;
    }

    public void setPosition(StackPane content, Pos pos) {
        switch (pos) {
            case TOP_LEFT:
                stage.setX(decorator.getX());
                stage.setY(decorator.getY());
                break;
            case TOP_CENTER:
                stage.setX(calculateCenterX(content));
                stage.setY(decorator.getY());
                break;
            case TOP_RIGHT:
                stage.setX(calculateMaxX(content));
                stage.setY(decorator.getY());
                break;
            case CENTER_LEFT:
                stage.setX(decorator.getX());
                stage.setY(calculateCenterY(content));
                break;
            case CENTER:
                stage.setX(calculateCenterX(content));
                stage.setY(calculateCenterY(content));
                break;
            case CENTER_RIGHT:
                stage.setX(calculateMaxX(content));
                stage.setY(calculateCenterY(content));
                break;
            case BOTTOM_LEFT:
            case BASELINE_LEFT:
                stage.setX(decorator.getX());
                stage.setY(calculateMaxY(content));
                break;
            case BOTTOM_CENTER:
            case BASELINE_CENTER:
                stage.setX(calculateCenterX(content));
                stage.setY(calculateMaxY(content));
                break;
            case BOTTOM_RIGHT:
            case BASELINE_RIGHT:
                stage.setX(calculateMaxX(content));
                stage.setY(calculateMaxY(content));
                break;
        }
    }

    private double calculateMaxX(StackPane content) {
        return (decorator.getWidth() + decorator.getX()) - content.getPrefWidth();
    }

    private double calculateMaxY(StackPane content) {
        return (decorator.getHeight() + decorator.getY()) - content.getPrefHeight();
    }

    private double calculateCenterX(StackPane content) {
        return (decorator.getWidth() + decorator.getX()) - (decorator.getWidth() / 2) - (content.getPrefWidth() / 2);
    }

    private double calculateCenterY(StackPane content) {
        return (decorator.getHeight() + decorator.getY()) - (decorator.getHeight() / 2) - (content.getPrefHeight() / 2);
    }
}
