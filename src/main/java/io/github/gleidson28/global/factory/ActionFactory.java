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

import io.github.gleidson28.global.cell.ActionCell;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.plugin.ViewManager;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/07/2019
 */
public class ActionFactory<E extends Professional> implements Callback<TableColumn<E, E>, TableCell<E, E>> {

    @Override
    public TableCell<E, E> call(TableColumn<E, E> param) {


            return new TableCell<E, E>() {

                @Override
                protected void updateItem(E item, boolean empty) {

                    super.updateItem(item, empty);

                    if(item != null) {

                        ActionCell<E> cell = new ActionCell<>(getTableView() ,item);
                        setAlignment(Pos.BASELINE_RIGHT);



                        cell.setEdiAction(event -> {
                            try {
                                ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_register", item);
                            } catch (NavigationException e) {
                                e.printStackTrace();
                            }
                        });

                        cell.setViewAction(event -> {
                            try {
                                ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_view", item);
                            } catch (NavigationException e) {
                                e.printStackTrace();
                            }
                        });

                        cell.setDeleteAction(event -> {
                            this.getTableView().getItems().remove(item);
                            new ProfessionalPresenter().getAll().remove(item);
                        });

                        setGraphic(cell);
                        setItem(item);
                        setText(null);
                    } else {
                        setItem(null);
                        setText(null);
                        setGraphic(null);
                    }

                }
            };
        }
}
