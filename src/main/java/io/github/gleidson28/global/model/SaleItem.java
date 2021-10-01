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

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
@SuppressWarnings("unused")
public class SaleItem extends Item {

    private final ObjectProperty<ItemType>      type        = new SimpleObjectProperty<>();
    private final IntegerProperty               quantity    = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal>    price       = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal>    total       = new SimpleObjectProperty<>(new BigDecimal(0));
    private final FloatProperty                 discount    = new SimpleFloatProperty();
    private final BooleanProperty               hasDiscount = new SimpleBooleanProperty(false);

    public SaleItem() {
        super();
    }

    public ItemType getType() {
        return type.get();
    }

    public ObjectProperty<ItemType> typeProperty() {
        return type;
    }

    public void setType(ItemType type) {
        this.type.set(type);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }


    public BigDecimal getTotal() {
        return total.get();
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total.set(total);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return  price ;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public float getDiscount() {
        return discount.get();
    }

    public FloatProperty discountProperty() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount.set(discount);
    }

    public boolean hasDiscount() {
        return hasDiscount.get();
    }

    public BooleanProperty hasDiscountProperty() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount.set(hasDiscount);
    }
}
