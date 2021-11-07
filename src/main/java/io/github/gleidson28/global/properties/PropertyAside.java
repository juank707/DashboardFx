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

import io.github.gleidson28.global.creators.SnackBarCreator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/11/2021
 */
public class PropertyAside extends ScrollPane {

    private final VBox body = new VBox();
    private final VBox customSelectors = new VBox();

    private SVGPath  icon       = new SVGPath();
    private Label    title      = new Label();

    private TextArea styleArea  = new TextArea();
    private TextArea javaArea  = new TextArea();
    private TextArea fxmlArea  = new TextArea();

    private Control control;

//    private final ObservableList<Selector> customSelectors = FXCollections.observableArrayList();

    private final ObservableList<String> styleClass = FXCollections.observableArrayList();

    private enum BoxType {
        STYLE, JAVA, FXML
    }

    public PropertyAside() {
        this(null);
    }

    public PropertyAside(Control control) {

        this.control = control;

        styleArea.setEditable(false);
        javaArea.setEditable(false);
        fxmlArea.setEditable(false);

        icon.setContent(" M2.53 19.65l1.34.56v-9.03l-2.43 5.86c-.41 1.02.08 2.19 1.09 2.61zm19.5-3.7L17.07 3.98c-.31-.75-1.04-1.21-1.81-1.23-.26 0-.53.04-.79.15L7.1 5.95c-.75.31-1.21 1.03-1.23 1.8-.01.27.04.54.15.8l4.96 11.97c.31.76 1.05 1.22 1.83 1.23.26 0 .52-.05.77-.15l7.36-3.05c1.02-.42 1.51-1.59 1.09-2.6zm-9.2 3.8L7.87 7.79l7.35-3.04h.01l4.95 11.95-7.35 3.05z M5.88 19.75c0 1.1.9 2 2 2h1.45l-3.45-8.34v6.34z");

        title.setGraphic(icon);
        title.getStyleClass().add("h3");

        title.setText("Style");
        title.setGraphicTextGap(10D);

        icon.setStyle("-fx-fill : -text-color;");

//        BorderPane.setMargin(this, new Insets(20));
        this.setPadding(new Insets(20));

        body.getChildren().add(title);
        body.getChildren().add(1, customSelectors);
        body.getChildren().addAll(createSeparator(), createBox("StyleClass", "Your StyleClass", BoxType.STYLE));
        body.getChildren().addAll(createSeparator(), createBox("Java", "CheckBox checkBox = new CheckBox(\"CheckBox\");", BoxType.JAVA));
        body.getChildren().addAll(createSeparator(), createBox("FXML", "<CheckBox text=\"CheckBox\">", BoxType.FXML));

        body.setSpacing(20);

        this.setContent(body);

        this.setPrefWidth(250);

        this.setFitToWidth(true);
        this.setFitToHeight(true);

        styleClass.addListener((ListChangeListener<String>) c -> {
            if(c.next()) {
//                if(c.wasAdded())
                    read();
                if(c.getList().size() < 1) {
                    reset();
                }
            }
        });

    }

    public void addSelectors(Selector... selectors) {
        customSelectors.getChildren().addAll(selectors);

    }

    public void addStyleClass(String _class) {
        styleClass.addAll(_class);
    }

    private void removeStyleClass(List _class) {
        styleClass.removeAll(_class);
    }

    public void removeStyleClass(String _class) {
        styleClass.removeAll(_class);
    }

    public Control getControl() {
        return this.control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public void clear() {
        customSelectors.getChildren().clear();

        styleClass.clear();
        styleArea.setText(null);
        javaArea.setText(null);
        fxmlArea.setText(null);
    }

    private VBox createBox(String title, String prompt, BoxType type) {
        return createBox(title, prompt, type, null);
    }

    private VBox createBox(String title, String prompt, BoxType type, String text) {
        VBox box = new VBox();

        box.setMinHeight(100);

        StackPane content = new StackPane();

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("h5");

        box.setPrefSize(300, 300);

        box.setPadding(new Insets(5D));
        box.setSpacing(10D);

        TextArea boxArea = new TextArea();
        boxArea.setPromptText(prompt);
        boxArea.setText(text);

        Button btnCopy = new Button();
        btnCopy.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnCopy.getStyleClass().add("btn-flat");
        SVGPath iconCopy = new SVGPath();
        iconCopy.setContent("M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 " +
                ".9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z");
        iconCopy.getStyleClass().add("icon");

        btnCopy.setGraphic(iconCopy);

        TextArea area = null;

        switch (type) {
            case STYLE:
                content.getChildren().addAll(styleArea, btnCopy);
                styleArea.setPromptText(prompt);
                styleArea.setText(text);
                area = styleArea;
                break;
            case JAVA:
                content.getChildren().addAll(javaArea, btnCopy);
                javaArea.setPromptText(prompt);
                javaArea.setText(text);
                area = javaArea;
                break;
            case FXML:
                content.getChildren().addAll(fxmlArea, btnCopy);
                fxmlArea.setPromptText(prompt);
                fxmlArea.setText(text);
                area = fxmlArea;
                break;
        }


        TextArea finalArea = area;
        btnCopy.setOnMouseClicked(event -> {
            Clipboard clipboard  = Clipboard.getSystemClipboard();
            ClipboardContent c = new ClipboardContent();
            c.putString(finalArea.getText());
            clipboard.setContent(c);
            finalArea.selectAll();

            SnackBarCreator.INSTANCE.createSnackBar("Copied to clipboard.");

        });

        btnCopy.setTooltip(new Tooltip("Copy"));

        box.getChildren().addAll(lblTitle, content);

        StackPane.setAlignment(btnCopy, Pos.BOTTOM_RIGHT);

        return box;
    }

    public void reset() {
        styleArea.setText(null);
        javaArea.setText(null);
        fxmlArea.setText(null);
    }

    private void read() {

        reset();

        javaArea.setText(
                "CheckBox checkBox = new CheckBox(\"CheckBox\");" +
                        "\ncheckBox.getStyleClass()\n.addAll("
        );

        for (String newValue : styleClass) {
            styleArea.appendText(newValue);
            javaArea.appendText( "\"" + newValue + "\n");
            fxmlArea.appendText( "<CheckBox text=\"CheckBox\">\n" +
                    "\t<styleClass>\n" +
                    "\t\t <String fx:value=\""+ newValue.replace(",", "") + "\" />\n");
        }

        if(styleArea.getText() != null)
            styleArea.replaceText(styleArea.getText().lastIndexOf(","), styleArea.getText().length(), "");

        if(javaArea.getText() != null && javaArea.getText().contains(",")) {
            javaArea.replaceText(javaArea.getText().lastIndexOf(","), javaArea.getText().length(), "");
            javaArea.appendText("\");");
        }

        if(fxmlArea.getText() != null) {
            fxmlArea.appendText("\t</styleClass>\n" +
                    "</CheckBox>");
        }

    }

    private VBox createSeparator() {
        VBox separator = new VBox();

        separator.getStyleClass().addAll("border");
        separator.setStyle("-fx-background-color : -light-gray;");

        separator.setPrefHeight(1);
        return separator;
    }
}
