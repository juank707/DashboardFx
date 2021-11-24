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

import io.github.gleidson28.App;
import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.converters.MoneySimpleStringConverter;
import io.github.gleidson28.global.creators.DrawerCreator;
import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.enhancement.CrudView;
import io.github.gleidson28.global.enhancement.ActionView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.popup.Popup;
import io.github.gleidson28.global.skin.LetterValidator;
import io.github.gleidson28.global.skin.SizeValidator;
import io.github.gleidson28.global.skin.ValidatorBase;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/09/2020
 */
public class RegisterController implements Initializable, ActionView, CrudView {

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

    @FXML private TextField tfPrice;

    @FXML private Text teamsText;
    @FXML private Text skillsText;

    @FXML private Rating rating;
    @FXML private ToggleGroup status;
    @FXML private GNAvatarView avatarView;
    @FXML private Button btnTeams;
    @FXML private Button btnSkills;


    private Professional professional = new Professional();

    private final ConfigButton config = new ConfigButton(event -> {
        try {
            openInfo();
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    });


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

    List<ValidatorBase> validators;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validators =  Arrays.asList(
                    new SizeValidator(tfName, "The name of " + tfName.getText() + " is not valid", 28),
                    new LetterValidator(tfName, "The size not contains necessary letters")
        );
//
//        tfName.lengthProperty().addListener((observable, oldValue, newValue) -> {
//            String max = nameLength.getText().substring(nameLength.getText().indexOf("/"));
//            String size = String.valueOf(newValue.intValue());
//            nameLength.setText(size + max);
//        });

//        tfLastName.lengthProperty().addListener((observable, oldValue, newValue) -> {
//            String max = lastNameLength.getText().substring(lastNameLength.getText().indexOf("/"));
//            String size = String.valueOf(newValue.intValue());
//            lastNameLength.setText(size + max);
//        });

//        tfName.setSkin(new GNTextFieldSkin(tfName, professional, "Error on this label"));
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
            Popup popup = new Popup(
                    ViewManager.INSTANCE.getRoot("avatars_content")
            );
            if(DrawerCreator.INSTANCE.isShow())
                popup.showTopLeft(boxAvatar);
            else popup.showTopRight(boxAvatar);
        }
    }

    @FXML
    private void back() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_index", professional);
    }

    @Override
    public void setModel(Model model) {
        this.professional = (Professional) model;

        // Name
        tfName.textProperty().bindBidirectional(professional.nameProperty());
//        errorName.visibleProperty().bind(
//                professional.nameValidatorProperty().not());

        // Last Name
        tfLastName.textProperty().bindBidirectional(professional.lastNameProperty());
//        errorLastName.visibleProperty().bind(professional
//                .lastNameValidatorProperty().not());

        tfPrice.textProperty().bindBidirectional(professional.priceProperty(), new MoneySimpleStringConverter());

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

        boolean valid = false;

        for (ValidatorBase val : validators) valid = val.validate();

        System.out.println(valid);

//        if(this.professional.isValid()) {
//
//            if(this.professional.getId() == 0) {
//
//                new ProfessionalPresenter().save(professional);
//
////                DrawerCreator.INSTANCE.createTrayNotification(
////                        "Save Successful",
////                        "The professional " + professional.getName() + " saved."
////                );
//
//                Button btnOk = new Button("OK");
//                Button btnCancel = new Button("Cancel");
//                btnOk.getStyleClass().add("btn-flat");
//                btnCancel.getStyleClass().add("btn-flat");
//
//
//                DialogCreator.INSTANCE.createDialog(
//                        "Save Successful",
//                        "The professional " + professional.getName() + " saved.",
//                        btnOk
//                );
//
//
//            }
//            else new ProfessionalPresenter().edit(professional);
////            DrawerCreator.INSTANCE.createTrayNotification(
////                    "Edit Successful",
////                    "The professional " + professional.getName() + " edited."
////            );
//            Button btnOk = new Button("OK");
//            Button btnCancel = new Button("Cancel");
//            btnOk.getStyleClass().add("btn-flat");
//            btnCancel.getStyleClass().add("btn-flat");
//
//
//            DialogCreator.INSTANCE.createDialog(
//                    "Edit Successful",
//                    "The professional " + professional.getName() + " edited.",
//                    btnOk
//            );
//
//            back();
//        }
    }

    @FXML
    private void openTeamPopup() {
        Popup p = new Popup(
                createRootPopup(FXCollections.observableArrayList(
                        "Creative", "Support", "Engineer"), teamsText));
        p.showTopRight(btnTeams);

    }

    @FXML
    private void openSkillPopup() {
        Popup p = new Popup(
                createRootPopup(FXCollections.observableArrayList(
                        "PHP", "Android", "UX Designer", "Java"),
                        skillsText));
        p.showTopRight(btnSkills);

    }

    private StackPane createRootPopup(ObservableList<String> items, Text text) {
        StackPane root = new StackPane();

        root.setMinHeight(200D);
        root.setPrefSize(250, 370);
        root.setPadding(new Insets(20));

        root.getChildren().add(createBody(items, text));
        return root;
    }

    private VBox createBody(ObservableList<String> items, Text text) {
        VBox body = new VBox();

        body.getChildren().addAll(
                createTitle("Teams"),
                createContent(items, text)
        );

        return body;
    }

    private VBox createTitle(String text) {
        VBox content = new VBox();

        content.setAlignment(Pos.CENTER);
        content.getStyleClass().addAll("border-b-1", "border");
        content.setMinHeight(48);
        content.setPrefSize(274,61);

        Label title = new Label(text);

        title.getStyleClass().add("h4");
        title.setStyle("-fx-font-weight : bold;");
        content.getChildren().addAll(title);
        return content;
    }

    private VBox createContent(ObservableList<String> items, Text text) {

        VBox root = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);

        // Price
        HBox controls = new HBox();
        controls.setMinHeight(40);

        TextField search = new TextField();
        search.setPromptText("Teams");
        search.getStyleClass().addAll("field-outlined");
        search.setMinHeight(40);
        search.setPrefHeight(40);
        controls.getChildren().addAll(search);
        HBox.setHgrow(search, Priority.ALWAYS);

        HBox buttons = new HBox();
        Button btnPlus = new Button();
        btnPlus.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnPlus.getStyleClass().addAll("round", "btn-mint");
        btnPlus.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        btnPlus.setStyle("-fx-pref-height : 30px; -fx-pref-width : 30px;");

        SVGPath iconPlus = new SVGPath();
        iconPlus.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
        iconPlus.setStyle("-fx-fill : white;");
        btnPlus.setGraphic(iconPlus);

        SVGPath iconP = new SVGPath();
        iconP.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
        iconP.setStyle("-fx-fill : white;");

        Button btnDelete = new Button();
        btnDelete.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnDelete.getStyleClass().addAll("round", "btn-danger");
        btnDelete.setMinSize(30, 30);
        btnDelete.setStyle("-fx-pref-height : 30px; -fx-pref-width : 30px;");
        btnDelete.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        SVGPath iconDelete = new SVGPath();
        iconDelete.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
        iconDelete.setStyle("-fx-fill : white;");
        btnDelete.setGraphic(iconDelete);
        buttons.setSpacing(10);

        ListView<String> lvTeams = new ListView<>();

