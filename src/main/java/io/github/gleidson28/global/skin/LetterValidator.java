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

import javafx.scene.control.TextField;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class LetterValidator implements ValidatorBase {

    private TextField textField;
    private String errorMessage;

    public LetterValidator(TextField textField, String errorMessage) {
        this.textField = textField;
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean validate() {

        GNTextFieldSkin skin = (GNTextFieldSkin) textField.getSkin();

        if(!textField.getText().matches("[^0-9]")) {
//            skin.setErrorVisible(true);
//            skin.setErrorMessage(errorMessage);
            return true;
        } else {
//            skin.setErrorVisible(false);
            return false;
        }
    }

}
