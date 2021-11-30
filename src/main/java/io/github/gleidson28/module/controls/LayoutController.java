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
package io.github.gleidson28.module.controls;

import io.github.gleidson28.global.creators.DrawerCreator;
import io.github.gleidson28.global.enhancement.ActionView;
import io.github.gleidson28.global.material.icon.IconContainer;
import io.github.gleidson28.global.material.icon.Icons;
import io.github.gleidson28.global.properties.PropertyAside;
import io.github.gleidson28.global.properties.Selector;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/11/2021
 */
public class LayoutController implements Initializable, ActionView {

    @FXML private BorderPane body;
    @FXML private TilePane boxControl;
    @FXML private Label title;
    @FXML private Text about;
    @FXML private VBox header;
    @FXML private TabPane tabs;

    @FXML private ChoiceBox<String> options;

    @FXML private Tab skin;

    @FXML private GridPane grid;

    @FXML private VBox box;

    @FXML private TextField textTest;

    private final Button        hamburger   = new Button();
    private final PropertyAside aside       = new PropertyAside();

    @FXML private ComboBox<String> comboBox;

    private final ChangeListener<Number> resizeLayout = (observable, oldValue, newValue) -> {
        if(newValue.doubleValue() < 800) {
            if (!header.getChildren().contains(hamburger)) {
                header.getChildren().add(hamburger);
                hamburger.toBack();
            }
            body.setLeft(null);
        } else {
            header.getChildren().remove(hamburger);
            body.setLeft(aside);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hamburger.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        hamburger.getStyleClass().addAll("btn-flat");
        hamburger.setPadding(new Insets(0,10,0,10));

        hamburger.setPrefSize(40, 40);
        hamburger.setMaxSize(40, 40);

        hamburger.setMinHeight(40);

        hamburger.setGraphic(new IconContainer(Icons.HAMBURGER));

        hamburger.setOnMouseClicked(event -> {
            DrawerCreator.INSTANCE.createDrawerLeft(aside);
        });

        comboBox.getItems().addAll("First Value", "Second Value");
    }

    public void setControl(Control control,  String about,  Selector... selectors) {
        setControl(control,  null, null, selectors);
    }

    public void setControl(Control control, String about, List<Tab> tabs, Selector... selectors) {

        title.setText(control.getClass().getSimpleName());

        this.about.setText(about);

        this.tabs.getTabs().remove(1, this.tabs.getTabs().size());
        if (tabs != null) {
            for (Tab tab : tabs) {
                this.tabs.getTabs().add(tab);
            }
        }

        aside.clear();
        aside.setControl(control);

        for (Selector selector : selectors) {
            selector.setAside(aside);
            aside.addSelector(selector);
        }

        boxControl.getChildren().clear();
        boxControl.getChildren().add(control);

        body.setLeft(aside);
    }

    @Override
    public void onEnter() {
        addListener(body, resizeLayout);
    }

    @Override
    public void onExit() {
        removeListener(body);
    }
}
