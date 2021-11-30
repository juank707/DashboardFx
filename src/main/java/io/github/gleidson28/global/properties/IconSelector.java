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
package io.github.gleidson28.global.properties;

import io.github.gleidson28.global.popup.Popup;
import io.github.gleidson28.global.material.icon.IconContainer;
import io.github.gleidson28.global.material.icon.Icons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class IconSelector extends Selector {

    private final Control control;
    private PropertyAside aside;

    private final ToggleGroup   group   = new ToggleGroup();

    private ObservableList<String> _class = FXCollections.observableArrayList();

    public IconSelector(Control control) {

        this.control = control;

        Button btn = new Button("Choose Icon");
        btn.setPrefSize(300, 40);
        btn.setPrefWidth(300);

        Label title = new Label("TextField Options");
        title.getStyleClass().add("h5");

        VBox box = new VBox(btn);
        box.setAlignment(Pos.CENTER);
        box.setMinHeight(100);

        getChildren().addAll(createSeparator(), title, box);

        VBox.setMargin(title, new Insets(10,0,0,0));

        addListenerOnGroup(btn);

        btn.setContentDisplay(ContentDisplay.BOTTOM);
        btn.setMinHeight(100);
        btn.getStyleClass().addAll("btn-choose-icon");

        ScrollPane scrollPane = createContent();

        btn.setOnMouseClicked(event -> {
            Popup popup = new Popup(scrollPane);
            popup.showTopRight(btn);
        });

        btn.graphicProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue != null) {
                IconContainer icon = (IconContainer) oldValue ;
                removeClass(icon.getName());
            }

            if (newValue != null) {
                IconContainer icon = (IconContainer) newValue ;
                addClass(icon.getName());
            }
        });
    }

    private void addListenerOnGroup(Button btn) {

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                ToggleButton _toggle = (ToggleButton) newValue;
                IconContainer _cont = (IconContainer) _toggle.getGraphic();
                IconContainer c = new IconContainer(_cont.getName());
                c.setScaleX(1.5);
                c.setScaleY(1.5);

                btn.setGraphic( c );
                btn.setGraphicTextGap(10);

            }
        });
    }

    private ScrollPane createContent() {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        scrollPane.setPrefSize(5 * 80, Icons.values().length * 20);

        Label pTitle = new Label("Icons");
        pTitle.getStyleClass().add("h2");
        VBox.setMargin(pTitle, new Insets(10, 0, 10, 0));
        VBox container = new VBox(pTitle, createSeparator());
        container.setPadding(new Insets(20));

        GridPane layout = new GridPane();
        VBox.setMargin(layout, new Insets(10,0,0,0));

        int col = 0;
        int row = 0;

        for (Icons icon : Icons.values()) {

            if (col == 5) {
                col = 0;
                row++;
            }

            if (icon.equals(Icons.NULL) || icon.equals(Icons.NONE)) break;

            ToggleButton select = new ToggleButton();
            select.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            select.setAlignment(Pos.CENTER);
            select.getStyleClass().addAll("btn-choose-icon", "btn-icon-select");
            select.setMinHeight(50);
            select.setPrefSize(40,40);
            select.setMinSize(40,40);

            group.getToggles().add(select);

            IconContainer iconContainer = new IconContainer(icon);
            layout.getChildren().add(select);

            select.setGraphic(iconContainer);

            layout.setHgap(10);
            layout.setVgap(10);

            GridPane.setConstraints(select, col, row, 1, 1,
                    HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

            col++;
        }


        container.getChildren().add(layout);
        scrollPane.setContent(container);

        return scrollPane;
    }

    private void addClass(String newValue) {

        control.getStyleClass().addAll( "lead-" +
                newValue.toLowerCase().replaceAll("_", "-")
        );

        if (aside != null)
            aside.addStyleClass("lead-"  +
                newValue.toLowerCase().replaceAll("_", "-") + ","
        );

        if(!_class.contains("action" + newValue)) {
            _class.add(newValue);
        }
    }

    private void removeClass(String oldValue) {

        control.getStyleClass().removeAll("lead-"
                + oldValue.toLowerCase().replaceAll("_", "-"));
        aside.removeStyleClass("lead-"
                + oldValue.toLowerCase().replaceAll("_", "-") + ",");
    }

    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {
        this.control.setOnMouseEntered(null);
        this.control.setOnMouseExited(null);

        for (String cl : _class) {
            removeClass(cl);
        }
    }
}
