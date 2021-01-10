package core.test;

import core.database.MDBConnector;
import core.model.UserModel;

import java.sql.Connection;
import java.sql.*;

public class TDBConnection {
    public TDBConnection() {}
    public void testConnectToMySQL(){
        MDBConnector mdbConn = MDBConnector.getInstance();
        Connection conn = mdbConn.getConnection();
        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2)
                        + "  " + rs.getString(3));
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //use
    public void testInsertUser() {
        UserModel user = new UserModel("hocsinh1" ,"admin", "Học sinh", "1",2);
        boolean status = user.insert();
        System.out.println(status ? "OK" : "Error");

    }

    public void testUpdateUser() {
        UserModel user = new UserModel("admin" ,"admin", "Trần Minh Đức", "1",1);
        user.setID(1);
        user.setFullName("Đã thay đổi");
        boolean status = user.update(null,null); //update by current id full fields;
        //boolean status = user.update(null, String.format("WHERE username = '%s'", "admin"));
        System.out.println(status ? "OK" : "Error");
    }
    public void testRemoveUser() {
        UserModel user = new UserModel(1);
        boolean status = user.remove();//remove default by id
        //boolean status = user.remove("WHERE username='admin1'");//remove default by condition
        System.out.println(status ? "OK" : "Error");

    }
}
