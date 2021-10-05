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
package io.github.gleidson28.module.professional;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeOutLeft;
import io.github.gleidson28.App;
import io.github.gleidson28.global.dao.ProfessionalPresenter;
import io.github.gleidson28.global.dao.UserPresenter;
import io.github.gleidson28.global.enhancement.DataHandler;
import io.github.gleidson28.global.enhancement.DataTableHandler;
import io.github.gleidson28.global.enhancement.FluidView;
import io.github.gleidson28.global.exceptions.NavigationException;
import io.github.gleidson28.global.factory.*;
import io.github.gleidson28.global.model.Product;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.model.User;
import io.github.gleidson28.global.plugin.ViewManager;
import io.github.gleidson28.global.popup.DashPopup;
import io.github.gleidson28.global.util.MoneyUtil;
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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.Rating;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/09/2020
 */
public class IndexController implements Initializable, FluidView {

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
    private RangeSlider price = new RangeSlider();
    private RangeSlider rating = new RangeSlider();


    private ToggleGroup status = new ToggleGroup();
    private ListView<Professional> listView = new ListView<>();


    private StackPane popupRoot;

    private DataHandler dataHandler = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filteredItems = new FilteredList<>(professionalPresenter.getAll(), d -> true);

        dataHandler = new DataHandler<>(
                table, first, last,
                pagination, entries, legend,
                filteredItems);

    }

    private void setup() {

        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        columnName.setCellValueFactory(new DefaultValueFactory<>());
        columnName.setCellFactory(new ProfileFactory<>());

        columnStatus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStatus()));
        columnStatus.setCellFactory(new StatusFactory<>());

        columnPrice.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrice()));
        columnPrice.setCellFactory(new MonetaryFactory<>());

        columnRating.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getRating()));
        columnRating.setCellFactory(new RatingFactory<>());

        columnLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        columnEmail.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getEmail()));
        columnEmail.setCellFactory(new EmailFactory<>());

        actions.setCellValueFactory(new DefaultValueFactory<>());
        actions.setCellFactory(new ActionFactory<>());

        nameFilter.bind(Bindings.createObjectBinding( () ->
                        product -> product.getName().toLowerCase().contains(search.getText().toLowerCase()),
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
        popupRoot = createRootPopup();

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

//            Thread thread = new Thread(load);
//            thread.setDaemon(true);
//            thread.start();

        listView. setStyle("-fx-fixed-cell-size : 60px;");

        listView.setCellFactory(new Callback<ListView<Professional>, ListCell<Professional>>() {
            @Override
            public ListCell<Professional> call(ListView<Professional> param) {
                return new ListCell<Professional>() {
                    @Override
                    protected void updateItem(Professional item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {

                            HBox body = new HBox();
                            Circle clip = new Circle();

                            clip.setStroke(Color.WHITE);
                            clip.setStrokeWidth(0.6);

                            ImagePattern imagePattern = new ImagePattern(item.getAvatar("50"));

                            clip.setFill(imagePattern);
                            double frac = 20;

                            clip.setRadius(frac);
                            clip.setCenterX(frac);
                            clip.setCenterY(frac);


                            ////////////////////

                            GridPane content = new GridPane();
                            Label name = new Label(item.getName());
                            name.setStyle("-fx-font-weight : bold; ");
                            Label price = new Label(MoneyUtil.format(item.getPrice()));
                            Rating rating = new Rating();
                            rating.setRating(item.getRating());
                            rating.setTranslateX(-35D);
                            rating.setScaleX(0.6);
                            rating.setScaleY(0.6);

                            Label status = new Label();
                            status.setAlignment(Pos.CENTER);
                            status.setMinSize(20,20);
                            status.setPadding(new Insets(3));
                            status.setStyle("-fx-background-radius : 20px;");

                            status.getStyleClass().add("lbl-status");
                            status.setText(item.getStatus().toString());

                            String _default = "-fx-text-fill : white; " +
                                    "-fx-border-width : 2px;  -fx-border-color : white;";

                            switch (item.getStatus()) {

                                case UNAVAILABLE: status.setStyle("-fx-background-color : -amber; " + _default);
                                    break;
                                case FREE:  status.setStyle("-fx-background-color : -success; " + _default);
                                    break;
                                default:  status.setStyle("-fx-background-color : -grapefruit; " + _default);
                                    break;
                            }

                            content.setPadding(new Insets(5));

                            content.getChildren().addAll(name, price,  status);

                            GridPane.setConstraints(name, 0, 0, 1, 1,HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                            GridPane.setConstraints(price, 0, 1, 1, 1,HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
                            GridPane.setConstraints(status, 1, 0, 1, 2,HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

//                            content.setGridLinesVisible(true);

                            Button view = new Button();
                            view.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            SVGPath icon = new SVGPath();
                            icon.setContent("M6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm12 0c-1.1 0-2 .9-2 2s.9 2 2 " +
                                    "2 2-.9 2-2-.9-2-2-2zm-6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z");
                            view.setGraphic(icon);
                            icon.setRotate(90);

                            view.getStyleClass().addAll("round","btn-small", "btn-action");

                            view.setMinHeight(40D);
                            view.setPrefHeight(40D);

                            body.getChildren().addAll(clip, content, view);
                            body.setAlignment(Pos.CENTER);

                            HBox.setHgrow(content, Priority.ALWAYS);

                            setGraphic(body);
                            setItem(item);
                            setText(null);


                        } else {
                            setItem(null);
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });

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

        App.INSTANCE.getDecorator().widthProperty().addListener(resizeTable);
        App.INSTANCE.getDecorator().widthProperty().addListener(resizeFields);
        App.INSTANCE.getDecorator().widthProperty().addListener(resizeTitles);
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

            } else {
                columnEmail.setVisible(true);
                columnRating.setVisible(true);
                columnLocation.setVisible(false);
            }
        }
    };


    @Override
    public void onExit() {
        App.INSTANCE.getDecorator().widthProperty().removeListener(resizeTable);
        App.INSTANCE.getDecorator().widthProperty().removeListener(resizeFields);
        App.INSTANCE.getDecorator().widthProperty().removeListener(resizeTitles);
    }

    @FXML
    private void goRegister() throws NavigationException {
        ViewManager.INSTANCE.setContent("professional_register", new Professional());
    }

    @FXML
    private void openFilterPopup() {
        DashPopup p = new DashPopup(popupRoot);
        p.showBottomRight(btnFilter);
//        p.showOnWindow();
    }

    private StackPane createRootPopup() {
        StackPane root = new StackPane();

        root.setMinHeight(200D);
        root.setPrefSize(350, 370);
        root.setPadding(new Insets(20));

        root.getChildren().add(createBody());
        return root;
    }

    private VBox createBody() {
        VBox body = new VBox();

        body.getChildren().addAll(createTitle(), createContent());

        return body;
    }

    private VBox createTitle() {
        VBox content = new VBox();

        content.setAlignment(Pos.CENTER);
        content.getStyleClass().addAll("border-b-1", "border");
        content.setMinHeight(48);
        content.setPrefSize(274,61);

        Label title = new Label("Filters");

        title.getStyleClass().add("h4");
        title.setStyle("-fx-font-weight : bold;");
        content.getChildren().addAll(title);
        return content;
    }

    private ScrollPane createContent() {
        ScrollPane root = new ScrollPane();
        root.setFitToWidth(true);
        VBox.setVgrow(root, Priority.ALWAYS);

        VBox content = new VBox();
        content.setSpacing(10D);

        // Price
        VBox boxPrice = new VBox();
        boxPrice.setPrefHeight(70);
        Label lblPrice = new Label("Price:");
        boxPrice.getChildren().addAll(lblPrice, price);
        VBox.setMargin(boxPrice, new Insets(10, 0, 0,0));
        boxPrice.setFillWidth(true);
        VBox.setMargin(price, new Insets(5));

        price.setBlockIncrement(10);
        price.setLowValue(0);
        price.setMajorTickUnit(10);
        price.setMax(100);
        price.setMin(0);
        price.setMinorTickCount(10);
        price.setShowTickLabels(true);
        price.setShowTickMarks(true);
        price.setSnapToTicks(true);
        price.setHighValue(100);

        // Rating
        VBox boxRating = new VBox();
        boxRating.setPrefHeight(70);
        Label lblRating = new Label("Rating:");
        boxRating.getChildren().addAll(lblRating, rating);
        VBox.setMargin(boxRating, new Insets(10, 0, 0,0));
        boxRating.setFillWidth(true);
        VBox.setMargin(rating, new Insets(5));

        rating.setBlockIncrement(1);
        rating.setLowValue(0);
        rating.setMajorTickUnit(1);
        rating.setMax(5);
        rating.setMin(0);
        rating.setMinorTickCount(1);
        rating.setShowTickLabels(true);
        rating.setShowTickMarks(true);
        rating.setSnapToTicks(true);
        rating.setHighValue(5);

        // Status
        VBox boxStatus = new VBox();
        boxStatus.setPrefHeight(70);
        Label lblStatus = new Label("Status:");

        VBox.setMargin(lblStatus, new Insets(0, 0, 10, 0));

        boxStatus.getChildren().addAll(lblStatus);

        VBox.setMargin(boxStatus, new Insets(10, 0, 0,0));
        boxStatus.setFillWidth(true);

        GridPane gridContent = new GridPane();
        gridContent.setAlignment(Pos.CENTER);

        RadioButton all = new RadioButton("All");
        all.setUserData(Status.ALL);
        all.setSelected(true);
        RadioButton free = new RadioButton("Free");
        free.setUserData(Status.FREE);
        free.getStyleClass().addAll("radio-success");
        RadioButton busy = new RadioButton("Busy");
        busy.setUserData(Status.BUSY);
        busy.getStyleClass().addAll("radio-danger");
        RadioButton unavailable = new RadioButton("Unavailable");
        unavailable.setUserData(Status.UNAVAILABLE);
        unavailable.getStyleClass().addAll("radio-amber");

        all.setToggleGroup(status);
        free.setToggleGroup(status);
        busy.setToggleGroup(status);
        unavailable.setToggleGroup(status);

        gridContent.add(all, 0, 0);
        gridContent.add(free, 1, 0);
        gridContent.add(busy, 2, 0);
        gridContent.add(unavailable, 3, 0);

        gridContent.setHgap(10);

        GridPane.setHalignment(busy, HPos.CENTER);
        GridPane.setHalignment(free, HPos.CENTER);
        GridPane.setHalignment(unavailable, HPos.RIGHT);

        boxStatus.getChildren().addAll(gridContent);

        content.getChildren().addAll(boxPrice, boxRating, boxStatus);
        root.setContent(content);

        return root;
    }

}
