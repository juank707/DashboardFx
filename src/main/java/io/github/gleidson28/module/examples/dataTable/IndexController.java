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
package io.github.gleidson28.module.examples.dataTable;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutLeft;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.enhancement.CrudView;
import io.github.gleidson28.global.enhancement.DataHandler;
import io.github.gleidson28.global.enhancement.ActionView;
import io.github.gleidson28.global.enhancement.ObserverView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.factory.*;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.popup.Popup;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.controlsfx.control.RangeSlider;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/09/2020
 */
public class IndexController implements Initializable, ActionView, CrudView, ObserverView {

    @FXML private StackPane root;

    @FXML private ComboBox<Integer> entries;

    @FXML private Pagination pagination;
    @FXML private Hyperlink first;
    @FXML private Hyperlink last;
    @FXML private Label     legend;
    @FXML private Label    btnFilter;
    @FXML private GridPane  contPag;

    @FXML private TextField         search;

    @FXML private TableView  <Professional>              table;
    @FXML private TableColumn<Professional, Number>      columnId;

    @FXML private TableColumn<Professional, Professional>      columnName;

    @FXML private TableColumn<Professional, Status>      columnStatus;
    @FXML private TableColumn<Professional, BigDecimal>  columnPrice;
    @FXML private TableColumn<Professional, Number>      columnRating;
    @FXML private TableColumn<Professional, String>      columnLocation;
    @FXML private TableColumn<Professional, String>      columnEmail;

    @FXML private GridPane title;
    @FXML private HBox     boxControls;
    @FXML private HBox     boxSearch;
    @FXML private HBox     boxEntries;
    @FXML private Label    columnOptions;


    @FXML private VBox content;
    @FXML private HBox boxPagination;

    @FXML private TableColumn<Professional, Professional>     actions;

    @FXML private Circle loader;

    private FilteredList<Professional>   filteredItems;

    private final ObjectProperty<Predicate<Professional>>    nameFilter             = new SimpleObjectProperty<>();
    private final ObjectProperty<Predicate<Professional>>    rangeFilter            = new SimpleObjectProperty<>();
    private final ObjectProperty<Predicate<Professional>>    ratingFilter           = new SimpleObjectProperty<>();

    private final ObjectProperty<Predicate<Professional>>    statusFilter           = new SimpleObjectProperty<>();

    private final ProfessionalPresenter              professionalPresenter  = new ProfessionalPresenter();

    // Popup`s Fields

    private final RangeSlider price = new RangeSlider();
    private final RangeSlider rating = new RangeSlider();
    private final ToggleGroup status = new ToggleGroup();

    private final ListView<Professional> listView = new ListView<>();

    private StackPane popupRoot;
    private DataHandler<Professional> dataHandler;

    private StackPane boxColumnVisibility;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filteredItems = new FilteredList<>(professionalPresenter.getAll(), d -> true);

        dataHandler = new DataHandler<>(
                table, first, last,
                pagination, entries, legend,
                filteredItems);


