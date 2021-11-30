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
package io.github.gleidson28.module.layout;

import io.github.gleidson28.global.creators.DrawerCreator;
import io.github.gleidson28.global.enhancement.ActionView;
import io.github.gleidson28.module.app.App;
import io.github.gleidson28.module.app.ConfigApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  31/08/2020
 */
public class LayoutController implements Initializable, ActionView {

    @FXML private HBox titleContent;
    @FXML private BorderPane main;
    @FXML private VBox drawer;
    @FXML private Button hamburger;
    @FXML private GridPane gridBar;

    @FXML private Label breadTitle;

    @FXML private VBox content;

    @FXML private StackPane foreground;

    @FXML private Label lbl_title;

    private ObservableList<ChangeListener<Number>> widthListeners =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hamburger.getStyleClass().add("btn-flat");
    }

    private void changed(ObservableValue<? extends Number> observable,
                         Number oldValue, Number newValue) {

        double x = App.getWidth() -
                (drawer.getWidth() + breadTitle.getWidth());


        removeOrAddBread(x < 220);

        if(newValue.doubleValue() < 500) {

//                if(!titleContent.getChildren().contains(hamburger)) titleContent.getChildren().add(0, hamburger);

            if(!ConfigApp.INSTANCE.getDecorator()
                    .getCustomControls().contains(hamburger)) {
                ConfigApp.INSTANCE.getDecorator()
                        .getCustomControls().add(hamburger);
                }

            hideDrawer();
            showHamburger();
            VBox.setMargin(breadTitle, new Insets(5));

        } else if(newValue.doubleValue() > 600) {
            hideHamburger();
            showDrawer();
            VBox.setMargin(breadTitle, new Insets(5,100,5,5));

        }
    }

    public void foregroundOpen() {
        foreground.toFront();
    }

    public void foregroundClose() {
        foreground.toBack();
    }

    @FXML
    private void openDrawer(){
        DrawerCreator.INSTANCE.createDrawerLeft(drawer);
        hamburger.setVisible(false);
    }

    private void hideDrawer() {
        DrawerCreator.INSTANCE.closePopup(hamburger);
        main.getChildren().remove(drawer);

        hamburger.setVisible(true);
        hamburger.toBack();

        if(!ConfigApp.INSTANCE.getDecorator().getCustomControls().contains(hamburger)){
            ConfigApp.INSTANCE.getDecorator().addControl(hamburger);
        }

        removeOrAddBread(true);
    }

    private void showDrawer(){
        if(!main.getChildren().contains(drawer)) {
            drawer.setPrefWidth(250D);
            main.setLeft(drawer);

            ConfigApp.INSTANCE.getDecorator().removeControl(hamburger);
            removeOrAddBread(false);
        }
    }

    private void removeOrAddBread(boolean remove) {
//
        if(remove) {
            VBox box = (VBox) drawer.lookup("#drawerBox");
            HBox h = (HBox) ConfigApp.INSTANCE.getDecorator().lookup(".badges");
            if(h != null && !box.getChildren().contains(h)) {
                box.getChildren().add(h);
                HBox.setHgrow(h, Priority.ALWAYS);
                h.setAlignment(Pos.CENTER);
                h.setPadding(new Insets(10));
                VBox.setVgrow(h, Priority.ALWAYS);
                h.toBack();
            }
        } else {

            HBox h = (HBox) ConfigApp.INSTANCE.getDecorator().lookup(".badges");
            if(h != null) {
                h.setPadding(new Insets(0));
                if (!ConfigApp.INSTANCE.getDecorator().getCustomControls().contains(h)) {
                    ConfigApp.INSTANCE.getDecorator().getCustomControls().add(h);
                    h.setAlignment(Pos.CENTER_RIGHT);
                }
            }
        }

    }

    private void showHamburger() {
        breadTitle.setPadding(new Insets(30,5,5,5));
    }

    private void hideHamburger(){

        breadTitle.setPadding(new Insets(5));
//        gridBar.setMinHeight(50);
//        hamburger.setVisible(false);
//        lbl_title.setPrefHeight(50);
//        lbl_title.setPadding(new Insets(0, 0 ,0 ,10));
//        lbl_title.getStyleClass().removeAll("h4");
    }

    @Override
    public void onEnter() {
        addGlobalListener(this::changed);
    }

    @Override
    public void onExit() {
        removeGlobalListener();
    }
}
