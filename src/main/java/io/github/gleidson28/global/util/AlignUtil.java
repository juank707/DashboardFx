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
package io.github.gleidson28.global.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/02/2021
 */
public class AlignUtil {

    private AlignUtil() {
    }

    private static void clear(Node node) {
        AnchorPane.clearConstraints(node);
    }

    /**
     * Função para facilitar o redimensionamento dos nós para seu tamanho de
     * acordo com a distancia do seu parente
     *
     * @param no     node parente do componente a ser redimensionado
     * @param top    distancia do topo com o nó parente
     * @param right  distancia da direita com o nó parente
     * @param bottom distancia do fundi com o nó parente
     * @param left   distancia sa esquerda com o nó parente
     */
    public static void margin(Node no, double top, double right, double bottom, double left) {
        clear(no);
        AnchorPane.setTopAnchor(no, top);
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setBottomAnchor(no, bottom);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Função para facilitar o redimensionamento dos nós para seu tamanho de
     * acordo com a distancia do seu parente
     *
     * @param no    node parente do componente a ser redimensionado
     * @param valor valores para todos os nodes
     */
    public static void margin(Node no, double valor) {
        clear(no);
        AnchorPane.setTopAnchor(no, valor);
        AnchorPane.setRightAnchor(no, valor);
        AnchorPane.setBottomAnchor(no, valor);
        AnchorPane.setLeftAnchor(no, valor);
    }

    /**
     * Defenir valores de margen topo, direita e esquerda em relação ao parente
     */
    public static void margin(Node no, double top, double right, double left) {
        clear(no);
        AnchorPane.setTopAnchor(no, top);
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Defenir valores de margen direita e esquerda em relação ao parente
     */
    public static void margin(Node no, double right, double left) {
        clear(no);
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Defenir valores de margen do topo
     */
    public static void marginTop(Node no, double top) {
        clear(no);
        AnchorPane.setTopAnchor(no, top);
    }

    /**
     * Defenir valores de margen direita
     */
    public static void marginRight(Node no, double right) {
        clear(no);
        AnchorPane.setRightAnchor(no, right);
    }

    /**
     * Defenir valores de margen rodape
     */
    public static void marginBottom(Node no, double bottom) {
        clear(no);
        AnchorPane.setBottomAnchor(no, bottom);
    }

    /**
     * Defenir valores de margen esquerda
     */
    public static void marginLeft(Node no, double left) {
        clear(no);
        AnchorPane.setLeftAnchor(no, left);
    }
}
