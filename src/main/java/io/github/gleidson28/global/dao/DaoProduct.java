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
package io.github.gleidson28.global.dao;

import io.github.gleidson28.global.model.Product;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
public final class DaoProduct extends AbstractDao<Product> {

    private ListProperty<Product> products =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    DaoProduct(){
       products.get().addListener(new ListChangeListener<Product>() {
           @Override
           public void onChanged(Change<? extends Product> c) {

               System.out.println(c.getList());

//               if (c.next()) {
//
//               }
           }
       });
    }


    protected void bind(ObjectProperty<ObservableList<Product>> listProperty) {
        listProperty.bind(products);
    }

    @Override
    public Product find(long id) {
        return null;
    }

    @Override
    public boolean store(Product product) {

        try {
            connect();
            PreparedStatement prepare = prepare("insert into product(name, line) values(?, ?);");
            prepare.setString(1, product.getName());
            prepare.setString(2, product.getLine());

            System.out.println(prepare.execute());

            System.out.println("product = " + product);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    public boolean executeQuery(String sql) throws SQLException {
        connect();
        executeSQL(sql);
        return result().first();
    }


    protected void readAll() {

        connect();
        executeSQL("select * from product;");
        ResultSet result = result();

        try {
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setName(result.getString("name"));
                product.setPrice(result.getBigDecimal("price"));
                product.setDescription(result.getString("description"));
                product.setDiscount(result.getFloat("discount"));
                products.get().add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }

    }

    public ObservableList<Product> getProducts() {
        return products.get();
    }


    public synchronized Task<ObservableList<Product>> getTask() {

//        products.set(FXCollections.observableArrayList());

        return new Task<ObservableList<Product>>() {

            @Override
            protected ObservableList<Product> call() throws Exception {

                products.clear();
                connect();
                executeSQL("select * from product;");
                ResultSet result = result();

                try {
                    while (result.next()) {

                        Product product = new Product();
                        product.setName(result.getString("name"));
                        product.setPrice(result.getBigDecimal("price"));
                        product.setDescription(result.getString("description"));
                        product.setDiscount(result.getFloat("discount"));
                        product.setLine(result.getString("line"));
                        products.get().add(product);

                        Thread.sleep(100);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return products;
            }
        };
    }
}


