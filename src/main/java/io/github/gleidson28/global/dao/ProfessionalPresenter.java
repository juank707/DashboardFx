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

import io.github.gleidson28.global.model.Professional;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/08/2021
 */
public class ProfessionalPresenter {

    private final DaoProfessional dao = DaoProfessional.getInstance();

    public ObservableList<Professional> getAll() {
        return dao.getAll();
    }

    public Task<ObservableList<Professional>> getTask() {
        return dao.getTask();
    }

    public void save(Professional user) {
        dao.store(user);
    }

    public void edit(Professional user) {
        dao.update(user);
    }

}
