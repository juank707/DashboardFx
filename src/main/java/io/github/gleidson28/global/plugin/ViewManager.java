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
import io.github.gleidson28.decorator.GNDecorator;
import io.github.gleidson28.global.creators.PopupCreator;
import io.github.gleidson28.global.enhancement.CrudView;
import io.github.gleidson28.global.enhancement.FluidView;
import io.github.gleidson28.global.enhancement.ObserverView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.model.Model;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;
import java.util.List;

//import io.github.gleidson28.module.registers.employees.FormView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  07/10/2018
 */
@SuppressWarnings("unused")
public enum ViewManager {

    INSTANCE;

    private String current;
    private String previous;

    private GNDecorator decorator;
    private ScrollPane  body;
    private FlowPane    crumb;
    private Label       breadTitle;

    private final HashMap<String, ViewConstructor> SCREENS = new HashMap<>();

    public void put(ViewConstructor viewController) {

        // prevent reload
        current = null;
        previous = null;

        if(viewController != null) {
            SCREENS.put(viewController.getModule().getName(), viewController);
        }
    }

    public void previous() throws NavigationException {
        setContent(previous);
    }

    public void navigate(String name) throws NavigationException {

        ViewConstructor viewController = get(name);

        if(viewController.getRoot() == null)
            throw new NavigationException("NAVIGATION", String.format("The view '%s' was not encountered.", name));
        else {

            viewController.getRoot().layout();

            App.INSTANCE.setContent(viewController.getRoot());

            if (get(name).getController() instanceof FluidView){
                ( (FluidView) viewController.getController()).onEnter();

            }

            if(viewController.getRoot().lookup("#defaultButton") != null) {
                viewController.getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        if(!PopupCreator.INSTANCE.isShowing() )
                            ((Button) viewController.getRoot().lookup("#defaultButton")).fire();

                    }
                });
            }

            viewController.getRoot().requestFocus();
        }
    }


    private void updateCrumb(Module module) {
        if(module.getRoot() != null) {
            Module root = module.getRoot();
            Hyperlink link = new Hyperlink(root.getTitle() + " / ");
            link.getStyleClass().add("bread-link");

            if(root.getFxml() != null) {
                link.setOnAction( event -> {
                    try {
                        setContent(root.getName());
                    } catch (NavigationException e) {
                        e.printStackTrace();
                    }
                });
            }

            if (root.getViews() != null) {
                updateCrumb(root);
                crumb.getChildren().add(link);
                link.getStyleClass().add("bread-link");
                if (root.getFxml() == null) {
                    link.setDisable(true);
                }
            }
        }
    }

    @Deprecated
    public void setContent(Node node) {
        getContents();
        body.setContent(node);
    }

    public String setContent(String name) throws NavigationException {
        return setContent(name,  null, null);
    }

    public String setContent(String view, Model model) throws NavigationException {
        return setContent(view, model, null);
    }

    public String setContent(String view, String title) throws NavigationException {
        return setContent(view, null, title);
    }

    private String setContent(String name, Model model, String title) throws NavigationException {

        getContents();

        ViewConstructor viewController = getWithUpdate(name);

        if(viewController != null) {

            crumb.getChildren().clear();
            updateCrumb(viewController.getModule());

            Hyperlink link = new Hyperlink(viewController.getModule().getTitle());
            link.getStyleClass().add("bread-link");
            crumb.getChildren().add(link);
            link.setDisable(true);

            if(title != null)
                breadTitle.setText(title);
            else
                breadTitle.setText(viewController.getModule().getTitle());

            body.setContent(viewController.getRoot());

            if(previous != null) {

                Object controller = get(previous).getController();

                if (controller instanceof FluidView)
                    ((FluidView) get(previous).getController()).onExit();

                if (controller instanceof ObserverView) {

                    for(ChangeListener<Number> l : ((ObserverView) controller).getListeners()) {
                        App.INSTANCE.getDecorator().widthProperty().removeListener(l);
                    }

                }

            }

            if (viewController.getController() instanceof FluidView) {
                ((FluidView) viewController.getController()).onEnter();

                if (viewController.getController() instanceof ObserverView) {
                    for (ChangeListener<Number> l : ((ObserverView) viewController.getController()).getListeners()) {
                        // fire listeners
                        l.changed(App.INSTANCE.widthProperty(), App.INSTANCE.getWidth(), App.INSTANCE.getWidth() );

                        App.INSTANCE.widthProperty().addListener(l);

                    }
                }

            }

            if(model != null) {
                if(viewController.getController() instanceof CrudView)
                    ((CrudView)viewController.getController()).setModel(model);
            }

            if(viewController.getRoot().lookup("#defaultButton") != null) {

                viewController.getRoot().requestFocus();
                viewController.getRoot().lookup("#defaultButton").requestFocus();

                decorator.getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {

                    if(decorator.getWindow().getScene().lookup("#defaultButton") != null) {
                        if (event.getCode() == KeyCode.ENTER) {
                            if(!PopupCreator.INSTANCE.isShowing())
                                ((Button) viewController.getRoot().lookup("#defaultButton")).fire();
                        }
                    }
                });
            }

            return viewController.getModule().getTitle();

        } else {

            throw new NavigationException("NAVIGATION",
                    String.format("The ViewController '%s' was not encountered.", name));

        }
    }




    //convenient method
    private void getContents() {
         decorator = App.INSTANCE.getDecorator();
         body = (ScrollPane) decorator.lookup("#body");
         crumb = (FlowPane) decorator.lookup(".breadCrumb");
         breadTitle = (Label) decorator.lookup("#breadTitle");
    }

    // Implement in the future
    private boolean isMobile() {
        return decorator.getWidth() < 600;
    }

    public ViewConstructor getCurrent(){
        return SCREENS.get(current);
    }

    public ViewConstructor getPrevious(){
        return SCREENS.get(previous);
    }

    // convenient method
    private ViewConstructor get(String view) {
        return SCREENS.get(view);
    }

    // convenient method
    private ViewConstructor getWithUpdate(String view){
        previous = current;
        current = view;
        return SCREENS.get(view);
    }

    public Module getModule(String view) {
        return SCREENS.get(view).getModule();
    }

    public List<Module> getViews(String view) {
        return SCREENS.get(view).getModule().getViews();
    }

    public ViewConstructor getViewConstructor(String view) {
        return SCREENS.get(view);
    }

    public Parent getRoot(String view) {
        return SCREENS.get(view).getRoot();
    }

    public Object getController(String view) {
        return SCREENS.get(view).getController();
    }

//    public FormView getFormController(String view) {
//        return SCREENS.get(view).getFormController();
//    }

    public int size() {
        return SCREENS.size();
    }

    public boolean contains(String name) {
        return SCREENS.containsKey(name);
    }
}