        boxColumnVisibility = createColumnFilter();

    }

    @FXML
    private void openBoxColumnVisibility() {
        boxColumnVisibility.setPadding(new Insets(10));
        Popup dashPopup = new Popup(boxColumnVisibility);
        dashPopup.showBottomLeft(columnOptions);
    }

    private StackPane createColumnFilter() {
        StackPane root = new StackPane();
        VBox box = new VBox();
        box.setPadding(new Insets(5D));

        box.setSpacing(10D);

        for(TableColumn column : table.getColumns()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(column.getText());
            checkBox.selectedProperty().bindBidirectional(column.visibleProperty());
            box.getChildren().add(checkBox);
        }

        root.getChildren().add(box);
        return root;
    }

    private void setup() {

        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        columnName.setCellValueFactory(new DefaultValueFactory<>());
        columnName.setCellFactory(new ProfileFactory<>());

        columnStatus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStatus()));
        columnStatus.setCellFactory(new StatusFactory<>());

        columnPrice.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().getPrice()));
        columnPrice.setCellFactory(new MonetaryFactory<>());

        columnRating.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().getRating()));
        columnRating.setCellFactory(new RatingFactory<>());

        columnLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        columnEmail.setCellValueFactory(param ->
                new ReadOnlyObjectWrapper<>(param.getValue().getEmail()));
        columnEmail.setCellFactory(new EmailFactory<>());

        actions.setCellValueFactory(new DefaultValueFactory<>());
        actions.setCellFactory(new ActionFactory<>());

        nameFilter.bind(Bindings.createObjectBinding( () ->
                        product -> product.getName().toLowerCase()
                                .contains(search.getText().toLowerCase()),
                search.textProperty()));

        rangeFilter.bind(Bindings.createObjectBinding( () ->
                        professional -> professional.getPrice() != null && price.getLowValue() <= professional.getPrice().doubleValue() &&  professional.getPrice().doubleValue() <= price.getHighValue(),
                price.lowValueProperty(), price.highValueProperty()
        ));

        ratingFilter.bind(Bindings.createObjectBinding( () ->
                        professional -> rating.getLowValue() <= professional.getRating() &&  professional.getRating() <= rating.getHighValue(),
                rating.lowValueProperty(), rating.highValueProperty()
        ));

        statusFilter.bind(Bindings.createObjectBinding( () ->
            professional -> status.getSelectedToggle().getUserData().equals(Status.ALL) || status.getSelectedToggle().getUserData().equals(professional.getStatus()),
                status.selectedToggleProperty()
        ));

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> nameFilter.get().and(rangeFilter.get()).and(ratingFilter.get()).and(statusFilter.get()),
                nameFilter, rangeFilter, ratingFilter, statusFilter ));
    }

    private void configTable() {
        popupRoot = new FilterRoot(price, rating, status);

        new FadeInLeft(loader).play();

        setup();

        Task<ObservableList<Professional>> load = professionalPresenter.getTask();
        RotateTransition rotate = new RotateTransition(Duration.seconds(10), loader);

        rotate.setNode(loader);
        rotate.setAutoReverse(true);

        rotate.setByAngle(180);
        rotate.setDelay(Duration.seconds(0));
        rotate.setRate(3);
        rotate.setCycleCount(-1);
        rotate.play();

        FadeTransition fadeTransition = new FadeTransition();

        fadeTransition.setNode(loader);

        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        load.setOnSucceeded(event -> new FadeOutLeft(loader).play());

        listView. setStyle("-fx-fixed-cell-size : 60px;");

        listView.setCellFactory(new OptionListFactory());

        Thread thread = new Thread(load);
        thread.setName("Loading data table [Professional]");
        thread.setPriority(1);
        thread.start();

    }

    @Override
    public void onEnter() {
        if(filteredItems.isEmpty()) {
            configTable();
        }
    }


    @Override
    public void onExit() {

    }

    @FXML
    private void goRegister() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_register", new Professional());
    }

    @FXML
    private void openFilterPopup() {
        Popup p = new Popup(popupRoot);
        p.showBottomLeft(btnFilter);
//        p.showOnWindow();
    }

    @Override
    public void setModel(Model model) {
        Professional professional = (Professional) model;
        table.refresh();
        dataHandler.pagination();
    }


    private final ChangeListener<Number> resizeTitles = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            if(newValue.doubleValue() <= 800) {
                title.getColumnConstraints().clear();
                GridPane.setConstraints(boxSearch, 0, 0, 1, 1,HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                boxEntries.setAlignment(Pos.CENTER);
                GridPane.setConstraints(boxEntries, 0, 1, 1, 1,HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
                title.setMinHeight(120);
            } else {
                boxControls.setAlignment(Pos.CENTER_LEFT);
                boxSearch.setAlignment(Pos.CENTER_LEFT);
                GridPane.setConstraints(boxSearch, 0, 0, 1, 1,HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
                GridPane.setConstraints(boxEntries, 1, 0, 1, 1,HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);

                title.setMinHeight(Region.USE_COMPUTED_SIZE);
            }
        }
    };

    private final ChangeListener<Number> resizeFields = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if(newValue.doubleValue() <= 800) {
                contPag.getColumnConstraints().clear();
                GridPane.setConstraints(legend, 0, 0, 1, 1,HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                GridPane.setConstraints(boxPagination, 0, 1, 1, 1,HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
                boxPagination.setAlignment(Pos.CENTER);
            } else {
                contPag.getColumnConstraints().clear();
                GridPane.setConstraints(legend, 0, 0, 1, 1,HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                GridPane.setConstraints(boxPagination, 1, 0, 1, 1,HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
                boxPagination.setAlignment(Pos.CENTER_RIGHT);
            }
        }
    };

    private final ChangeListener<Number> resizeTable = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if(newValue.doubleValue() <= 800) {
                if(content.getChildren().contains(table)) {
                    content.getChildren().remove(table);
                    dataHandler.setDataControl(listView);
                    VBox.setVgrow(listView, Priority.ALWAYS);
                    content.getChildren().add(listView);
                    columnRating.setVisible(false);

                    boxEntries.getChildren().remove(columnOptions);

                }

            } else if (newValue.doubleValue() > 800 && newValue.doubleValue() <= 1200) {

                if (!content.getChildren().contains(table)) {
                    content.getChildren().remove(listView);
                    content.getChildren().add(table);
                    dataHandler.setDataControl(table);
                }

                columnEmail.setVisible(false);
                columnLocation.setVisible(false);
                columnRating.setVisible(true);

            } else if(newValue.doubleValue() > 1200) {

                if (!content.getChildren().contains(table)) {
                    content.getChildren().remove(listView);
                    content.getChildren().add(table);
                    dataHandler.setDataControl(table);
                }

                columnEmail.setVisible(true);
                columnRating.setVisible(true);
                columnLocation.setVisible(true);

                if(!boxEntries.getChildren().contains(columnOptions))
                    boxEntries.getChildren().addAll(columnOptions);

            }
        }
    };

    @Override
    public List<ChangeListener<Number>> getListeners() {
        return Arrays.asList(resizeTable, resizeFields, resizeTitles);
    }
}
