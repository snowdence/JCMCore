package core.model;

import core.database.MDBConnector;

import java.sql.*;

public abstract class BaseModel {
    protected int ID = 0;
    protected String table = null;
    protected MDBConnector mdbConnector;
    protected Connection conn;
    public BaseModel(){
        mdbConnector = MDBConnector.getInstance();
        conn = mdbConnector.getConnection();

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public abstract boolean insert();
    public abstract boolean insert(String query, String condition);

    public abstract boolean update();//default by id
    public abstract boolean update(String query, String condition);//default by id

    public abstract boolean remove();
    public abstract boolean remove(String condition);

    public abstract boolean select();

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
