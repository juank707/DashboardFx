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
package com.gn.global.plugin;

import com.gn.DashApp;
import com.gn.global.exceptions.LoadViewException;
import com.gn.global.exceptions.NavigationException;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/03/2020
 */
public class LoadViews extends Service<Module> {

    private final List<Module> views;
    private final StringBuilder builder = new StringBuilder();

    public LoadViews() {
        Yaml yaml = new Yaml(new Constructor(List.class));
        views = yaml.load(LoadViews.class.getResourceAsStream("/com/gn/global/modules.yml"));
    }

    public void loadView(Module view) throws LoadViewException {

        FXMLLoader loader = new FXMLLoader();
        URL location = null;

        String path = DashApp.getProperties().get("app.module").toString();

        if(view.getName() == null && view.getModuleName() == null){
           throw new LoadViewException("LOAD_VIEW", "This module does not have a name or a moduleName identification.");
        }

        if(view.getModuleName() != null ) {
            builder.append("/").append(view.getModuleName());
        }

        if(view.getViews() != null) {

            for (Module v : view.getViews()) {
                if (v.getFxmlFile() != null && v.getModuleName() != null) {
                    location = getClass().getResource(path + builder + "/" + v.getFxmlFile());
                }
                loadView(v);
            }
        }

        if(view.getModuleName() == null ){
            location = getClass().getResource(path + builder + "/"  + view.getFxmlFile());
        } else if(view.getFxmlFile() != null && view.getModuleName() != null){
            location = getClass().getResource(path + builder + "/" + view.getFxmlFile());
        }

        if(view.getModuleName() != null) {
            String act = builder.substring(builder.lastIndexOf("/") + 1 , builder.length());
            if (act.equals(view.getModuleName())) {
                builder.delete(builder.lastIndexOf("/"), builder.length());
            }
        }

        if (location != null){
            loader.setLocation(location);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ViewManager.INSTANCE.put(new ViewController(view, loader));
        }
    }
//
    @Override
    protected synchronized void succeeded() {
        super.succeeded();

//        System.out.println("+----------------------------------+");
//        System.out.println("|        LoadViews Succeeded       |");
//        System.out.println("+----------------------------------+");

        try {
            ViewManager.INSTANCE.navigate(DashApp.decorator, "main");
            ScrollPane body = (ScrollPane) DashApp.decorator.lookup("#body");
            ViewManager.INSTANCE.openSubView(body, "dashboard");
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void failed() {
        super.failed();
        super.getException().printStackTrace();
    }

    @Override
    public void start() {
        super.start();
//        System.out.println("+----------------------------------+");
//        System.out.println("|        Starting Load Views       |");
//        System.out.println("+----------------------------------+");
    }

    @Override
    protected Task<Module> createTask() {
        return new Task<Module>() {
            @Override
            protected Module call()  {
                Label lbl = (Label) DashApp.decorator.lookup("#labelLoading");
                for (Module view : views) {
//                    System.out.println("Loading... " + view);
                    try {
                        loadView(view);
                    } catch (LoadViewException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        if(view.getName() != null)
                            lbl.setText("Loading... [ " + (view.getName() + " ]/[ " + view.getName() + " ]"));
                    });
                }
                Platform.runLater(() -> lbl.setText("Going to Main :D"));
                return null;
            }
        };
    }
}
