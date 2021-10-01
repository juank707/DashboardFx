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

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/09/2020
 */
@SuppressWarnings("unused")
public final class Product extends SaleItem {

    private StringProperty line = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> cost = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> registered = new SimpleObjectProperty<>();
    private IntegerProperty sold = new SimpleIntegerProperty();
    private IntegerProperty rating = new SimpleIntegerProperty();


    public Product() {
        super();
    }

    public String getLine() {
        return line.get();
    }

    public StringProperty lineProperty() {
        return line;
    }

    public void setLine(String line) {
        this.line.set(line);
    }

    public BigDecimal getCost() {
        return cost.get();
    }

    public ObjectProperty<BigDecimal> costProperty() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost.set(cost);
    }

    public LocalDate getRegistered() {
        return registered.get();
    }

    public ObjectProperty<LocalDate> registeredProperty() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered.set(registered);
    }

    public int getSold() {
        return sold.get();
    }

    public IntegerProperty soldProperty() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold.set(sold);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    @Override
    public boolean isValid() {
        return this.getName() != null && this.getName().length() > 3;
    }

    @Override
    public String toString() {
        return super.getName() != null ? super.getName() : super.toString();
    }
}
