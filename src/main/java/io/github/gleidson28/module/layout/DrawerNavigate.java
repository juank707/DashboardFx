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
 * along with popOver program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidson28.module.layout;

import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.enhancement.FluidView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.properties.*;
import io.github.gleidson28.module.controls.LayoutController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/09/2020
 */
public class DrawerNavigate implements Initializable, FluidView {

    @FXML private VBox drawerBox;
    @FXML private HBox searchBox;
    @FXML private Hyperlink language;
    @FXML private ChoiceBox<Label> choiceLanguage;
    @FXML private Label title;
    @FXML private Button btn_settings;

    @FXML private GNAvatarView avatarView;

    private LayoutController layoutController;

    private Properties properties = new Properties();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new DrawerController(drawerBox, searchBox);

        try {
            InputStream input = new FileInputStream("app/controls.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(path == null) {
//            path = "app/media/img/avatars/man@400.png";
//            try {
//                avatarView.setImage(new Image(new FileInputStream(path)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } else {
//            avatarView.setImage(new Image(in));
//        }


//        avatarView.setImage(new Image( new UserPresenter().getActive().getAvatar("50")));


    }

    @FXML private void goDashboard() throws NavigationException {
        ViewManager.INSTANCE.setContent("dashboard");
    }

    @FXML
    private void goSales() throws NavigationException {

    }

    @FXML
    private void goDash() throws NavigationException {
        ViewManager.INSTANCE.setContent("dashboard");
    }






    @FXML
    private void goCarousel() throws NavigationException {
        ViewManager.INSTANCE.setContent("carousel");
    }

    @FXML
    private void goAreaChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("area_chart");
    }

    @FXML
    private void goBarChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("bar_chart");
    }

    @FXML
    private void goBubbleChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("bubble_chart");
    }

    @FXML
    private void goLineChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("line_chart");
    }

    @FXML
    private void goPieChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("pie_chart");
    }

    @FXML
    private void goScatter() throws NavigationException {
        ViewManager.INSTANCE.setContent("scatter_chart");
    }

    @FXML
    private void goStackedAreaChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("stacked_area_chart");
    }

    @FXML
    private void goStackedBarChart() throws NavigationException {
        ViewManager.INSTANCE.setContent("stacked_bar_chart");
    }

    @FXML
    private void goColors() throws NavigationException {
        ViewManager.INSTANCE.setContent("color");
    }

    @FXML
    private void goPopupCreator() throws NavigationException {
        ViewManager.INSTANCE.setContent("popup-creator");
    }


    @FXML
    private void goDataTable() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_index");

    }

    @FXML
    private void goSettings() throws NavigationException {
        ViewManager.INSTANCE.setContent("settings");
    }

    @FXML
    private void goAbout() throws NavigationException {
        ViewManager.INSTANCE.setContent("about");
    }

    @FXML
    private void openOptions() {

    }

    private void configLayoutControl(Control control, List<Tab> tabs, Selector... selectors) throws NavigationException {

        if(layoutController == null) {
            layoutController = (LayoutController)
                    ViewManager.INSTANCE.getController("control-layout");
        }

        String className = control.getClass().getSimpleName();

        ViewManager.INSTANCE.setContent("control-layout", className);

        layoutController.setControl(
                control,
                tabs,
                properties.getProperty(className + ".about"),
                selectors
        );
    }

    @FXML
    private void goCheckBox() throws NavigationException {
        CheckBox checkBox = new CheckBox("CheckBox");
        checkBox.getProperties().putIfAbsent("prefix", "check-");
        configLayoutControl(
                checkBox,
                Collections.singletonList(new Tab("Skin")),
                new ColorSelector(
                        checkBox,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new TypeSelector(
                        checkBox,
                        "Rounded", "Flat", "Task"
                ),
                new SizeSelector(checkBox, "small")
        );
    }

    @FXML
    private void goPagination() throws NavigationException {
        Pagination pagination = new Pagination();
        pagination.getProperties().putIfAbsent("prefix", "pag-");
        configLayoutControl(
                pagination,
                Collections.singletonList(new Tab("Skin")),
                new ColorSelector(
                        pagination,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new TypeSelector(
                        pagination,
                        "Rounded", "Triangle"
                )
        );
    }


    @FXML
    private void goButton() throws NavigationException {

        Button button = new Button("Button");

        SVGPath iconCopy = new SVGPath();
        iconCopy.setContent("M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z");
        iconCopy.getStyleClass().add("icon");

//        button.setGraphic(iconCopy);
        button.setPrefSize(80, 60);

        button.getProperties().putIfAbsent("prefix", "btn-");

        configLayoutControl(
                button,
                Arrays.asList(
                        new Tab("Skin"),
                        new Tab("Lead Icon")
                ),
                new ColorSelector(
                        button,
                        "Primary", "Info", "Success", "Warning", "Danger", "Secondary"
                ),
                new TypeSelector(
                        button,
                        "Flat", "Round", "Rounded", "Deep",
                        "Raised", "Outlined"
                ),
                new SizeSelector(button, "medium")

        );

    }



    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }
}
