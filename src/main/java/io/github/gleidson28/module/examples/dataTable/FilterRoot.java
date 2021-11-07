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
package io.github.gleidson28.module.examples.dataTable;

import io.github.gleidson28.global.model.Status;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/10/2021
 */
public class FilterRoot extends StackPane {

    private final RangeSlider price;
    private final RangeSlider rating;
    private final ToggleGroup status;

    public FilterRoot(RangeSlider price, RangeSlider rating, ToggleGroup status) {

        this.price = price;
        this.rating = rating;
        this.status = status;

        this.setMinHeight(200D);
        this.setPrefSize(350, 370);
        this.setPadding(new Insets(20));

        this.getChildren().add(createBody());
    }

    private VBox createBody() {
        VBox body = new VBox();

        body.getChildren().addAll(createTitle(), createContent());

        return body;
    }

    private VBox createTitle() {
        VBox content = new VBox();

        content.setAlignment(Pos.CENTER);
        content.getStyleClass().addAll("border-b-1", "border");
        content.setMinHeight(48);
        content.setPrefSize(274,61);

        Label title = new Label("Filters");

        title.getStyleClass().add("h4");
        title.setStyle("-fx-font-weight : bold;");
        content.getChildren().addAll(title);
        return content;
    }

    private ScrollPane createContent() {
        ScrollPane root = new ScrollPane();
        root.setFitToWidth(true);
        VBox.setVgrow(root, Priority.ALWAYS);

        VBox content = new VBox();
        content.setSpacing(10D);

        // Price
        VBox boxPrice = new VBox();
        boxPrice.setPrefHeight(70);
        Label lblPrice = new Label("Price:");
        boxPrice.getChildren().addAll(lblPrice, price);
        VBox.setMargin(boxPrice, new Insets(10, 0, 0,0));
        boxPrice.setFillWidth(true);
        VBox.setMargin(price, new Insets(5));

        price.setBlockIncrement(10);
        price.setLowValue(0);
        price.setMajorTickUnit(10);
        price.setMax(100);
        price.setMin(0);
        price.setMinorTickCount(10);
        price.setShowTickLabels(true);
        price.setShowTickMarks(true);
        price.setSnapToTicks(true);
        price.setHighValue(100);

        // Rating
        VBox boxRating = new VBox();
        boxRating.setPrefHeight(70);
        Label lblRating = new Label("Rating:");
        boxRating.getChildren().addAll(lblRating, rating);
        VBox.setMargin(boxRating, new Insets(10, 0, 0,0));
        boxRating.setFillWidth(true);
        VBox.setMargin(rating, new Insets(5));

        rating.setBlockIncrement(1);
        rating.setLowValue(0);
        rating.setMajorTickUnit(1);
        rating.setMax(5);
        rating.setMin(0);
        rating.setMinorTickCount(1);
        rating.setShowTickLabels(true);
        rating.setShowTickMarks(true);
        rating.setSnapToTicks(true);
        rating.setHighValue(5);

        // Status
        VBox boxStatus = new VBox();
        boxStatus.setPrefHeight(70);
        Label lblStatus = new Label("Status:");

        VBox.setMargin(lblStatus, new Insets(0, 0, 10, 0));

        boxStatus.getChildren().addAll(lblStatus);

        VBox.setMargin(boxStatus, new Insets(10, 0, 0,0));
        boxStatus.setFillWidth(true);

        GridPane gridContent = new GridPane();
        gridContent.setAlignment(Pos.CENTER);

        RadioButton all = new RadioButton("All");
        all.setUserData(Status.ALL);
        all.setSelected(true);
        RadioButton free = new RadioButton("Free");
        free.setUserData(Status.FREE);
        free.getStyleClass().addAll("radio-success");
        RadioButton busy = new RadioButton("Busy");
        busy.setUserData(Status.BUSY);
        busy.getStyleClass().addAll("radio-danger");
        RadioButton unavailable = new RadioButton("Unavailable");
        unavailable.setUserData(Status.UNAVAILABLE);
        unavailable.getStyleClass().addAll("radio-amber");

        all.setToggleGroup(status);
        free.setToggleGroup(status);
        busy.setToggleGroup(status);
        unavailable.setToggleGroup(status);

        gridContent.add(all, 0, 0);
        gridContent.add(free, 1, 0);
        gridContent.add(busy, 2, 0);
        gridContent.add(unavailable, 3, 0);

        gridContent.setHgap(10);

        GridPane.setHalignment(busy, HPos.CENTER);
        GridPane.setHalignment(free, HPos.CENTER);
        GridPane.setHalignment(unavailable, HPos.RIGHT);

        boxStatus.getChildren().addAll(gridContent);

        content.getChildren().addAll(boxPrice, boxRating, boxStatus);
        root.setContent(content);

        return root;
    }

}
