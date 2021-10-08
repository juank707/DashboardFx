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

import io.github.gleidson28.App;
import io.github.gleidson28.global.creators.DrawerCreator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  31/08/2020
 */
public class LayoutController implements Initializable {

    @FXML private HBox titleContent;
    @FXML private BorderPane main;
    @FXML private VBox drawer;
    @FXML private Button hamburger;
    @FXML private GridPane gridBar;

    @FXML private StackPane foreground;

    @FXML private Label lbl_title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.INSTANCE.getDecorator().widthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.doubleValue() < 500){
                if(!titleContent.getChildren().contains(hamburger)) titleContent.getChildren().add(0, hamburger);
                hideDrawer();
                showHamburger();
            } else if(newValue.doubleValue() > 600) {
                hideHamburger();
                showDrawer();
            }
        });
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
        DrawerCreator.INSTANCE.closePopup();
        main.getChildren().remove(drawer);
        hamburger.setVisible(true);
    }

    private void showDrawer(){
        if(!main.getChildren().contains(drawer)) {
            drawer.setPrefWidth(250D);
            main.setLeft(drawer);
        }
    }

    private void showHamburger() {
        App.INSTANCE.getDecorator().addControl(hamburger);
        lbl_title.setPadding(new Insets(0,0,0,50));
        gridBar.setPrefHeight(30);
        gridBar.setMinHeight(30);
        lbl_title.setMinHeight(30);
        lbl_title.getStyleClass().addAll("h4");
        hamburger.setVisible(true);
    }

    private void hideHamburger(){
        gridBar.setMinHeight(50);
        hamburger.setVisible(false);
//        lbl_title.setPrefHeight(50);
//        lbl_title.setPadding(new Insets(0, 0 ,0 ,10));
//        lbl_title.getStyleClass().removeAll("h4");
    }
}
