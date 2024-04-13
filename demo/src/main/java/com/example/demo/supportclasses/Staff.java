package com.example.demo.supportclasses;

import com.example.demo.connectivity.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Staff {
    private int staffID;
    private String name;
    private String role;

    public Staff(int staffID) throws SQLException {
        Database db = new Database();

        this.staffID = staffID;

        ArrayList<ArrayList<String>> staffValues = db.selectValues(String.format("SELECT name, role FROM Staff WHERE staff_id = %d", staffID));

        this.name = staffValues.get(0).get(0);
        this.role = staffValues.get(0).get(1);

        db.close();
    }

    public int getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void updateRole(int id, String newRole) throws SQLException {
        Database db = new Database("in2033t01_a", "CtYS1azKU-8");

        db.insertValues(String.format("UPDATE Staff " +
                "SET role = '%s' " +
                "WHERE staff_id = %d", newRole, id));

        db.close();
    }

    public void updatePassword(int id, String newPassword) throws SQLException {
        Database db = new Database("in2033t01_a", "CtYS1azKU-8");

        db.insertValues(String.format("UPDATE Credentials " +
                "SET password = '%s' " +
                "WHERE staff_id = %d", newPassword, id));

        db.close();
    }

    public static List<Staff> getAllStaff() throws SQLException {
        List<Staff> staff = new ArrayList<>();

        // Get all the IDs from the Staff table
        Database db = new Database();

        ArrayList<ArrayList<String>> staffIds = db.selectValues("SELECT staff_id FROM Staff");

        for (int i = 0; i < staffIds.size(); i++){
            staff.add(new Staff(Integer.parseInt(staffIds.get(i).get(0))));
        }

        db.close();

        return staff;
    }
}
