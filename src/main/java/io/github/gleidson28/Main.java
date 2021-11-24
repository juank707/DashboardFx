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
package io.github.gleidson28;

import io.github.gleidson28.global.plugin.LoadViews;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/08/2021
 */
public class Main extends Application {

    @Override
    public void init()  {

    }

    @Override
    public void stop() {
        App.INSTANCE.store();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/module/loader/loader.fxml"));

        App.INSTANCE.show(root);
        App.INSTANCE.setHostServices(getHostServices());

        Thread thread = new Thread(() -> {
            Runnable updater = () -> {
                LoadViews loadViews = new LoadViews();
                loadViews.start();
            };
//             UI update is run on the Application thread
            Platform.runLater(updater);
        });
//         don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();


    }
}
