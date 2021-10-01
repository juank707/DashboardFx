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
package io.github.gleidson28.global.database;

import java.sql.*;
import java.util.TimeZone;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/01/2019
 */
@SuppressWarnings("unused")
public class DataConnection {

    private Connection  connection;
    private Statement   statement;
    private ResultSet   result;

    private String  driver          = "com.mysql.cj.jdbc.Driver";
    private String  user            = "root";
    private String  password        = "radar";
    private int     port            =  3308;
    private String  host            = "localhost:" + port;
    private String  database        = "dash";
    private String  timeZone        = String.valueOf(TimeZone.getDefault().toZoneId());
    private String  url             = "jdbc:mysql://" + host + "/" + database + "?useUnicode=true&characterEncoding=utf8&serverTimezone=" + timeZone;

    public DataConnection() {

    }

    public DataConnection(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public DataConnection(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void init() {
        try {
            System.setProperty("jdbc.Driver", driver);
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, user, password);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
//            init();
        }
    }

    public boolean close() {
        try {
            if ((this.getResult() != null) && (this.statement != null)) {
                this.getResult().close();
                this.statement.close();
            }
            this.getConnection().close();
            connection.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int insertSQL(String sql) {
        int status = 0;
        try {
            this.setStatement(getConnection().createStatement());
            this.getStatement().executeUpdate(sql);
            this.setResult(this.getStatement().executeQuery("SELECT LAST_INSERT_ID();"));

            while (this.result.next()) {
                status = this.result.getInt(1);
            }
            return status;
        } catch (SQLException ex) {
            System.err.println("Error : " + ex);
            return status;
        }
    }

    public synchronized boolean executeSQL(String SQL) throws SQLException {
        try {
            this.setStatement(getConnection().createStatement());
            this.setResult(getStatement().executeQuery(SQL));
            return true;
        } catch (SQLException ex) {
            System.err.println("Error : " + ex);
            ex.printStackTrace();
            return false;
        } finally {
        }
    }

    public boolean isClosed(){
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
