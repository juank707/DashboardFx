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

import io.github.gleidson28.global.enhancement.Avatar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * Base class for creation models.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/04/2019
 */
public class User extends Model {

    private final ObjectProperty<Avatar> avatar = new SimpleObjectProperty<>();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();

    public Image getAvatar(String sizeInPixes) {
        String path = avatar.get().getPath();
        if(path.contains("@")) {
            String sub = path.substring(path.indexOf("@") + 1);
            String extension = path.substring(path.indexOf("."));
            String construct = path.substring(0, path.lastIndexOf("@"));
            String _final = construct + "@" + sizeInPixes + extension;
//            avatar.set(_final);
            return new Avatar(_final);
        } else {
            return avatar.get();
        }
    }

    public Avatar getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(avatar);
    }

    public Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    @Override
    public boolean isValid() {
        return this.getName() != null && this.getName().length() >= 3
                && this.getLastName() != null && this.getLastName().length() >= 3;
    }
}