//        lvTeams.getItems().addAll("Creative", "Support", "Engineer");
        lvTeams.getItems().addAll(items);

        btnPlus.setOnMouseClicked(event -> {
            if(!search.getText().isEmpty() || search.getText() != null) {
                lvTeams.getItems().add(search.getText());
            }
        });

        buttons.getChildren().addAll(btnPlus, btnDelete);
        controls.getChildren().addAll(buttons);

        VBox.setMargin(controls, new Insets(10,0,10,10));
        ScrollPane content = new ScrollPane();
        content.setFitToWidth(true);
        content.setFitToHeight(true);

        content.setContent( lvTeams );

        Button add = new Button("Add");
        add.setMinHeight(40);
        add.setPrefWidth(100);
        add.setStyle("-fx-font-weight : bold;");

        add.setOnMouseClicked(event -> {

            if(lvTeams.getSelectionModel().getSelectedItem() != null) {
                if (text.getText().isEmpty()) {
                    text.setText(text.getText().concat(lvTeams.getSelectionModel().getSelectedItem()));
                } else {
                    text.setText(text.getText().concat(", " +
                            lvTeams.getSelectionModel().getSelectedItem()));
                }
            }
        });

        VBox.setMargin(controls, new Insets(10,0,10,10));

        add.setGraphic(iconP);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(controls, content, add);
        return root;
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
