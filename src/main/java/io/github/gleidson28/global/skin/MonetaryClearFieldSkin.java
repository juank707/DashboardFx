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
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;

import java.text.DecimalFormat;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  12/11/2021
 */
public class MonetaryClearFieldSkin extends ClearableSkin {

    public MonetaryClearFieldSkin(TextField textField) {
        super(textField);

        textField.getStyleClass().add("gn-monetary-field-clear");

        DecimalFormat ds = (DecimalFormat) DecimalFormat.getInstance();
        textField.setText("0" + ds.getDecimalFormatSymbols().getMonetaryDecimalSeparator() + "00");

        textField.setOnKeyPressed(event -> {

            DecimalFormat s = (DecimalFormat) DecimalFormat.getInstance();

            char group = s.getDecimalFormatSymbols().getGroupingSeparator();
            char decimal = s.getDecimalFormatSymbols().getMonetaryDecimalSeparator();

            if (event.getText().matches("[0-9]") ) {

                if (textField.getText().charAt(0) == '0') {

                    if (textField.getText().charAt(textField.getLength() - 1) == '0') {
                        textField.deleteText(textField.getLength() - 1, textField.getLength());
                    } else if (textField.getText().charAt(textField.getLength() - 2) == '0') {
                        textField.deleteText(textField.getLength() - 2, textField.getLength() - 1);
                    } else if (textField.getText().charAt(textField.getLength() - 4) == '0') {
                        textField.deleteText(0, 2);
                        textField.insertText(1, String.valueOf(decimal));
                    }
                } else {
                    if (textField.getText().length() > 2) {

                        String value = textField.getText();
                        value = value.replaceAll("[^0-9]", "");

                        String cent = value.substring(value.length() - 1);
                        String middle = value.substring(0, value.length() - 1);


                        StringBuilder build = new StringBuilder(middle);
                        for (int i = middle.length(); i > 3; i -= 3) {
                            build.insert(i - 3, group);
                        }

                        String all;

                        if (build.toString().length() > 0) all = build.toString() + decimal + cent;
                        else all = build.toString() + cent;

                        textField.setText(all);
                    } else {

                        String value = textField.getText().replaceAll("[^0-9]", "");
                        textField.setText(value);
                    }
                }

                textField.positionCaret(textField.getLength());

            } else if (event.getCode().equals(KeyCode.BACK_SPACE) ) {

                if ( textField.getLength() <= 4) {

                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    String cent = value.substring(0, 3);

                    textField.setText("0" + decimal + cent);

                } else if (textField.getText().length() > 2) {

                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");

                    String cent = value.substring(value.length() - 3);
                    String middle = value.substring(0, value.length() - 3);

                    StringBuilder build = new StringBuilder(middle);
                    for (int i = middle.length(); i > 3; i -= 3) {
                        build.insert(i - 3, group);
                    }

                    String all;

                    if (build.toString().length() > 0) all = build.toString() + decimal + cent;
                    else all = build.toString() + cent;
                    textField.setText(all);
                } else {
                    String value = textField.getText().replaceAll("[^0-9]", "");
                    textField.setText(value);
                }
                textField.positionCaret(textField.getLength());
            } else {
                event.consume();
            }
        });


        textField.setTextFormatter(new TextFormatter<>(change -> {

            if(!change.getControlNewText().isEmpty() ) {
                String test = change.getControlNewText().substring(
                        change.getControlNewText().length() - 1
                );

                if (test.matches("[.]")) return null;
                if (test.matches("[,]")) return null;
            }

            if(change.getText().matches("[^0-9.,]") ) {
                return null;
            } else
                return change;

        }));
    }

    @Override
    void mouseReleased() {
        getTextField().setText("0" +
                ((DecimalFormat) DecimalFormat.getInstance())
                        .getDecimalFormatSymbols().getMonetaryDecimalSeparator() +
                "00"
        ); // no null pointer
        getTextField().positionCaret(getTextField().getLength());
    }
}
