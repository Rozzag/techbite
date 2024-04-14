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

    public void delUser() throws SQLException {
        Database db = new Database("in2033t01_a", "CtYS1azKU-8");

        db.insertValues(String.format("DELETE FROM Credentials WHERE staff_id = %d", this.staffID));
        db.insertValues(String.format("DELETE FROM Staff WHERE staff_id = %d", this.staffID));

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

    public static Staff addMember(String name, String username, String password, String role) throws SQLException {
        Database db = new Database();

        int maxId = Integer.parseInt(db.selectValues("SELECT MAX(staff_id) FROM Staff").get(0).get(0)) + 1;

        db.insertValues(String.format("INSERT INTO Staff VALUES (%d, '%s', '%s')", maxId, name, role));
//        int id = Integer.parseInt(db.selectValues("SELECT * FROM Staff ORDER BY staff_id DESC LIMIT 1").get(0).get(0));
        int maxCredID = Integer.parseInt(db.selectValues("SELECT MAX(credentials_id) FROM Credentials").get(0).get(0)) + 1;
        db.insertValues(String.format("INSERT INTO Credentials VALUES (%d, %d, '%s', '%s')", maxCredID, maxId, username, password));

        db.close();

        return new Staff(maxId);
    }
}
