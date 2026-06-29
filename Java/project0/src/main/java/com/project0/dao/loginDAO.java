package com.project0.dao;
import com.project0.util.ConnectManager;
import java.sql.*;

public class loginDAO {

    @SuppressWarnings("unused")
    private String username;
    @SuppressWarnings("unused")
    private String password;

    public boolean login(String username, String password){
        this.username = username;
        this.password = password;

        String sql = "SELECT * FROM users WHERE username=? AND password=? AND role='manager'";
        
        try(
            Connection conn = ConnectManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        return rs.next();

        }catch(SQLException e){
        System.out.println(e.getMessage());
        return false;
    }
    }
}
