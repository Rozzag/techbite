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

    // retrieves the table information for a table number
    public Table(int tableNum) {
        this.tableNum = tableNum;
        this.capacity = 2;

        db = new Database();

    }

    // returns true if the table is available and false otherwise
    public boolean getAvailability() throws SQLException {
        ArrayList<ArrayList<String>> getAv = this.db.selectValues(String.format("SELECT availability FROM Tables WHERE table_id=%d", tableNum));

        availability = Integer.parseInt(getAv.get(0).get(0));

        return availability == 1;
    }

    // change the availability of a table once the status of a client on the table
    // changes
    public void setAvailability() throws SQLException {
        availability = availability == 1 ? 0 : 1;

        this.db.insertValues(String.format("UPDATE Tables SET availability=%d WHERE table_id=%d", availability, tableNum));

    }
}
