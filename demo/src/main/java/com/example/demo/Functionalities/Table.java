package com.example.demo.Functionalities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {
    private int tableNum;
    private int capacity;
    private int availability;
    private Connection conn;

    public Table(int tableNum) {
        this.tableNum = tableNum;
        this.capacity = 2;

        Connectivity c = new Connectivity();
        conn = c.getConnection();

    }

    public boolean getAvailability() throws SQLException {
        Statement stm = conn.createStatement();;
        ResultSet rs = stm.executeQuery(String.format("SELECT availability FROM Tables WHERE table_id=%d", tableNum));

        if (rs.next()){
            availability = rs.getInt("availability");
        }
        return availability == 1;
    }

    public void setAvailability() throws SQLException {
        availability = availability == 1 ? 0 : 1;

        AdminConnectivity ac = new AdminConnectivity();
        Connection adminConn = ac.getConnection();
        Statement stm = adminConn.createStatement();
        stm.executeUpdate(String.format("UPDATE Tables SET availability=%d WHERE table_id=%d", availability, tableNum));

    }
}
