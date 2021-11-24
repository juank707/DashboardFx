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
package io.github.gleidson28.global.skin;

import io.github.gleidson28.global.skin.icon.Icons;
import io.github.gleidson28.global.skin.textField.ActionButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/11/2021
 */
public class GNPasswordFieldSkin extends GNTextFieldSkin {

    private boolean shouldMaskText = true;

    public GNPasswordFieldSkin(PasswordField textField) {
        super(textField);

        actionButtonTypeProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(ActionButtonType.VIEWER)) {
                getActionIcon().setContent(Icons.VIEWER);
                createViewAction();
            }
        });

    }

    private void createViewAction() {
        getActionButton().setOnMouseClicked(event -> {
            TextField textField = getSkinnable();

            if(shouldMaskText) {
                getActionIcon().setContent(Icons.VIEWER_OFF);
                shouldMaskText = false;
            } else {
                getActionIcon().setContent(Icons.VIEWER);
                shouldMaskText = true;
            }
            textField.setText(textField.getText());

            textField.end();

        });


    }

    @Override
    protected String maskText(String txt) {

        if (getSkinnable() instanceof PasswordField && shouldMaskText) {
            int n = txt.length();
            StringBuilder passwordBuilder = new StringBuilder(n);
            for (int i = 0; i < n; i++) {
                passwordBuilder.append(BULLET);
            }
            return passwordBuilder.toString();
        } else {

            return txt;
        }
    }
}
