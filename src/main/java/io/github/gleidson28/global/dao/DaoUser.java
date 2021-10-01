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

import io.github.gleidson28.GNAvatarView;
import io.github.gleidson28.global.enhancement.Avatar;
import io.github.gleidson28.global.model.Status;
import io.github.gleidson28.global.model.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
public final class DaoUser extends AbstractDao<User> {

    private final ListProperty<User> users =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private final ObjectProperty<User> active = new SimpleObjectProperty<>();

    private static DaoUser instance;

    public static DaoUser getInstance() {
        if(instance == null) {
            instance = new DaoUser();
        }
        return instance;
    }

    private DaoUser() {
        User user = new User();
        user.setName("John Doe");
        user.setAvatar(new Avatar("theme/img/avatars/man@50.png"));
        active.set(user);
    }

    public User getActive() {
        return this.active.get();
    }

    @Override
    public User find(long id) {
        return null;
    }

    @Override
    public boolean store(User user) {

        try {
            connect();
            PreparedStatement prepare = prepare("insert into user(name, lastName, avatar) values(?, ?, ?);");
            prepare.setString(1, user.getName());
            prepare.setString(2, user.getLastName());
            prepare.setString(3, user.getAvatar().getPath());

            prepare.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        try {


            connect();
            PreparedStatement prepare = prepare("update user set name = ?, avatar = ? " +
                    "where id = " + user.getId() + "; ");

            prepare.setString(1, user.getName());
            prepare.setString(2, user.getAvatar().getPath());

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

    public ObservableList<User> getAll() {
        return users;
    }

    public Task<ObservableList<User>> getTask() {

        return new Task<ObservableList<User>>() {

            @Override
            protected void failed() {
                System.out.println("failed");
                System.out.println(super.getException());
                super.failed();
            }

            @Override
            protected ObservableList<User> call() {

                users.clear();
                connect();
                executeSQL("select * from user;");
                ResultSet result = result();

                try {
                    while (result.next()) {

                        User user = new User();
                        user.setId(result.getInt("id"));
                        user.setName(result.getString("name"));
                        user.setLastName(result.getString("lastName"));

                        String path = result.getString("avatar");

                        if(path.startsWith("theme")) {
                            user.setAvatar(new Avatar(path));
                        } else {

                            File file = new File(path);
                            FileInputStream fileInputStream = new FileInputStream(file);
                            Avatar avatar = new Avatar(fileInputStream, path);
                            user.setAvatar(avatar);

                        }

                        if(result.getString("status").equals("F")) user.setStatus(Status.FREE);
                        else if(result.getString("status").equals("U")) user.setStatus(Status.UNAVAILABLE);
                        else user.setStatus(Status.BUSY);

                        users.get().add(user);

                        Thread.sleep(100);

                    }
                } catch (SQLException | FileNotFoundException | InterruptedException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return users;
            }
        };
    }
}


