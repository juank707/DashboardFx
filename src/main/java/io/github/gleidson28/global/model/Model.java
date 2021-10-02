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
package io.github.gleidson28.global.model;

import javafx.beans.property.*;

/**
 *
 * Base class for creation models.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/04/2019
 */
public class Model {

    private final IntegerProperty id            = new SimpleIntegerProperty();
    private final StringProperty  name          = new SimpleStringProperty();
    private final StringProperty  description   = new SimpleStringProperty();

    private final BooleanProperty valid         = new SimpleBooleanProperty();
    private final StringProperty  error         = new SimpleStringProperty();

    public Model() {

    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }

    public String getError() {
        return error.get();
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void setError(String error) {
        this.error.set(error);
    }

    public boolean compare(Model model) {

//        System.out.println();
//        System.out.println("this.getId() = " + this.getId());
//        System.out.println("this.getName() = " + this.getName());
//        System.out.println("model.getId() = " + model.getId());
//        System.out.println("model.getName() = " + model.getName());
//        System.out.println();



        return model != null && this.getId() == model.getId();
    }
}
