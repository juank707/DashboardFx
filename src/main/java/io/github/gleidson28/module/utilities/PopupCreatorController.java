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
package io.github.gleidson28.module.utilities;

import io.github.gleidson28.global.creators.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/04/2020
 */
public class PopupCreatorController implements Initializable {

    @FXML private VBox bodyContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void openDrawerLeft(){
        DrawerCreator.INSTANCE.createDrawerLeft(new VBox());
    }

    @FXML
    private void openDrawerRight(){
        DrawerCreator.INSTANCE.createDrawerRight(new VBox());
    }

    @FXML
    private void createAlertInfo(){
        Button btnInfo = new Button("OK");
        btnInfo.getStyleClass().add("btn-info");
        btnInfo.setPrefWidth(100);

        AlertCreator.INSTANCE.createAlert(
                AlertType.INFO, "Alert Info",
                "You create a alert info",
                btnInfo
        );
    }

    @FXML
    private void createAlertError(){
        Button btnError = new Button("OK");
        btnError.getStyleClass().add("btn-danger");
        btnError.setPrefWidth(100);

        AlertCreator.INSTANCE.createAlert(
                AlertType.ERROR,
                "Alert Error",
                "You create a alert error.",
                btnError
        );
    }

    @FXML
    private void createAlertWarning(){
        Button btnWarning = new Button("OK");
        btnWarning.getStyleClass().add("btn-warning");
        btnWarning.setPrefWidth(100);

        AlertCreator.INSTANCE.createAlert(
                AlertType.WARNING,
                "Alert Error",
                "You create a alert error.",
                btnWarning
        );
    }

    @FXML
    private void createAlertDone(){
        Button btnDone = new Button("OK");
        btnDone.getStyleClass().add("btn-success");
        btnDone.setPrefWidth(100);

        AlertCreator.INSTANCE.createAlert(
                AlertType.DONE,
                "Alert Error",
                "You create a alert error." +
                        "\"Lorem ipsum dolor color." +
                        "\"Lorem ipsum dolor color." +
                        "\"Lorem ipsum dolor color." +
                        "\"Lorem ipsum dolor color.\"Lorem ipsum dolor color." +
                        "\"Lorem ipsum dolor color.\"Lorem ipsum dolor color." +
                        "",
                btnDone
        );
    }

    @FXML
    private void createDialog(){
        Button btnOk = new Button("OK");
        Button btnCancel = new Button("Cancel");
        btnOk.getStyleClass().add("btn-flat");
        btnCancel.getStyleClass().add("btn-flat");


        DialogCreator.INSTANCE.createDialog(
                "This is a simple dialog",
                "Lorem ipsum dolor color.",

                btnOk, btnCancel
        );

    }

    @FXML
    private void createSnackSuccess() {
        SnackBarCreator.INSTANCE.createSnackBar("SnackBar Success",
                SnackType.SUCCESS);
    }

    @FXML
    private void createSnackWarning() {
        SnackBarCreator.INSTANCE.createSnackBar("SnackBar Warning",
                SnackType.WARNING);
    }

    @FXML
    private void createSnackDanger() {
        SnackBarCreator.INSTANCE.createSnackBar("SnackBar Danger",
                SnackType.ERROR);
    }

    @FXML
    private void createSnackInfo() {
        SnackBarCreator.INSTANCE.createSnackBar("SnackBar Info",
                SnackType.INFO);
    }
}
