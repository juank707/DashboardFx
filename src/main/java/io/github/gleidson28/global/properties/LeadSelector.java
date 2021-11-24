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

import io.github.gleidson28.global.skin.textField.ActionButtonType;
import io.github.gleidson28.global.skin.textField.LeadIconType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/11/2021
 */
public class LeadSelector extends Selector {

    private Control control;
    private PropertyAside aside;

    private final VBox          content = new VBox();
    private final ToggleGroup   group   = new ToggleGroup();

    public LeadSelector(Control control,  String... options) {
        this.control = control;
        addOptions(options);
    }

    public void addOptions(String... options) {

        Label title = new Label("TextField Options");
        title.getStyleClass().add("h5");
        getChildren().addAll(createSeparator(), title);

        VBox.setMargin(title, new Insets(10,0,0,0));

        VBox iconContent = new VBox();
        getChildren().addAll(iconContent);

        CheckBox checkLead = new CheckBox("Lead Icon");
        checkLead.setPadding(new Insets(10));
        getChildren().add(checkLead);
        iconContent.getChildren().add(checkLead);

        VBox actionContent = new VBox();
        getChildren().add(actionContent);
        CheckBox checkAction = new CheckBox("Action Button");
        checkAction.setPadding(new Insets(10));
        actionContent.getChildren().add(checkAction);
        ChoiceBox<ActionButtonType> selector = new ChoiceBox<>();
        selector.getStyleClass().add("field-filled");


        for (ActionButtonType v : ActionButtonType.values()) {
            selector.getItems().add(v);
        }

        checkAction.selectedProperty().addListener((observable, oldValue, newValue) -> {
                selector.setPrefWidth(250);

                if(newValue) {
                    selector.setMinHeight(40D);
                    actionContent.getChildren().add(selector);
                    selector.getSelectionModel().selectFirst();
                    addClass(selector.getSelectionModel().getSelectedItem().toString());
                    selector.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                        removeClass(oldValue1.toString());
                        addClass(newValue1.toString());
                    });
                } else  {
                    removeClass(selector.getSelectionModel().getSelectedItem().toString());
                    actionContent.getChildren().remove(selector);
                }
            });

            ChoiceBox<LeadIconType> leadSelector = new ChoiceBox<>();
            leadSelector.getStyleClass().add("field-filled");
            for (LeadIconType v : LeadIconType.values()) {
                leadSelector.getItems().add(v);
            }

            checkLead.selectedProperty().addListener((observable, oldValue, newValue) -> {
                leadSelector.setPrefWidth(250D);
                leadSelector.getStyleClass().add("field-filled");

                if (newValue) {

                    leadSelector.setMinHeight(40D);
                    iconContent.getChildren().add(leadSelector);
                    leadSelector.getSelectionModel().selectLast();

                    addClass(leadSelector.getSelectionModel().getSelectedItem().toString());
                    leadSelector.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                        removeClass(oldValue1.toString());
                        addClass(newValue1.toString());
                    });
                } else {

                    iconContent.getChildren().remove(leadSelector);
                    removeClass(leadSelector.getSelectionModel().getSelectedItem().toString());
                }
            });
    }

    private void addClass(String newValue) {

        control.getStyleClass().addAll(control.getProperties().get("prefix")  +
                newValue.toLowerCase()
        );

        aside.addStyleClass(control.getProperties().get("prefix")  +
                newValue.toLowerCase() + ","
        );
    }

    private void removeClass(String oldValue) {
        control.getStyleClass().removeAll(control.getProperties().get("prefix")
                + oldValue.toLowerCase());
        aside.removeStyleClass(control.getProperties().get("prefix")
                + oldValue.toLowerCase() + ",");
    }

    @Override
    public void setAside(PropertyAside aside) {
        this.aside = aside;
    }

    @Override
    public void reset() {
        this.control.setOnMouseEntered(null);
        this.control.setOnMouseExited(null);
    }
}
