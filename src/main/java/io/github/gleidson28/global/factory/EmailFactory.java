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

import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.util.MoneyUtil;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/09/2021
 */
public class EmailFactory<E extends Professional> implements Callback<TableColumn<E, String>, TableCell<E, String>> {

    @Override
    public TableCell<E, String> call(TableColumn<E, String> param) {
        return new TableCell<E, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {

                    if(item.isEmpty()) {
                        setText("Not registered");
                        setGraphic(null);
                    } else {
                        setGraphic(new Hyperlink(item));
                        setText(null);
                    }

                    setItem(item);
                } else {
                    setText(null);
                    setItem(null);
                    setGraphic(null);
                }
            }
        };
    }

}
