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

import io.github.gleidson28.App;
import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.plugin.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/06/2021
 */
public class AvatarsContentController implements Initializable {

    @FXML private ToggleGroup group;

    private FileChooser fileChooser;
    private RegisterController registerController;

    @FXML
    private void openChooser() throws FileNotFoundException {

        File file = fileChooser.showOpenDialog(App.INSTANCE.getDecorator().getWindow());

        if (file != null) {
            if(registerController == null) registerController = (RegisterController) ViewManager.INSTANCE.getController("professional_register");

            registerController.setAvatar(new Avatar(new FileInputStream(file), file.getPath()));

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {

                if(registerController == null) registerController = (RegisterController) ViewManager.INSTANCE.getController("professional_register");

                if(newValue instanceof ToggleButton) {

                    String path = "theme/img/avatars/" + ((ToggleButton) newValue).getId() + "@400.png";

                    registerController.setAvatar(new Avatar(path));
                }
            }
        });
    }
}
