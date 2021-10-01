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
package com.gn.module.main;

import animatefx.animation.Shake;
import com.gn.App;
import com.gn.DashApp;
import com.gn.global.exceptions.NavigationException;
import com.gn.global.plugin.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/04/2020
 */
public class DrawerNavigate implements Initializable  {

    @FXML private VBox drawer;
    @FXML private TextField search;
    @FXML private Button clear;
    @FXML private Label name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new DrawerController( drawer );

        search.setOnMouseClicked(event -> new Shake(clear).play());
        name.setOnMouseEntered(event -> new Shake(name).play());

    }

    @FXML
    private void clearText() {
        search.clear();
    }

    @FXML
    private void goCards(){
        updateViewDetails("cards");
    }

    @FXML
    private void goProjects(){
        updateViewDetails("projects");
    }

    @FXML
    private void goAbout(){
        updateViewDetails("about");
    }

    @FXML
    private void buttons() {
        updateViewDetails("button");
    }

    @FXML
    private void goCarousel() {
        updateViewDetails("carousel");
    }

    @FXML
    private void goToggle() {
        updateViewDetails("toggle-button");
    }

    @FXML
    private void goTextField(){
        updateViewDetails("text-field");
    }

    @FXML
    private void goDatePicker(){
        updateViewDetails("datepicker");
    }

    @FXML
    private void goCheckBox(){
        updateViewDetails("check-box");
    }

    @FXML
    private void goComboBox(){
        updateViewDetails("combobox");
    }

    @FXML
    private void goColorPicker(){
        updateViewDetails("color-picker");
    }

    @FXML
    private void goChoiceBox(){
        updateViewDetails("choice-box");
    }

    @FXML
    private void goSplitMenuButton(){
        updateViewDetails("splitmenubutton");
    }

    @FXML
    private void goMenuButton(){
        updateViewDetails("menubutton");
    }

    @FXML
    private void goMenuBar(){
        updateViewDetails("menubar");
    }

    @FXML
    private void goSlider(){
        updateViewDetails("slider");
    }

    @FXML
    private void goMediaView(){
        updateViewDetails("mediaview");
    }

    @FXML
    private void goLabel(){
        updateViewDetails("label");
    }

    @FXML
    private void goImageView(){
        updateViewDetails("imageview");
    }

    @FXML
    private void goHyperlink(){
        updateViewDetails("hyperlink");
    }

    @FXML
    private void goSpinner(){
        updateViewDetails("spinner");
    }

    @FXML
    private void goListView(){
        updateViewDetails("listview");
    }

    @FXML
    private void goRadioButton(){
        updateViewDetails("radiobutton");
    }

    @FXML
    private void goProgressBar(){
        updateViewDetails("progressbar");
    }

    @FXML
    private void goPasswordField(){
        updateViewDetails("passwordfield");
    }

    @FXML
    private void goProgressIndicator(){
        updateViewDetails("progressindicator");
    }

    @FXML
    private void goPagination(){
        updateViewDetails("pagination");
    }

    @FXML
    private void goPieChart(){
        updateViewDetails("pie-chart");
    }

    @FXML
    private void goStackedBarChart(){
        updateViewDetails("stacked-bar-chart");
    }

    @FXML
    private void goStackedAreaChart(){
        updateViewDetails("stacked-area-chart");
    }

    @FXML
    private void goScatterChart(){
        updateViewDetails("scatter-chart");
    }

    @FXML
    private void goDashboard(){
        updateViewDetails("dashboard");
    }

    @FXML
    private void goAreaChart(){
        updateViewDetails("area-chart");
    }

    @FXML
    private void goBarChart(){
        updateViewDetails("bar-chart");
    }

    @FXML
    private void goBubbleChart(){
        updateViewDetails("bubble-chart");
    }

    @FXML
    private void goLineChart(){
        updateViewDetails("line-chart");
    }

    @FXML
    private void goTableView(){
        updateViewDetails("tableview");
    }

    @FXML
    private void goScrollBar(){
        updateViewDetails("scrollbar");
    }

    @FXML
    private void goTreeTableView(){
        updateViewDetails("tree-table-view");
    }

    @FXML
    private void goTextArea(){
        updateViewDetails("text-area");
    }

    @FXML
    private void goTreeView(){
        updateViewDetails("tree-view");
    }

    @FXML
    private void goAnimateButtons(){
        updateViewDetails("animated-button");
    }

    @FXML
    private void goAlerts(){
        updateViewDetails("alerts");
    }

    @FXML
    private void goPopupCreator(){
        updateViewDetails("popup-creator");
    }

    @FXML
    private void goColors(){
        updateViewDetails("colors");
    }

    @FXML
    private void goTypographic(){
        updateViewDetails("typography");
    }

    @FXML
    private void goDataFilter(){
        updateViewDetails("data-filter");
    }


    private void updateViewDetails(String viewNm){
        try {
            ScrollPane body = (ScrollPane) DashApp.decorator.lookup("#body");
            Label title = (Label)  DashApp.decorator.lookup("#title");

            title.setText(ViewManager.INSTANCE.openSubView(body, viewNm));
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }
}
