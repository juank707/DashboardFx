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

import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.model.Professional;
import io.github.gleidson28.global.model.Status;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
public final class DaoProfessional extends AbstractDao<Professional> {

    private final ListProperty<Professional> professionals =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private static DaoProfessional instance;

    public static DaoProfessional getInstance() {
        if(instance == null) {
            instance = new DaoProfessional();
        }
        return instance;
    }

    @Override
    public Professional find(long id) {
        return null;
    }

    @Override
    public boolean store(Professional professional) {

        try {
            connect();
            PreparedStatement prepare = prepare("insert into professional(name, lastName, avatar, price, status) values(?, ?, ?, ?, ?);");
            prepare.setString(1, professional.getName());
            prepare.setString(2, professional.getLastName());
            prepare.setString(3, professional.getAvatar().getPath());
            prepare.setBigDecimal(4, professional.getPrice());

            prepare.setString(5, String.valueOf(Status.convertChar(professional.getStatus())));

            prepare.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    @Override
    public boolean update(Professional professional) {
        try {

            connect();
            PreparedStatement prepare = prepare("update professional set name = ?, avatar = ?, " +
                    "price = ?, teams = ? where id = " + professional.getId() + "; ");

            prepare.setString(1, professional.getName());
            prepare.setString(2, professional.getAvatar().getPath());
            prepare.setBigDecimal(3, professional.getPrice());
            prepare.setString(4, professional.getTeams());

            prepare.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    public boolean executeQuery(String sql) throws SQLException {
        connect();
        executeSQL(sql);
        return result().first();
    }

    public ObservableList<Professional> getAll() {
        return professionals;
    }

    public Task<ObservableList<Professional>> getTask() {

        return new Task<ObservableList<Professional>>() {

            @Override
            protected void failed() {
                System.out.println("failed");
                System.out.println(super.getException());
                super.failed();
            }

            @Override
            protected ObservableList<Professional> call() {

                professionals.clear();
                connect();
                executeSQL("select * from professional;");
                ResultSet result = result();

                try {
                    while (result.next()) {

                        Professional professional = new Professional();
                        professional.setId(result.getInt("id"));
                        professional.setName(result.getString("name"));
                        professional.setLastName(result.getString("lastName"));

                        String path = result.getString("avatar");

                        if(path.startsWith("theme")) {
                            professional.setAvatar(new Avatar(path));
                        } else {

                            File file = new File(path);
                            FileInputStream fileInputStream = new FileInputStream(file);
                            Avatar avatar = new Avatar(fileInputStream, path);
                            professional.setAvatar(avatar);

                        }

                        if(result.getString("status").equals("F")) professional.setStatus(Status.FREE);
                        else if(result.getString("status").equals("U")) professional.setStatus(Status.UNAVAILABLE);
                        else professional.setStatus(Status.BUSY);

                        professional.setPrice(result.getBigDecimal("price"));

                        professional.setRating(result.getFloat("rating"));
                        professional.setLocation(result.getString("location"));
                        professional.setEmail(result.getString("email"));
                        professional.setTeams(result.getString("teams"));

                        professionals.get().add(professional);

                        Thread.sleep(100);

                    }
                } catch (SQLException | FileNotFoundException | InterruptedException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return professionals;
            }
        };
    }
}


