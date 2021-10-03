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
package io.github.gleidson28.module.professional;

import com.gn.control.GNMonetaryField;
import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.dao.UserPresenter;
import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.enhancement.CrudView;
import io.github.gleidson28.global.enhancement.FluidView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.model.User;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.popup.DashPopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    @FXML private TextField txtName;
    @FXML private TextField txtLastName;

    @FXML private GNMonetaryField tfPrice;

    @FXML private StackPane boxAvatar;
    @FXML private GNAvatarView avatarView;

    @FXML private Text teamsText;
    @FXML private Text skillsText;

    @FXML private Button btnTeams;
    @FXML private Button btnSkills;

    @FXML private ToggleGroup status;

    @FXML private Rating rating;
    @FXML private Label lblRating;

    private Professional professional = new Professional();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblRating.textProperty().bind(
                Bindings.createStringBinding( () ->
                                String.valueOf(Math.round( rating.getRating() * 100.0) / 100.0),
                        rating.ratingProperty())
        );

        status.selectedToggleProperty().
                addListener((observable, oldValue, newValue) ->
                        professional.setStatus(Status.convertString( ((RadioButton) newValue).getText())));
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @FXML
    private void goRegister() throws NavigationException {
        ViewManager.INSTANCE.setContent("register");
    }

    @Override
    public void setModel(Model model) {

        this.professional = (Professional) model;

        txtName.textProperty().bindBidirectional(professional.nameProperty());
        txtLastName.textProperty().bindBidirectional(professional.lastNameProperty());
        tfPrice.valueProperty().bindBidirectional(professional.priceProperty());
        teamsText.textProperty().bindBidirectional(professional.teamsProperty());
        rating.ratingProperty().bindBidirectional(professional.ratingProperty());

        professional.setStatus(Status.convertString( ((RadioButton) status.getSelectedToggle()).getText()));


        if(professional.getAvatar() != null)
            setAvatar(professional.getAvatar());
        else setAvatar(new Avatar("theme/img/avatars/man@400.png"));

    }
    
    void setAvatar(Avatar image) {
        professional.setAvatar(image);
        avatarView.setImage(image);
    }

    @FXML
    private void returnView() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional");
    }

    @FXML
    private void save() throws NavigationException  {

        if(this.professional.isValid()) {
            if(this.professional.getId() == 0) new ProfessionalPresenter().save(professional);
            else new ProfessionalPresenter().edit(professional);

            returnView();
        }
    }

    @FXML
    private void openPopup() {
        DashPopup popup = new DashPopup(
                ViewManager.INSTANCE.getRoot("avatars_content"));
        popup.showTopRight(boxAvatar);
    }

    @FXML
    private void openTeamPopup() {
        DashPopup p = new DashPopup(
                createRootPopup(FXCollections.observableArrayList(
                        "Creative", "Support", "Engineer"), teamsText));
        p.showTopRight(btnTeams);

    }

    @FXML
    private void openSkillPopup() {
        DashPopup p = new DashPopup(
                createRootPopup(FXCollections.observableArrayList(
                        "PHP", "Android", "UX Designer", "Java"), skillsText));
        p.showTopRight(btnSkills);

    }

    private StackPane createRootPopup(ObservableList<String> items, Text text) {
        StackPane root = new StackPane();

        root.setMinHeight(200D);
        root.setPrefSize(350, 370);
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
                    text.setText(text.getText().concat(", " + lvTeams.getSelectionModel().getSelectedItem()));
                }

            }
        });

        add.setGraphic(iconP);

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(controls, content, add);

        return root;
    }


}
