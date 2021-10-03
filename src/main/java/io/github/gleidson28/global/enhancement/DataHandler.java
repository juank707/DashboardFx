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
package io.github.gleidson28.global.enhancement;

//import com.gn.global.util.TableActions;

import io.github.gleidson28.global.model.Model;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/07/2019
 */
public class DataHandler<E extends Model> {

    private final ComboBox<Integer>   entries;
    private       Control             dataControl;
    private final Pagination          pagination;
    private final Hyperlink           first;
    private final Hyperlink           last;
    private final Label               legend;

    private int maxPages;

    private final BooleanProperty                filtered = new SimpleBooleanProperty();
    private final ObjectProperty<ResourceBundle> bundle   = new SimpleObjectProperty<>();

    private Boolean pag = false;

    private FilteredList<E> filteredList;

    // For hacking and tests
    private final GridPane  paginationParent;
    private final HBox      container;

    private CheckBox checkBox;

    public DataHandler(Control dataControl, Hyperlink first, Hyperlink last,
                       Pagination pagination, ComboBox<Integer> entries, Label legend,
                       FilteredList<E> filteredList)
    {
        this(dataControl, first, last, pagination, entries, legend, filteredList, null);
    }

    public DataHandler(Control dataControl, Hyperlink first, Hyperlink last,
                       Pagination pagination, ComboBox<Integer> entries, Label legend,
                       FilteredList<E> filteredList, ResourceBundle bundle) {

        if (!(dataControl instanceof TableView) && !(dataControl instanceof ListView)) {
            try {
                throw new Exception("Data Control does not instance of TableView or ListView ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if(entries.getItems().size() == 0) {
            entries.getItems().setAll(5, 10, 50, 100);
            entries.setValue(10);
        }

        entries.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                pagination(pagination.getCurrentPageIndex(), newValue);
            }
        });

//        pagination
        this.dataControl = dataControl;
        this.entries = entries;
        this.first = first;
        this.last = last;
        this.pagination = pagination;
        this.legend = legend;
        this.filteredList = filteredList;
        this.bundle.set(bundle);

        // For hacking and tests
        container = (HBox) pagination.getParent();
        paginationParent = (GridPane) container.getParent();

        first.setMinWidth(0D);
        last.setMinWidth(0D);

        addPagination();
//        updatePagination(converter.size());
        if(filteredList != null)
        updatePagination(filteredList.size()); // Testing

        if(filteredList != null)
        pagination(0, entries.getValue());

        first.addEventHandler(ActionEvent.ACTION,
                event -> pagination(0, entries.getValue()));

        last.addEventHandler(ActionEvent.ACTION,
                event -> pagination(maxPages, entries.getValue()));

        if(filteredList != null) {
            setFilteredList(filteredList);
        }

        this.first.setDisable(true);

