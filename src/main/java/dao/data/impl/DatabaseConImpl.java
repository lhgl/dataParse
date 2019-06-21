package dao.data.impl;

import dao.data.DatabaseCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConImpl implements DatabaseCon {

    private Connection con = null;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }


    @Override
    public Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    @Override
    public Connection getConnection() {
        try {
            if (this.getCon() == null) {
                this.setCon(DriverManager.getConnection(
                        getURLConnection(), getUserConnection(), getPasswordConnection()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getCon();
    }

    @Override
    public void closeConnection() {
        try {
            if (getCon() != null) {
                getCon().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserConnection() {
        return "root";
    }

    @Override
    public String getURLConnection() {
        return "jdbc:mysql://localhost:3306/captura?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    @Override
    public String getPasswordConnection() {
        return "admin";
    }
}
