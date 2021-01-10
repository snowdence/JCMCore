package core.model;

import java.sql.*;

public class UserModel extends BaseModel {

    protected String Username;
    protected String Password;
    protected String FullName;
    protected String AvatarPath;
    protected int Role;

    public UserModel(int id){
        this.table = "user";
        this.setID(id);
    }
    public UserModel(String username, String password, String fullName, String avatarPath, int role) {
        this.table = "user";
        this.Password = password;
        this.Username = username;
        this.FullName = fullName;
        this.AvatarPath = avatarPath;
        this.Role = role;
    }




    //region get set
    public void setRole(int role) {
        Role = role;
    }

    public int getRole() {
        return Role;
    }


    public void setAvatarPath(String avatarPath) {
        AvatarPath = avatarPath;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


    public void setUsername(String username) {
        Username = username;
    }



    public String getAvatarPath() {
        return AvatarPath;
    }

    public String getFullName() {
        return FullName;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    //endregion



    @Override
    public boolean insert() {
        return this.insert(null,null);
    }

    @Override
    public boolean insert(String query, String whereCondition) {
        String queryString = String.format("INSERT INTO %s (username, password, full_name, avatar_path, role) VALUES ( '%s', '%s', '%s' ,'%s' ,%d) ", getTable(), getUsername(), getPassword(), getFullName(), getAvatarPath(), getRole());

        if(query != null){
            queryString = query;
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            //System.out.println(queryString);
            int row_effect = stmt.executeUpdate(queryString);
            return (row_effect > 0 ? true : false);
        } catch (SQLException throwables) {
            System.out.println("[DBHandleException]: Username không được trùng");
            return false;
        }
    }


    @Override
    public boolean update() {
        return update(null, null);
    }

    @Override
    public boolean update(String query, String condition) {
        String queryString = String.format("UPDATE %s SET username ='%s', password = '%s' , full_name ='%s', avatar_path ='%s', role =%d ", getTable(), getUsername(), getPassword(), getFullName(), getAvatarPath(), getRole());
        
        if(query != null){
            queryString = query;
        }

        if(condition == null){
            queryString += String.format("WHERE id =%d", getID());
        }
        else{
            queryString += condition;
        }
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            System.out.println(queryString);
            int row_effect = stmt.executeUpdate(queryString);
            return (row_effect > 0 ? true : false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DBHandleException]: Có lỗi xảy ra DB update()");
            return false;
        }
    }

    @Override
    public boolean remove() {
        return remove(null);
    }

    @Override
    public boolean remove(String condition) {
        String queryString = String.format("DELETE FROM %s ", getTable());
        if(condition == null){
            queryString += String.format("WHERE id = %d", getID());
        }
        else{
            queryString += condition;
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            System.out.println(queryString);
            int row_effect = stmt.executeUpdate(queryString);
            return (row_effect > 0 ? true : false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("[DBHandleException]: Có lỗi xảy ra DB update()");
            return false;
        }
    }

    @Override
    public boolean select() {
        return false;
    }


}