        // config if bundle not null
        if(this.bundle.get() != null){
            first.setText(this.bundle.get().getString("DataTable.first"));
            last.setText(this.bundle.get().getString("DataTable.last"));
        } else {
            first.setText("First");
            last.setText("Last");
        }
    }

    public void removeCheckBoxColumn() {
        if(dataControl instanceof TableView) {
            TableView<Object> tableView = (TableView<Object>) dataControl;
            tableView.getColumns().forEach(
                    c -> {
                        if (c.getUserData() != null) {
                            if (c.getUserData().equals("check-column")) {
                                tableView.getColumns().remove(c);
                            }
                        }
                    }
            );
        }
    }

    private ChangeListener<Boolean> checkObserver;

    public void setDataControl(Control dataControl) {
        this.dataControl = dataControl;

        if(filteredList != null)
            updatePagination(filteredList.size()); // Testing

        if(filteredList != null)
            pagination(0, entries.getValue());
    }


    public void setFilteredList(FilteredList<E> filteredList) {

        this.filteredList = filteredList;

        if(filteredList != null) {

            filteredList.addListener((ListChangeListener<E>) c -> {
                if (c.next()) {
                    if (c.wasAdded()) {
                        c.getAddedSubList().stream().filter(e -> e instanceof SelectedCell).forEach(e ->
                                ((SelectedCell) e).selectedProperty().addListener(checkObserver));
                    }
                }
            });

            filteredList.stream().filter(filter -> filter instanceof SelectedCell).forEach(c ->
                    ((SelectedCell) c).selectedProperty().addListener(checkObserver));

            filteredList.getSource().addListener((ListChangeListener<E>) c -> {
                if (c.next()) {
                    if (c.wasAdded()) {
                        if(dataControl instanceof TableView) {
                            TableView table = (TableView) dataControl;
                            if (table.getItems().size() <= entries.getValue()) {
                                pagination();
                            } else pagination(maxPages, entries.getValue());
                        } else if(dataControl instanceof ListView) {
                            ListView list = (ListView) dataControl;
                            if (list.getItems().size() <= entries.getValue()) {
                                pagination();
                            } else pagination(maxPages, entries.getValue());
                        }

                    }
                }
            });

            updatePagination(filteredList.size());
            pagination(0, entries.getValue());

            filteredList.predicateProperty().addListener((observable, oldValue, newValue) -> pagination());
        }

    }

    public void pagination(int index, int limit){
        pag = true;

        if (checkBox != null) {
            checkBox.setIndeterminate(false);
            checkBox.setSelected(false);
        }

        if(isFiltered()){
            paginationFilter(filteredList, index);
        } else {
            int size = filteredList.size();

            ObservableList<E> data = null;

            if(dataControl instanceof TableView) {
                data = ((TableView<E>) dataControl).getItems();
            } else data = ((ListView<E>) dataControl).getItems();

            List<E> list = filteredList.
                    stream()
                    .skip((long) index * limit)  // Equivalent to SQL's offset
                    .limit(limit) // Equivalent to SQL's limit
                    .collect(Collectors.toList());

            data = FXCollections.observableArrayList(list);

            if(dataControl instanceof TableView) {
                ((TableView<E>) dataControl).setItems(data);
            } else ((ListView<E>) dataControl).setItems(data);

            altLegend(
                    index == 0 ? data.isEmpty() ? 0 : index + 1
                            : ((index + 1) * entries.getValue()) - (entries.getValue() - 1),
                    index == 0 ? data.size() : (entries.getValue() * pagination.getCurrentPageIndex()) + data.size(),
                    size
            );


            updatePagination(size);

            if(index >= 0)
                pagination.setCurrentPageIndex(index);

        }
        pag = false;
    }

    public void pagination(){
        pagination(pagination.getCurrentPageIndex(), entries.getValue());
    }

    private void paginationFilter(ObservableList<E> filtered, int index) {

        pag = true;
        int newList = index * entries.getValue();
        List<E> subList;
        subList = filtered.subList(Math.min(newList, filtered.size()), Math.min(filtered.size(), newList + entries.getValue()));

        ObservableList<E> newData = FXCollections.observableArrayList(subList);

        if(dataControl instanceof TableView) ((TableView) dataControl).setItems(newData);
        else ((ListView) dataControl).setItems(newData);


        updatePagination(filtered.size());

        altLegend(
                subList.size() == 0 ? 0 :
                        index == 0 ? index + 1 : ( (index+1) * entries.getValue()) - (entries.getValue() - 1),
                index == 0 ? subList.size() : (entries.getValue() * pagination.getCurrentPageIndex()) + subList.size(),
                filtered.size(),
                filtered.size()
        );

        pagination.setCurrentPageIndex(index);

        pag = false;
    }

    private void addPagination(){
        pagination.setCurrentPageIndex(0);
        pagination.currentPageIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

            pagination(newValue.intValue(), entries.getValue());
            first.setDisable(newValue.intValue() == 0);
            last.setDisable(newValue.intValue() == pagination.getPageCount() - 1);

        });
    }

    private void updatePagination(int size){

        Platform.runLater(() -> {

            int deff = 5;

            double division = size % entries.getValue();
            maxPages = division == 0 ? ( size / entries.getValue() ) : ( size / entries.getValue() ) + 1;

            pagination.setPageCount(maxPages);

            hidePagination(maxPages < 2);
            if(filteredList.size() > 0) {
                pagination.setMaxPageIndicatorCount(Math.min(maxPages, deff));
                int count = 3;
                int width = 26;
                pagination.setMinWidth((pagination.getMaxPageIndicatorCount() + count) * width);
                pagination.setPrefWidth((pagination.getMaxPageIndicatorCount() + count) * width);
                pagination.setMaxWidth((pagination.getMaxPageIndicatorCount() + count) * width);
            }
        });

    }


    private void hidePagination(boolean hide) {
        paginationParent.setVisible(!hide);
    }

    private void altLegend(int init, int end, int total){
        Platform.runLater(() -> {
            if(this.bundle.get() != null){
                legend.setText(
                        bundle.get().getString("DataTable.showing") + " "
                                + init + " " + bundle.get().getString("DataTable.to") + " "
                                + end + " " + bundle.get().getString("DataTable.of") + " " + total + " "
                                + bundle.get().getString("DataTable.entries") + "."
                );
            } else {

                legend.setText(
                        "Showing "
                                + init + " to "
                                + end + " of "
                                + total
                                + " entries."
                );
            }
        });
    }

//    (filtered from 57 total entries)

    private void altLegend(int init, int end, int total, int filtered){
        Platform.runLater(() -> {
//            if(this.bundle != null){
//                legend.setText(
//                        bundle.get().getString("DataTable.showing") + " "
//                                + init + " " + bundle.get().getString("DataTable.to") + " "
//                                + end + " " + bundle.get().getString("DataTable.of") + " " + total + " "
//                                + bundle.get().getString("DataTable.entries") + ". "
//                                + "(" + bundle.get().getString("DataTable.filtered") + " "
//                                + bundle.get().getString("DataTable.from") + " "
//                                + filtered +" total " + bundle.get().getString("DataTable.entries") + ")"
//                );
//            } else {
//                legend.setText(
//                        "Showing "
//                                + init + " to "
//                                + end + " of "
//                                + total
//                                + " entries."
//                                + "(Filtered "
//                                + "from "
//                                + filtered + " total entries)"
//                );
//            }
        });
    }

    public void setBundle(ResourceBundle bundle){
        this.bundle.set(bundle);
    }

    public boolean isFiltered() {
        return filtered.get();
    }

    @Deprecated
    public void refresh(){
        setBundle(ResourceBundle.getBundle(this.bundle.get().getBaseBundleName(), Locale.getDefault()));
        pagination();
    }

//    public void addFilter(Bindin)
    public void addFilter(Predicate<E> predicate) {

        List<Predicate<E>> allPredicates = new ArrayList<>();
        allPredicates.add(predicate);

        List<E> result = filteredList.stream()
                .filter(allPredicates.stream().reduce(x->true, Predicate::and))
                .collect(Collectors.toList());

//        filteredList.getPredicate().and(predicate);

//
//        filteredList.predicateProperty().addListener((observable, oldValue, newValue) -> {
//            this.paginationFilter(result, pagination.getCurrentPageIndex());
//            this.pagination(this.getIndex(), this.getLimit());
//        });

    }

}