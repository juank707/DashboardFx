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
package io.github.gleidson28.global.factory;

import java.lang.Number;

import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.User;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/09/2021
 */
public class RatingFactory<E extends Model> implements Callback<TableColumn<E, Number>, TableCell<E, Number>> {


    @Override
    public TableCell<E, Number> call(TableColumn<E, Number> param) {
        return new TableCell<E, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {

                    Rating rating = new Rating();
                    rating.setRating(item.doubleValue());
                    rating.setPartialRating(true);
                    rating.setMouseTransparent(true);

                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(String.valueOf(Math.round(rating.getRating()*100.0)/100.0));

                    setTooltip(tooltip);

                    rating.setScaleX(0.6);
                    rating.setScaleY(0.6);

                    rating.setTranslateX(-25);

                    setGraphic(rating);
                    setText(null);
                    setItem(item);
                } else {
                    setText(null);
                    setItem(null);
                    setGraphic(null);
                }
            }
        };
    };
}
