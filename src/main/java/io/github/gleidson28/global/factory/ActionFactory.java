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

import animatefx.animation.Pulse;
import animatefx.animation.Shake;
import animatefx.animation.Swing;
import io.github.gleidson28.global.cell.ActionCell;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.User;
import io.github.gleidson28.global.plugin.ViewManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/07/2019
 */
public class ActionFactory<E extends Model> implements Callback<TableColumn<E, E>, TableCell<E, E>> {

    @Override
    public TableCell<E, E> call(TableColumn<E, E> param) {


            return new TableCell<E, E>() {

                @Override
                protected void updateItem(E item, boolean empty) {

                    super.updateItem(item, empty);

                    if(item != null) {

                        ActionCell<E> cell = new ActionCell<>(getTableView() ,item);
                        setAlignment(Pos.BASELINE_RIGHT);

                        Timeline enter = new Timeline();

                        enter.getKeyFrames().addAll(
                                new KeyFrame(Duration.ZERO, new KeyValue(getTableRow().translateXProperty(), 0)),
                                new KeyFrame(Duration.millis(300), new KeyValue(getTableRow().translateXProperty(), 5))
                        );

                        getTableRow().setOnMouseEntered(event -> enter.play());

                        cell.setEdiAction(event -> {
                            try {
                                ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_register", item);
                            } catch (NavigationException e) {
                                e.printStackTrace();
                            }
                        });

                        cell.setViewAction(event -> {
                            try {
                                ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_index", item);
                            } catch (NavigationException e) {
                                e.printStackTrace();
                            }
                        });

                        cell.setDeleteAction(event -> {
                            try {
                                ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_index", item);
                            } catch (NavigationException e) {
                                e.printStackTrace();
                            }
                        });

//
//                        cell.getChildren().get(0).setOnMousePressed(event -> {
////                            getTableView().getSelectionModel().select(item);
////                            Views.sendRequisition(new Requisition<>(FormAction.VIEW, item));
//
//                        });
//
//                        cell.getChildren().get(1).setOnMousePressed(event -> {
//                            getTableView().getSelectionModel().select(item);
////                            Views.sendRequisition(new Requisition<>(FormAction.UPDATE, item));
//                        });
//
//                        cell.getChildren().get(2).setOnMousePressed(evento -> {
//                            getTableView().getSelectionModel().select(item);
//
////                            Alerts.warning("Tem certeza?", "Deseja realmente excluir o item?",
////                                    event ->  {
////
////                                        Timer timer = new Timer();
////                                        TimerTask timerTask = new TimerTask() {
////                                            @Override
////                                            public void run() {
////                                                getTableView().getItems().remove(item);
////                                            }
////                                        };
////                                        timer.schedule(timerTask, 300);
////                                    }
////                            );
//
////                            getTableView().getItems().remove(item);
//                        });

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
