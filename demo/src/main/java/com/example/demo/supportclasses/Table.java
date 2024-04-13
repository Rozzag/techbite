package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Table {
    private int tableNum;
    private int capacity;
    private int availability;
    private Database db;

    public Table(int tableNum) {
        this.tableNum = tableNum;
        this.capacity = 2;

        db = new Database();

    }

    public boolean getAvailability() throws SQLException {
        ArrayList<ArrayList<String>> getAv = this.db.selectValues(String.format("SELECT availability FROM Tables WHERE table_id=%d", tableNum));

        availability = Integer.parseInt(getAv.get(0).get(0));

        return availability == 1;
    }

    public void setAvailability() throws SQLException {
        availability = availability == 1 ? 0 : 1;

        this.db.insertValues(String.format("UPDATE Tables SET availability=%d WHERE table_id=%d", availability, tableNum));

    }
}
