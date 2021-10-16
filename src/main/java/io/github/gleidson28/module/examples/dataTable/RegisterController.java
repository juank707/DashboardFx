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
package io.github.gleidson28.module.examples.dataTable;

import com.gn.control.GNMonetaryField;
import io.github.gleidson28.App;
import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.converters.MonetaryStringConverter;
import io.github.gleidson28.global.creators.DrawerCreator;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.enhancement.CrudView;
import io.github.gleidson28.global.enhancement.FluidView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.popup.DashPopup;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/09/2020
 */
public class RegisterController implements Initializable, FluidView, CrudView {

    @FXML private GridPane form;
    @FXML private VBox info;
    @FXML private BorderPane content;
    @FXML private VBox firstColumn;
    @FXML private VBox secondColumn;
    @FXML private StackPane boxAvatar;
    @FXML private HBox formBox;

    @FXML private TextField tfName;
    @FXML private TextField tfLastName;
    @FXML private Label errorName;
    @FXML private Label errorLastName;

    @FXML private Label nameLength;
    @FXML private Label lastNameLength;

    @FXML private GNMonetaryField tfPrice;

    @FXML private Text teamsText;
    @FXML private Rating rating;
    @FXML private ToggleGroup status;
    @FXML private GNAvatarView avatarView;


    private Professional professional = new Professional();

    private final ConfigButton config = new ConfigButton(event -> {
        try {
            openInfo();
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    });

    @Override
    public void relocate(double width) {

        if(width < 1222) {
            form.getColumnConstraints().clear();
            GridPane.setConstraints(firstColumn, 0, 0, 1,
                    1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
            GridPane.setConstraints(secondColumn, 0, 1,
                    1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);

        } else {
            form.getRowConstraints().clear();
            GridPane.setConstraints(firstColumn, 0, 0, 1,
                    1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
            GridPane.setConstraints(secondColumn, 1, 0, 1,
                    1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
        }

        if(width < 700) {

            content.getChildren().remove(info);
            content.setTop(info);
            content.setCenter(formBox);
            content.setRight(null);
        } else if(width < 1000) {
            content.getChildren().remove(info);
            content.setCenter(formBox);
            content.setRight(config);
        } else {
//                if(!content.getTop().equals(info))
            content.getChildren().remove(info);
            content.getChildren().remove(formBox);
            content.setLeft(info);
            content.setCenter(formBox);
            content.setRight(null);
//                content.getChildren().remove(config);
        }
    }

    private final ChangeListener<Number> resizeForm = (observable, oldValue, newValue) ->
            relocate(newValue.doubleValue());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfName.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String max = nameLength.getText().substring(nameLength.getText().indexOf("/"));
            String size = String.valueOf(newValue.intValue());
            nameLength.setText(size + max);
        });

        tfLastName.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String max = lastNameLength.getText().substring(lastNameLength.getText().indexOf("/"));
            String size = String.valueOf(newValue.intValue());
            lastNameLength.setText(size + max);
        });
    }

    @Override
    public void onEnter() {
        App.INSTANCE.getDecorator().widthProperty().addListener(resizeForm);
    }

    @Override
    public void onExit() {
        App.INSTANCE.getDecorator().widthProperty().removeListener(resizeForm);
    }

    @FXML
    private void openInfo() throws NavigationException {
        DrawerCreator.INSTANCE.createDrawerRight(info);
        StackPane.setMargin(info, new Insets(30, 0, 0, 0));
    }

    @FXML
    private void openAvatarPopup() throws NavigationException {
        if(App.INSTANCE.getDecorator().getWidth() < 800) {
            ViewManager.INSTANCE.setContent("avatars_content");
            DrawerCreator.INSTANCE.closePopup();
        } else {
            DashPopup popup = new DashPopup(
                    ViewManager.INSTANCE.getRoot("avatars_content")
            );
            if(DrawerCreator.INSTANCE.isShow())
                popup.showTopLeft(boxAvatar);
            else popup.showTopRight(boxAvatar);
        }
    }

    @FXML
    private void back() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_index");
    }

    @Override
    public void setModel(Model model) {
        this.professional = (Professional) model;

        // Name
        tfName.textProperty().bindBidirectional(professional.nameProperty());
        errorName.visibleProperty().bind(
                professional.nameValidatorProperty().not());

        // Last Name
        tfLastName.textProperty().bindBidirectional(professional.lastNameProperty());
        errorLastName.visibleProperty().bind(professional
                .lastNameValidatorProperty().not());

        tfPrice.textProperty().bindBidirectional(professional.priceProperty(), new MonetaryStringConverter());

        teamsText.textProperty().bindBidirectional(professional.teamsProperty());
        rating.ratingProperty().bindBidirectional(professional.ratingProperty());

        professional.setStatus(Status.convertString(
                ((RadioButton) status.getSelectedToggle()).getText()));

        if(professional.getAvatar() != null)
            setAvatar(professional.getAvatar());
        else setAvatar(new Avatar("theme/img/avatars/man@400.png"));

    }

    void setAvatar(Avatar image) {
        professional.setAvatar(image);
        avatarView.setImage(image);
    }

    @FXML
    private void save() throws NavigationException  {

        if(this.professional.isValid()) {
            if(this.professional.getId() == 0) new ProfessionalPresenter().save(professional);
            else new ProfessionalPresenter().edit(professional);

            back();
        }
    }
}

class ConfigButton extends Button {

    public ConfigButton(EventHandler<ActionEvent> event) {

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        SVGPath icon = new SVGPath();
        icon.setContent("M19.14,12.94c0.04-0.3,0.06-0.61,0.06-0.94c0-0.32-0.02-0.64-0.07-0.94l2.03-1.58c0.18-0.14,0.23-0.41,0.12-0.61 l-1.92-3.32c-0.12-0.22-0.37-0.29-0.59-0.22l-2.39,0.96c-0.5-0.38-1.03-0.7-1.62-0.94L14.4,2.81c-0.04-0.24-0.24-0.41-0.48-0.41 h-3.84c-0.24,0-0.43,0.17-0.47,0.41L9.25,5.35C8.66,5.59,8.12,5.92,7.63,6.29L5.24,5.33c-0.22-0.08-0.47,0-0.59,0.22L2.74,8.87 C2.62,9.08,2.66,9.34,2.86,9.48l2.03,1.58C4.84,11.36,4.8,11.69,4.8,12s0.02,0.64,0.07,0.94l-2.03,1.58 c-0.18,0.14-0.23,0.41-0.12,0.61l1.92,3.32c0.12,0.22,0.37,0.29,0.59,0.22l2.39-0.96c0.5,0.38,1.03,0.7,1.62,0.94l0.36,2.54 c0.05,0.24,0.24,0.41,0.48,0.41h3.84c0.24,0,0.44-0.17,0.47-0.41l0.36-2.54c0.59-0.24,1.13-0.56,1.62-0.94l2.39,0.96 c0.22,0.08,0.47,0,0.59-0.22l1.92-3.32c0.12-0.22,0.07-0.47-0.12-0.61L19.14,12.94z M12,15.6c-1.98,0-3.6-1.62-3.6-3.6 s1.62-3.6,3.6-3.6s3.6,1.62,3.6,3.6S13.98,15.6,12,15.6z");
        this.setGraphic(icon);
        icon.setStyle("-fx-fill : white;");
        this.setStyle("-fx-background-radius : 100 0 0 100");
        this.setOnAction(event);

    }
}
