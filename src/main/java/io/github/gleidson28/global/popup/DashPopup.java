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
package io.github.gleidson28.global.popup;

import animatefx.animation.Pulse;
import io.github.gleidson28.App;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.module.layout.LayoutController;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.controlsfx.control.PopOver;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/06/2021
 */
public class DashPopup extends PopOver {

    private Node owner;

    public DashPopup(Node content) {

        this.getRoot().getStylesheets().add(
                DashPopup.class.getResource("/theme/css/poplight.css").toExternalForm());

        this.setArrowLocation(ArrowLocation.TOP_LEFT);
        this.setArrowIndent(0);
        this.setArrowSize(0);
        this.setCloseButtonEnabled(false);
        this.setHeaderAlwaysVisible(false);
        this.setCornerRadius(0);

        this.setHeaderAlwaysVisible(false);
        this.setContentNode(content);

        this.setCornerRadius(0);

//        this.setAnimated(false);


    }

    public void showOnWindow() {

        this.show(App.INSTANCE.getDecorator().getWindow());
        LayoutController layoutController = (LayoutController)
                ViewManager.INSTANCE.getController("layout");

        layoutController.foregroundOpen();

        Pulse fadeInLeft = new Pulse();
        fadeInLeft.setNode(getRoot());

        this.setOnHidden(event -> layoutController.foregroundClose());
    }

    public void showAndWait(Node node) {

//        this.show(node,  node.getLayoutBounds().getHeight()  );

//        this.show(node, node.getLayoutBounds().getWidth(), node.getLayoutBounds().getHeight());
        Bounds bounds = node.localToScreen(node.getBoundsInLocal());


        this.show(node, bounds.getMinX() + bounds.getWidth() ,
                (bounds.getMinY() + bounds.getHeight()) - bounds.getHeight() );

//        this.show(App.INSTANCE.getDecorator().getWindow());

        LayoutController layoutController = (LayoutController)
                ViewManager.INSTANCE.getController("layout");

//        Node skinNode = this.getSkin().getNode();
//        new Pulse(skinNode).play();

//        Pulse fadeInLeft = new Pulse();
//        fadeInLeft.setNode(getRoot());
//
//        fadeInLeft.play();
//        layoutController.foregroundOpen();
//
//        this.setOnHidden(event -> layoutController.foregroundClose());
    }
}
