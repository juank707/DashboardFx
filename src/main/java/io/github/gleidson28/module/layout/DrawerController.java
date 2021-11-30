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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/10/2021
 */
public class DrawerController {

    private ScrollPane  scroll;
    private VBox        drawerBox;
    private VBox        drawerContent;
    private HBox        searchBox;
    private ToggleGroup group;
    private TextField   search;

    private ObservableList<ToggleButton> items         = FXCollections.observableArrayList();
    private ObservableList<ToggleButton> designItems   = FXCollections.observableArrayList();
    private ObservableList<ToggleButton> controlsItems = FXCollections.observableArrayList();
    private ObservableList<ToggleButton> chartsItems   = FXCollections.observableArrayList();

    private FilteredList<ToggleButton> filteredList;

    public DrawerController(VBox drawerBox, HBox searchBox) {

        this.group = new ToggleGroup();
        this.drawerBox = drawerBox;

        scroll = (ScrollPane) drawerBox.lookup("#drawer-scroll");
        drawerContent = (VBox) scroll.getContent();

        search = (TextField) searchBox.lookup("#search");

        filteredList = new FilteredList<>( items, s -> true );
//
        search.textProperty().addListener( obs -> {

            String filter = search.getText();

            if ( filter == null || filter.length() == 0 ) {
                barInitial();

            } else {
                barFiltered(filter);

//
            }
        });
//
//            // This function can be included in one custom control for drawer..
//            // The stream is really util function..
//
        drawerContent.getChildren().stream()
                .filter(e -> e instanceof ToggleButton)
                .map(e -> (ToggleButton) e)
                .forEach(e -> {
                    e.setToggleGroup(group);
                    e.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                        if(DrawerCreator.INSTANCE.isShow()) {
                            DrawerCreator.INSTANCE.closePopup();
                        }
                    });
                    e.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                        if (e.isSelected()) {
                            event.consume();
                        }
                    });

                    ToggleButton toggleButton = new ToggleButton();
                    toggleButton.setText(e.getText());
                    toggleButton.setToggleGroup(group);
                    toggleButton.setOnMouseClicked(e.getOnMouseClicked());
                    toggleButton.setOnAction(e.getOnAction());
                    toggleButton.getStyleClass().setAll(e.getStyleClass());

//                    SVGPath iconClone = (SVGPath) e.getGraphic();
                    SVGPath iconClone = new SVGPath();
                    iconClone.setContent( ((SVGPath) e.getGraphic()).getContent());

                    toggleButton.setGraphicTextGap(e.getGraphicTextGap());
                    toggleButton.setGraphic(iconClone);

                    items.add(toggleButton);

                });

        drawerContent.getChildren()
                .stream()
                .filter(e -> e instanceof TitledPane)
                .map(e -> (TitledPane) e).filter(e -> e.getContent() instanceof VBox)
                    .forEach(e -> ((VBox) e.getContent()).getChildren()
                .stream()
                .filter(c -> c instanceof ToggleButton)
                .map(c -> (ToggleButton) c)
                .forEach(c -> {



                    c.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                        if (c.isSelected()) event.consume();

                        if (DrawerCreator.INSTANCE.isShow()) {
                            DrawerCreator.INSTANCE.closePopup();
                        }

                    });

                    c.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                        if (c.isSelected()) event.consume();
                    });

                    c.setToggleGroup(group);

                    SVGPath icon = new SVGPath();
                    icon.setContent("M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z");

                    c.setGraphic(icon);
                    c.setContentDisplay(ContentDisplay.TEXT_ONLY);
                    icon.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
//
                    c.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            c.setContentDisplay(ContentDisplay.RIGHT);
                        }
                        else c.setContentDisplay(ContentDisplay.TEXT_ONLY);
                    });

                    this.group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            if (((VBox) e.getContent()).getChildren().contains(newValue)) {
                                e.getStyleClass().addAll("menu-selected");
                            } else {
                                e.getStyleClass().removeAll("menu-selected");
                            }
                        }
                    });

                    ToggleButton toggleButton = new ToggleButton();
                    toggleButton.setText(c.getText());
                    toggleButton.setToggleGroup(group);
                    toggleButton.setOnMouseClicked(c.getOnMouseClicked());
                    toggleButton.setOnAction(c.getOnAction());
                    toggleButton.getStyleClass().setAll(c.getStyleClass());
                    toggleButton.setContentDisplay(c.getContentDisplay());
                    toggleButton.setNodeOrientation(NodeOrientation.INHERIT);
                    toggleButton.setPrefWidth(this.drawerBox.getPrefWidth());


                    items.add(toggleButton);
                }));

        }

        public ObservableList<ToggleButton> getItems() {
            return items;
        }

        private void barFiltered(String filter){
            filteredList.setPredicate(s -> s.getText().toUpperCase().contains(filter.toUpperCase()));
            scroll.setContent(filter(filteredList));
        }

        private VBox filter(ObservableList<ToggleButton>  nodes) {

            VBox vBox = new VBox();
            vBox.getStyleClass().add("drawer-content");
            vBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            vBox.setAlignment(Pos.TOP_LEFT);
            for (ToggleButton node : nodes){
                if (node.getGraphic() == null) node.setContentDisplay(ContentDisplay.TEXT_ONLY);

//                node.setPadding(new Insets(10D));
//                VBox.setMargin(node, new Insets(5D));
                node.setStyle("-fx-font-weight : bold;");
                node.setAlignment(Pos.CENTER_LEFT);
                node.setPrefWidth(300D);
            }
            vBox.getChildren().setAll(nodes);
            return vBox;
        }

        private void barInitial(){
            filteredList.setPredicate(s -> true);
            scroll.setContent(this.drawerContent);

        }
}
