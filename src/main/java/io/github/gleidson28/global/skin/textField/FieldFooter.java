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
package io.github.gleidson28.global.skin.textField;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/11/2021
 */
public class FieldFooter extends GridPane {

    private final Text count = new Text("0/0");

    private final TextFlow textFlow = new TextFlow();
    private final Text message = new Text();


    public FieldFooter() {

        message.getStyleClass().add("text-grapefruit");

        message.setText("Error on this label, please correct the value for yesterday.");
        textFlow.getChildren().add(message);
        message.getStyleClass().add("");

        getChildren().addAll(textFlow);

        GridPane.setConstraints(textFlow, 0, 0, 1, 1,
                HPos.LEFT, VPos.CENTER,
                Priority.SOMETIMES, Priority.SOMETIMES);

        textFlow.setVisible(false);

//        this.setGridLinesVisible(true);
    }

    public void setVisibleCount(boolean value) {
        if (value) {
            getChildren().add(count);
            count.setStyle("-fx-font-width : 10px;");
            GridPane.setConstraints(
                    count, 1, 0, 1, 1,
                    HPos.RIGHT, VPos.TOP, Priority.SOMETIMES, Priority.SOMETIMES);
        } else {
            getChildren().remove(count);
        }
    }

    public TextFlow getTextFlow() {
        return textFlow;
    }

    public void setErrorVisible(boolean value, String message) {
           textFlow.setVisible(value);
           this.message.setText(message);
    }

    public void setErrorVisible(boolean value) {
        textFlow.setVisible(value);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public Text getCount() {
        return this.count;
    }
}
