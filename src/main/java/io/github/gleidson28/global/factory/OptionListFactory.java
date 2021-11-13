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

import io.github.gleidson28.App;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.util.MoneyUtil;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/10/2021
 */
public class OptionListFactory implements Callback<ListView<Professional>, ListCell<Professional>> {

    @Override
    public ListCell<Professional> call(ListView<Professional> param) {
        return new ListCell<Professional>() {
            @Override
            protected void updateItem(Professional item, boolean empty) {

                super.updateItem(item, empty);

                if (item != null) {

                    HBox body = new HBox();
                    Circle clip = new Circle();

                    clip.setStroke(Color.WHITE);
                    clip.setStrokeWidth(0.6);

                    ImagePattern imagePattern = new ImagePattern(item.getAvatar("50"));

                    clip.setFill(imagePattern);
                    double frac = 20;

                    clip.setRadius(frac);
                    clip.setCenterX(frac);
                    clip.setCenterY(frac);

                    ////////////////////

                    GridPane content = new GridPane();
                    Label name = new Label(item.getName());
                    name.setStyle("-fx-font-weight : bold; ");
                    Label price = new Label(MoneyUtil.format(item.getPrice()));
                    Rating rating = new Rating();
                    rating.setRating(item.getRating());
                    rating.setTranslateX(-35D);
                    rating.setScaleX(0.6);
                    rating.setScaleY(0.6);

                    Label status = new Label();
                    status.setAlignment(Pos.CENTER);
                    status.setMinSize(20, 20);
                    status.setPadding(new Insets(3));
                    status.setStyle("-fx-background-radius : 20px;");

                    status.getStyleClass().add("lbl-status");
                    status.setText(item.getStatus().toString());

                    String _default = "-fx-text-fill : white; " +
                            "-fx-border-width : 2px;  -fx-border-color : white;";

                    switch (item.getStatus()) {

                        case UNAVAILABLE:
                            status.setStyle("-fx-background-color : -amber; " + _default);
                            break;
                        case FREE:
                            status.setStyle("-fx-background-color : -success; " + _default);
                            break;
                        default:
                            status.setStyle("-fx-background-color : -grapefruit; " + _default);
                            break;
                    }

                    content.setPadding(new Insets(5));

                    content.getChildren().addAll(name, price, status);

                    GridPane.setConstraints(name, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                    GridPane.setConstraints(price, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                    GridPane.setConstraints(status, 1, 0, 1, 2, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

//                            content.setGridLinesVisible(true);

                    Button view = new Button();
                    view.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    SVGPath icon = new SVGPath();
                    icon.setContent("M6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm12 0c-1.1 0-2 .9-2 2s.9 2 2 " +
                            "2 2-.9 2-2-.9-2-2-2zm-6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z");
                    view.setGraphic(icon);
                    icon.setRotate(90);

                    view.getStyleClass().addAll("round", "btn-small", "btn-action");

                    view.setMinHeight(40D);
                    view.setPrefHeight(40D);

                    body.getChildren().addAll(clip, content, view);
                    body.setAlignment(Pos.CENTER);

                    HBox.setHgrow(content, Priority.ALWAYS);

                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem viewItem = new MenuItem("View");
                    MenuItem editItem = new MenuItem("Edit");
                    MenuItem deleteItem = new MenuItem("Delete");

                    contextMenu.getItems().addAll(viewItem, editItem, deleteItem);

                    SVGPath iconView = new SVGPath();
                    SVGPath iconDelete = new SVGPath();
                    SVGPath iconUpdate = new SVGPath();

                    iconView.getStyleClass().add("icon");
                    iconDelete.getStyleClass().add("icon");
                    iconUpdate.getStyleClass().add("icon");

                    iconView.setContent("M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z");

                    iconUpdate.setContent("M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z");

                    iconDelete.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");

                    viewItem.setGraphic(iconView);
                    editItem.setGraphic(iconUpdate);
                    deleteItem.setGraphic(iconDelete);

                    double sX = 0.8D;
                    double sY = 0.8D;

                    iconView.setScaleX(sX);
                    iconUpdate.setScaleX(sX);
                    iconDelete.setScaleX(sX);

                    iconView.setScaleY(sY);
                    iconUpdate.setScaleY(sY);
                    iconDelete.setScaleY(sY);

                    view.setOnMouseClicked(event -> {
                        Bounds bounds = body.localToScreen(body.getBoundsInLocal());
                        contextMenu.show(App.INSTANCE.getDecorator().getWindow(),
                                bounds.getMaxX(), bounds.getMinY()
                                );
                    });


                    editItem.setOnAction(event -> {
                        try {
                            ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_register", item);
                        } catch (NavigationException e) {
                            e.printStackTrace();
                        }
                    });

                    viewItem.setOnAction(event -> {
                        try {
                            ViewManager.INSTANCE.setContent(item.getClass().getSimpleName().toLowerCase() + "_view", item);
                        } catch (NavigationException e) {
                            e.printStackTrace();
                        }
                    });

                    deleteItem.setOnAction(event -> {
                        this.getListView().getItems().remove(item);
                        new ProfessionalPresenter().getAll().remove(item);
                    });


                    setGraphic(body);
                    setItem(item);
                    setText(null);


                } else {
                    setItem(null);
                    setGraphic(null);
                    setText(null);
                }
            }
        };
    }
}
