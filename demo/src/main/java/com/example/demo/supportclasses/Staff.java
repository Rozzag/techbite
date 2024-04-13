package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public class Staff {
    private int staffID;
    private String name;
    private String role;

    public Staff(int staffID) throws SQLException {
        Database db = new Database();

        this.staffID = staffID;

        ArrayList<ArrayList<String>> staffValues = db.selectValues(String.format("SELECT name, role FROM Staff, Credentials WHERE Staff.staff_id = %d AND Staff.staff_id = Credentials.staff_id", staffID));

        this.name = staffValues.get(0).get(0);
        this.role = staffValues.get(0).get(1);
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
