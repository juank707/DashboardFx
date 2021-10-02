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

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/09/2021
 */
public enum Status {

    ALL, BUSY, FREE, UNAVAILABLE;

    public static Status convertString(String _status) {
        if(_status.equalsIgnoreCase("ALL")) {
            return Status.ALL;
        } else if(_status.equalsIgnoreCase("BUSY")) {
            return Status.BUSY;
        } else if(_status.equalsIgnoreCase("FREE")) {
            return Status.FREE;
        } else return Status.UNAVAILABLE;
    }

    public static char convertChar(Status status) {
        switch (status) {
            case FREE: return 'F';
            case BUSY: return 'B';
            case UNAVAILABLE: return 'U';
        }
        return 'A';
    }


    @Override
    public String toString() {
        String concat = super.toString().substring(0,1);
        String replace = super.toString().substring(1);
        return concat + replace.toLowerCase();
    }
}
