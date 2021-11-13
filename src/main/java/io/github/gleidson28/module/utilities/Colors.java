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

import eu.hansolo.colors.ColorHelper;
import eu.hansolo.colors.MaterialDesign;
import io.github.gleidson28.App;
import io.github.gleidson28.global.creators.SnackBarCreator;
import io.github.gleidson28.global.creators.SnackType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/10/2018
 * Version 1.0
 */
public class Colors implements Initializable {

    private static final Pattern PATTERN    = Pattern.compile("(_[A]?[0-9]{2,3})");
    private static final Matcher MATCHER    = PATTERN.matcher("");
    private static final int     BOX_WIDTH  = 100;
    private static final int     BOX_HEIGHT = 60;

    private final ToggleGroup group = new ToggleGroup();

    @FXML private GridPane grid;
    @FXML private VBox boxColor;
    @FXML private TextField textColor;
    @FXML private TilePane tiles;

    private Properties properties = new Properties();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            InputStream input = new FileInputStream("app/colors.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return properties.getProperty(name);
//        config();

        createContent();
    }

    private void createContent() {
        tiles.getChildren().clear();

        properties.stringPropertyNames().forEach( f -> {

            String color = f.contains("/") ? f.substring(f.lastIndexOf("-")) : f;

            VBox child = new VBox();
            child.setPrefWidth(200);

            HBox headColor = new HBox();

            int tileWidth = 200;
            int tileHeight = 50;

            Pane left = new Pane();
            left.setPrefSize(tileWidth, tileHeight);
            left.setStyle("-fx-background-radius :  10 0 0 0; -fx-background-color : derive(" + color + ", -10%);");

            Pane center = new Pane();
            center.setPrefSize(tileWidth, tileHeight);
            center.setStyle("-fx-background-color : " + color + ";");
            Pane right = new Pane();
            right.setPrefSize(tileWidth, tileHeight);
            right.setStyle("-fx-background-radius : 0 10 0 0; -fx-background-color : derive(" + color + ", 20%);");

            headColor.getChildren().addAll(left, center, right);

            VBox content = new VBox();
            content.setPadding(new Insets(5D));

            Label title = new Label(f);
            title.getStyleClass().addAll("h3", "title");

            GridPane colorText = new GridPane();
            colorText.getChildren().addAll(new Label("-10%"),new Label(f), new Label("20%"));
            GridPane.setConstraints(colorText.getChildren().get(0), 0,0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
            GridPane.setConstraints(colorText.getChildren().get(1), 1,0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
            GridPane.setConstraints(colorText.getChildren().get(2), 2,0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

            content.getChildren().addAll(title, colorText);

            child.getChildren().addAll(headColor, content);

            child.setStyle(" -fx-background-radius : 10px; -fx-background-color : white; ");
            child.getStyleClass().add("elevator");

            ContextMenu contextMenu = new ContextMenu();
            MenuItem name = new MenuItem("Hexadecimal");
            MenuItem background = new MenuItem("Background");
            MenuItem border = new MenuItem("Border");
            MenuItem fill = new MenuItem("Fill");
            MenuItem text = new MenuItem("Text");
            MenuItem java = new MenuItem("Java");

            contextMenu.getItems().addAll(name, background, border, fill, text, java);

            name.setOnAction(event -> {
                toClipBoard( "Hexadecimal : " + formatHexString(Color.web(properties.getProperty(color))));
            });

            background.setOnAction(event -> {
                toClipBoard(createCopyString("background", color));
            });

            border.setOnAction(event -> {
                toClipBoard( "-fx-border-color : derive(" + color + ", -10%);\n-fx-background-color : " + color + ";\n-fx-background-color : derive(" + color + ", 20%);");
            });

            fill.setOnAction(event -> {
                toClipBoard( createCopyString("fill", color) );
            });

            text.setOnAction(event -> {
                toClipBoard(createCopyString("text", color));
            });

            java.setOnAction(event -> {
                toClipBoard( "Color.web(\"" + formatHexString(Color.web(properties.getProperty(color))) +"\");");
            });


            child.setOnMousePressed(event -> {
                    if(event.isSecondaryButtonDown()) {

                    Bounds bounds = child.localToScreen(child.getBoundsInLocal());
                    contextMenu.show(App.INSTANCE.getDecorator().getWindow(),
                            bounds.getMinX() + 20, bounds.getMaxY() - 40
                    );
                }
            });

            tiles.getChildren().add(child);

        });
    }

    private String createCopyString(String selector, String color) {
        return  "-fx-" + selector + "-color : derive(" + color + ", -10%);\n-fx-" + selector + "-color : " + color + ";\n-fx-" + selector + "-color : derive(" + color + ", 20%);";
    }

    private void toClipBoard(String copy) {

        Clipboard clipboard  = Clipboard.getSystemClipboard();
        ClipboardContent clipContent = new ClipboardContent();
        clipContent.putString(copy);
        clipboard.setContent(clipContent);

        SnackBarCreator.INSTANCE.createSnackBar("Copied to clipboard", SnackType.INFO);
    }

    private static String formatHexString(Color c) {
        if (c != null) {
            return String.format((Locale) null, "#%02x%02x%02x",
                    Math.round(c.getRed() * 255),
                    Math.round(c.getGreen() * 255),
                    Math.round(c.getBlue() * 255));
        } else {
            return null;
        }
    }

    private void config() {
        int  col  = 0;
        int  row  = 1;

        for (MaterialDesign color : MaterialDesign.values()) {
            String name     = color.name().replace("_", " ");
            String strWeb   = ColorHelper.web(color.get());
            String strRgb   = ColorHelper.rgb(color.get());
            String text     = String.join("", name, "\n", strWeb, "\n", strRgb);

            MATCHER.reset(color.name());
            String brightness = "";
            while (MATCHER.find()) {
                brightness = MATCHER.group(1).replace("_", " ");
            }

            ToggleButton toggle = new ToggleButton(
                    color.name().contains("0") ? "" : color.name() + "\n" + brightness);
            toggle.setToggleGroup(group);
            toggle.setAlignment(Pos.CENTER);
            toggle.setTextFill(Color.WHITE);
            toggle.setCursor(Cursor.HAND);
            toggle.setAlignment(Pos.CENTER);
            toggle.setPrefSize(BOX_WIDTH, BOX_HEIGHT);


            boolean isBox = false;

            if(!color.name().contains("0")){
                toggle.setMouseTransparent(true);
                toggle.setStyle("-fx-background-color : white; -fx-text-fill : -gray;" +
                        "-fx-border-color : transparent transparent -light-gray transparent;" +
                        "-fx-background-radius : 0;");
//                toggle.setMinWidth(200);
//                toggle.setPrefWidth(150D);
            } else {
                toggle.setStyle("-fx-background-color : " +  formatHexString(color.get()) + ";" +
                        "-fx-background-radius : 0;"

                );

                isBox = true;
            }

            toggle.setOnMousePressed(event -> {
                String clipboardContent = "Color.web(\"" +
                        strWeb +
                        "\");\n" +
                        "Color." +
                        strRgb +
                        ";";

                Clipboard clipboard  = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(clipboardContent);
                clipboard.setContent(content);


//                System.out.println(color.get().getBrightness());

                if(color.get().getBrightness() > 0.75){
                    boxColor.setStyle("-fx-background-color : " + formatHexString(color.get()) + "; -inner-text : -dark-gray;");

                } else {
                    boxColor.setStyle("-fx-background-color : " + formatHexString(color.get()) + "; -inner-text : white;");
                }
                textColor.setText(formatHexString(color.get()));

//                if(color.get().getBrightness() > 0.75){
//                    App.getDecorator().getScene().getRoot().lookup(".drawer")
//                            .setStyle("-drawer-color : "  +  formatHexString(color.get()) + ";" +
//                                    "-inner-text : black;"
//                            );
//                } else {
//                    App.getDecorator().getScene().getRoot().lookup(".drawer")
//                            .setStyle("-drawer-color : "  +  formatHexString(color.get()) + ";" +
//                                    "-inner-text : white;"
//                            );
//                }



//                App.getDecorator().getScene().getRoot().
////                toggle.getScene().getRoot().
//                        setStyle("-drawer-color : "  +  formatHexString(color.get()) + ";");
            });

            boolean finalIsBox = isBox;
            toggle.setOnMouseEntered(event -> {
                if(finalIsBox){
                    toggle.setBackground(new Background(new BackgroundFill(color.get(), new CornerRadii(10), Insets.EMPTY)));
                    toggle.setStyle("-fx-background-radius : 10px; -fx-background-color : " + formatHexString(color.get())
                            + ";");
                }
            });

            toggle.setOnMouseExited(event -> {
                if(finalIsBox){
                    toggle.setStyle("-fx-background-radius : 0px; -fx-background-color : " + formatHexString(color.get()) + ";");
                }
            });

            if(finalIsBox){
                toggle.setToggleGroup(group);
            }

            Tooltip tooltip = new Tooltip(text);
            Tooltip.install(toggle, tooltip);
            grid.add(toggle, row, col);
            col++;

            if (row > 16) {
                if (col == 11) {
                    col = 0;
                    row++;
                }
            } else {
                if (col == 15) {
                    col = 0;
                    row++;
                }
            }
        }
        grid.setHgap(1);
        grid.setVgap(1);
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
    }
}
