package com.empresa.sistema.database;

import com.empresa.sistema.cointracker.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    Connection con;
    
    public boolean Autenticate(String login, String senha) throws Exception{       
        con = ConnectionFactory.getConnection();
        String sql = "select * from user where login = ? and senha = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, login);
            prepare.setString(2, senha);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                return true;
            }
            con.commit();
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return false;
    }
    
    public User getUser(String login, String senha) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "select * from user where login = ? and senha = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, login);
            prepare.setString(2, senha);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                User user = new User();
                user.setName(result.getString("name"));
                user.setLogin(result.getString("login"));
                user.setSenha(result.getString("senha"));
                return user;
            }
            con.commit();
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return null;
    }
    
}
