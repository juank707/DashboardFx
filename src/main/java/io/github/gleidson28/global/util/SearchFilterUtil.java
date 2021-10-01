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

import io.github.gleidson28.global.model.Model;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  31/01/2021
 */
@SuppressWarnings("ALL")
public class SearchFilterUtil {

    private static TextField search;
    private static ListView<Model> listView;
    private static ObservableList<Model> source;

    public static void textFieldSearch(TextField search, TableView table, FilteredList<? extends Model> filteredList){
        search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(model -> {

            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();
            return model.getName().toLowerCase().contains(lowerCaseFilter);
        }));

        table.setItems(filteredList);

        table.getItems().addListener((ListChangeListener<Model>) c -> {
            if(c.next()) {
                if(c.getList().size() != 0) table.getSelectionModel().selectFirst();
            }
        });

    }

    public static void textFieldSearch(TextField search, TableView table, ObservableList<Model> source) {

        FilteredList<Model> filteredList = new FilteredList<>(source, c -> true);


        search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(model -> {

            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();
            return model.getName().toLowerCase().contains(lowerCaseFilter);
        }));

        table.setItems(filteredList);

        table.getItems().addListener((ListChangeListener<Model>) c -> {
            if(c.next()) {
                if(c.getList().size() != 0) table.getSelectionModel().selectFirst();
            }
        });
    }

    public static void textFieldSearch(TextField search, ListView listView, ObservableList<? extends Model> source) {

        FilteredList<? extends Model> filteredList = new FilteredList<>(source, c -> true);


        search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(model -> {

            // If filter text is empty, display all items.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();
            return model.getName().toLowerCase().contains(lowerCaseFilter);
        }));

        listView.setItems(filteredList);
        if (!source.isEmpty()) listView.getSelectionModel().selectFirst();

        listView.getItems().addListener((ListChangeListener<Model>) c -> {
            if(c.next()) {
                if(c.getList().size() != 0) listView.getSelectionModel().selectFirst();
            }
        });
    }
}
