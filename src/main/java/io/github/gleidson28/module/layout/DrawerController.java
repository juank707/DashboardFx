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

import io.github.gleidson28.global.creators.PopupCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.stream.Collectors;

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

        Button clear = (Button) searchBox.lookup("#btnSearch");
        search = (TextField) searchBox.lookup("#search");

        SVGPath searchIcon = new SVGPath();

        searchIcon.setContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 " +
                "5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 " +
                "5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        clear.setGraphic(searchIcon);
        searchIcon.setStyle("-fx-fill : -text-color;");

        clear.setOnAction(event -> search.clear());
//
        filteredList = new FilteredList<>( items, s -> true );
//
        search.textProperty().addListener( obs -> {

            String filter = search.getText();

            if ( filter == null || filter.length() == 0 ) {
                barInitial();
                clear.setMouseTransparent(true);
                searchIcon.setContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
            } else {
                barFiltered(filter);
                clear.setMouseTransparent(false);
                searchIcon.setContent("M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z");
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
//                            PopupCreator.INSTANCE.closePopup();
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
                    .map(e -> (TitledPane) e)
                    .forEach(e -> {
                        if (e.getContent() instanceof VBox) {
                            ((VBox) e.getContent()).getChildren()
                                    .stream()
                                    .filter(c -> c instanceof ToggleButton)
                                    .map(c -> (ToggleButton) c)
                                    .forEach(c -> {
                                        c.addEventFilter(MouseEvent.MOUSE_CLICKED, ev ->{
//                                        PopupCreator.INSTANCE.closePopup();
                                        });
                                        c.setToggleGroup(group);
                                        this.group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                                            if(newValue != null) {
                                                if (((VBox) e.getContent()).getChildren().contains(newValue)) {
                                                    e.getStyleClass().addAll("menu-selected");
                                                } else e.getStyleClass().removeAll("menu-selected");
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
                                    });
                        }
                    });

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
