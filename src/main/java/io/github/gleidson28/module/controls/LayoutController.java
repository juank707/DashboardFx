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
import io.github.gleidson28.global.enhancement.ObserverView;
import io.github.gleidson28.global.properties.PropertyAside;
import io.github.gleidson28.global.properties.Selector;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/11/2021
 */
public class LayoutController implements Initializable, ObserverView {

    @FXML private BorderPane body;
    @FXML private TilePane boxControl;
    @FXML private Label title;
    @FXML private Text about;
    @FXML private VBox header;
    @FXML private TabPane tabs;

    @FXML private Tab skin;

    @FXML private GridPane grid;

    @FXML private VBox box;

    private final Button        hamburger   = new Button();
    private final PropertyAside aside       = new PropertyAside();

    @FXML private Button btnAlternate;

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
        hamburger.getStyleClass().addAll("btn-transparent", "border");
        hamburger.setPadding(new Insets(0,10,0,10));

        hamburger.setPrefSize(40, 40);
        hamburger.setMaxSize(40, 40);

        SVGPath icon = new SVGPath();
        icon.setContent("M2 15.5v2h20v-2H2zm0-5v2h20v-2H2zm0-5v2h20v-2H2z");
        icon.setStyle("-fx-fill : -text-color;");
        hamburger.setGraphic(icon);

        hamburger.setOnMouseClicked(event -> {
            DrawerCreator.INSTANCE.createDrawerLeft(aside);
        });

//        box.getChildren().forEach( child -> {
//            GridPane newChild = (GridPane) child;
//            newChild.widthProperty().addListener((observable, oldValue, newValue) -> {
//                AtomicInteger inc = new AtomicInteger();
//                if(newValue.doubleValue() < 425D) {
//                    newChild.getChildren().forEach( f -> {
//                        newChild.getColumnConstraints().clear();
//                        GridPane.setConstraints(
//                                f,0,
//                                inc.getAndIncrement(),1, 1,
//                                HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES
//                        );
//                    });
//                } else {
//                    newChild.getChildren().forEach( f -> {
//                        newChild.getRowConstraints().clear();
//                        GridPane.setConstraints(f, inc.getAndIncrement(),0,1, 1,
//                                HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES
//                        );
//                    });
//                }
//            });
//        });


    }

    public void setControl(Control control,  String about, Selector... selectors) {
        setControl(control, null, selectors);
    }

    public void setControl(Control control, Parent parent, String about, Selector... selectors) {

        title.setText(control.getClass().getSimpleName());

        this.about.setText(about);

//        this.tabs.getTabs().remove(1, this.tabs.getTabs().size());

        if(parent != null)
            skin.setContent(parent);

        aside.clear();
        aside.setControl(control);

        for (Selector selector : selectors) {
            selector.setAside(aside);
            aside.addSelectors(selector);
        }

        boxControl.getChildren().clear();
        boxControl.getChildren().add(control);

        body.setLeft(aside);
    }


    @Override
    public List<ChangeListener<Number>> getListeners() {
        return Collections.singletonList(resizeLayout);
    }
}
