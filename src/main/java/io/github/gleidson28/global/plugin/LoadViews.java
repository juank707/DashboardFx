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
package io.github.gleidson28.global.plugin;

import io.github.gleidson28.App;
import io.github.gleidson28.global.creators.AlertCreator;
import io.github.gleidson28.global.creators.AlertType;
import io.github.gleidson28.global.exceptions.NavigationException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import org.controlsfx.control.BreadCrumbBar;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  31/08/2020
 */
public class LoadViews extends Service<Module> {


    private final StringBuilder builder = new StringBuilder();

    private List<Module>    modules = null;
    private Label           lbl     = null;

    private ResourceBundle resourceBundle;

    public LoadViews() {

        // load by yaml document
        try {
            InputStream input = new FileInputStream("app/modules.yml");
            Yaml yaml = new Yaml(new Constructor(List.class));
            modules = yaml.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadView(Module module) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL location = null;

        String path = "/module";

        if(module.getDirectory() != null ) {
            builder.append("/").append(module.getDirectory());
        }

        if(module.getViews() != null) {

            for (Module v : module.getViews()) {

                if (v.getFxml() != null) {
                    location = getClass().getResource(path + builder + "/"
                            + v.getFxml());
                }

                v.setRoot(module);

                loadView(v);

            }

        }

        if(module.getDirectory() == null ) {
            location = LoadViews.class.getResource(path + builder + "/"
                    + module.getFxml());

        } else if(module.getFxml() != null && module.getDirectory() != null){
            location = getClass().getResource(path + builder + "/"
                    + module.getFxml());
        }

        if(module.getDirectory() != null) {
            String act = builder.substring(builder.lastIndexOf("/") + 1 , builder.length());
            if (act.equals(module.getDirectory())) {
                builder.delete(builder.lastIndexOf("/"), builder.length());
            }
        }


        if ( location != null ) {

//            loader.setCharset(StandardCharsets.UTF_8);
            loader.setLocation(location);
            loader.setResources(App.INSTANCE.getResourceBundle());
            loader.load();

            updateMessage("Loading Views [" + module.getName() + "]");

            ViewManager.INSTANCE.put(new ViewConstructor(module, loader));

        }

    }

    @Override
    protected void succeeded() {
        try {
            ViewManager.INSTANCE.navigate("layout");
            ViewManager.INSTANCE.setContent("dashboard");


        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cancelled() {
        super.cancelled();
    }

    @Override
    protected void failed() {
        super.failed();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error on loading view.");
        alert.setContentText(super.getException().getMessage());
        alert.show();

    }

    @Override
    protected synchronized Task<Module> createTask() {

        return new Task<Module>() {

            @Override
            protected Module call() throws IOException {


               updateMessage("Starting...");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateMessage("Loading database...");

//                DaoManager.INSTANCE.loadItems();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateMessage("Carregando visualizações...");

                for(Module module : modules){
                    loadView(module);
                }

                updateMessage("Going to main screen :D");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void updateMessage(String message) {
        if(lbl == null) lbl = (Label) App.INSTANCE.getDecorator().lookup("#labelLoading");
        Platform.runLater( () -> lbl.setText(message));
    }
}
