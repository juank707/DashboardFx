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

import io.github.gleidson28.global.database.DataConnection;
import io.github.gleidson28.global.model.Model;
import io.github.gleidson28.global.exceptions.SQLQueryError;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/12/2020
 */
public abstract class AbstractDao<T extends Model> implements Dao<T> {

    private final DataConnection dataConnection =
            new DataConnection();

    @Deprecated
    protected Connection getConnection() {
        return this.dataConnection.getConnection();
    }


    protected Statement getStatement() {
        return this.dataConnection.getStatement();
    }

    protected void connect() {
        dataConnection.init();
    }

    boolean isClosed() {
        return dataConnection.isClosed();
    }

    void disconnect() {
        dataConnection.close();
    }

    protected boolean executeSQL(String sql) {
        try {
            return dataConnection.executeSQL(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    protected ResultSet result() {
        return dataConnection.getResult();
    }

    protected PreparedStatement prepare(String sql) {
        try {
            return dataConnection.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(T model) {
        boolean result = false;
        try {
            result = prepare("delete from " +
                    model.getClass().getSimpleName().toLowerCase() +
                    " where id like '" + model.getId() + "';")
            .execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * Default store for model with non foreign keys.
     */
    @Override
    public boolean store(T model) throws SQLQueryError {
        String table = model.getClass().getSimpleName().toLowerCase();
        Field[] params = model.getClass().getDeclaredFields();

        StringBuilder sql = new StringBuilder("insert into " + table + "(");
        sql.append("name, ");

        for (int i = 0; i < params.length; i++){ // kick id
            if(i + 1 != params.length){
                sql.append(params[i].getName()).append(", ");
            } else {
                sql.append(params[i].getName()).append(") values(");
            }
        }

        for(int i = 0; i < (params.length + 1); i++){
            if(i + 1 != params.length){
                sql.append("?, ");
            } else {
                sql.append("?);");
            }
        }

        try {
            return prepare(sql.toString()).execute();
        } catch (SQLException e) {
            throw new SQLQueryError("SQLQueryError: " ,
                    String.format("The %s contains a foreign key. Implements custom store operation.",
                            table));
        }
    }

    @Override
    public boolean update(T model) throws SQLQueryError {
        String table = model.getClass().getSimpleName().toLowerCase();
        Field[] params = model.getClass().getDeclaredFields();


        StringBuilder sql = new StringBuilder("update " + table + " set ");
        sql.append("name, ");

        for (int i = 0; i < (params.length + 1); i++){ // kick id
            if(i + 1 != params.length){
                sql.append(params[i].getName()).append("= ?, ");
            } else {
                sql.append(params[i].getName()).append(" = ? where id like ").append(model.getId()).append(";");
            }
        }

        try {
            return prepare(sql.toString()).execute();
        } catch (SQLException e) {
            throw new SQLQueryError("SQLQueryError: " ,
                    String.format("The %s contains a foreign key. Implements custom update operation.",
                            table));
        }
    }

    public boolean commit() {
        try {
            dataConnection.getConnection().setAutoCommit(false); // For tests
            dataConnection.getConnection().commit();
            return true;
        } catch (SQLException throwables) {

            try {
                dataConnection.getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            throwables.printStackTrace();
            return false;
        }
    }


}
