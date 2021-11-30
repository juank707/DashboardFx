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
package io.github.gleidson28.module.app;

import io.github.gleidson28.decorator.GNDecorator;
import io.github.gleidson28.global.badges.AlertBadge;
import io.github.gleidson28.global.badges.MessageBadge;
import io.github.gleidson28.global.badges.NotificationBadge;
import javafx.application.HostServices;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.jetbrains.annotations.Contract;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/08/2021
 */
public enum ConfigApp {

    INSTANCE;

    private final Properties properties = new Properties();
    private final GNDecorator decorator = new GNDecorator();

    private final DoubleProperty width = new SimpleDoubleProperty();
    private final DoubleProperty height = new SimpleDoubleProperty();

    private Locale locale = null;

    private HostServices hostServices = null;

    ConfigApp() {
        width.set( Double.parseDouble(getString("app.width")));
        height.set( Double.parseDouble(getString("app.height")));

        decorator.setMinWidth(Double.parseDouble(getString("app.min.width")));
        decorator.setMinHeight(Double.parseDouble(getString("app.min.height")));

        HBox badges = new HBox();
        badges.setMaxHeight(20);

        badges.getStyleClass().add("badges");
        badges.getChildren().addAll(new MessageBadge(), new NotificationBadge(), new AlertBadge());
        badges.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(badges, Priority.ALWAYS);
        decorator.getCustomControls().add(badges);
    }

    public void setContent(Parent content) {
        decorator.setContent(content);
    }

    @Contract(pure = true)
    public GNDecorator getDecorator() {
        return this.decorator;
    }

    public ReadOnlyDoubleProperty widthProperty() {
        return this.decorator.widthProperty();
    }

    @Contract(mutates = "this")
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void openLink(String link) {
        this.hostServices.showDocument(link);
    }

    public void show(Parent root) {

        width.bind(decorator.widthProperty());
        height.bind(decorator.heightProperty());

        decorator.setWidth(Double.parseDouble(getString("app.width")));
        decorator.setHeight(Double.parseDouble(getString("app.height")));

        decorator.addStylesheets(
                getClass().getResource("/theme/css/fonts.css").toExternalForm(),
                getClass().getResource("/theme/css/material-color.css").toExternalForm(),
                getClass().getResource("/theme/css/skeleton.css").toExternalForm(),
                getClass().getResource("/theme/css/light.css").toExternalForm(),
                getClass().getResource("/theme/css/bootstrap.css").toExternalForm(),
                getClass().getResource("/theme/css/forms.css").toExternalForm(),
                getClass().getResource("/theme/css/shape.css").toExternalForm(),
                getClass().getResource("/theme/css/typographic.css").toExternalForm(),
                getClass().getResource("/theme/css/helpers.css").toExternalForm(),
                getClass().getResource("/theme/css/master.css").toExternalForm()
        );

        decorator.fullBody();
        decorator.setContent(root);
        decorator.getIcons().add(new Image("/theme/img/logo4.png"));
        decorator.show();

//        decorator.testWithScenicView();

    }

    public Object getObject(String name) {
        try {
            InputStream input = new FileInputStream("app/app.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.get(name);
    }

    public String getString(String name) {
        try {
            InputStream input = new FileInputStream("app/app.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(name);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void store () {
        properties.stringPropertyNames().forEach(f -> properties.setProperty(f, properties.getProperty(f)));

        properties.setProperty("app.width", String.valueOf(decorator.getWidth()));
        properties.setProperty("app.height", String.valueOf(decorator.getHeight()));

        try {
            properties.store(new FileOutputStream("app/app.properties"), "Update on " + LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    public ResourceBundle getResourceBundle() {

        if(locale == null) {
            locale = new Locale(getString("app.lang"), getString("app.country"));
        }

        return ResourceBundle.getBundle("theme.i18n.Lang", locale);
    }
}
