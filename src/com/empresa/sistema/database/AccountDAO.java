/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.database;

import cointracker.util.LogMaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.empresa.sistema.cointracker.entities.Account;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author David .V
 */
public class AccountDAO {
    
    Connection con;
    
    public int getAccountsCount() throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(id) from account";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                return result.getInt(0);
            }
            con.commit();
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return 0;
    }
    
    public List<Integer> getAccountsId() throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "SELECT id from account";
        PreparedStatement prepare;
        List<Integer> accountsIds = new ArrayList<Integer>();
        try{
            prepare = con.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                accountsIds.add(result.getInt("id"));
            }
            con.commit();
            return accountsIds;
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return null;
    }
    
    
    public Account getAccount(int id) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "select * from account where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            Account account;
            while(result.next()){
                account = new Account();
                account.setId(result.getInt("id"));
                account.setDescription(result.getString("description"));
                account.setDocument(result.getString("document"));
                account.setOpeningBalance(result.getDouble("openingbalance"));
                account.setBalance(result.getDouble("balance"));
                account.setOwnerName(result.getString("ownername"));
                account.setOwnerType(result.getInt("ownertype"));
                account.setType(result.getInt("type"));
                return account;
            }
            con.commit();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
            con.rollback();
        }finally{
            con.close();
        }    
        return null;
    }
    
    public boolean updateAccount(Account account) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "update account set description = ?, document = ?, "
                + "openingbalance = ?, balance = ?, ownertype = ?, type = ? , ownername = ?, id = ? "
                + "where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, account.getDescription());
            prepare.setString(2, account.getDocument());
            prepare.setDouble(3, account.getOpeningBalance());
            prepare.setDouble(4, account.getBalance());
            prepare.setInt(5, account.getOwnerType());
            prepare.setInt(6, account.getType());
            prepare.setString(7, account.getOwnerName());
            prepare.setInt(8, account.getId());
            prepare.setInt(9, account.getId());
            LogMaker.log(prepare.toString());
            prepare.execute();
            con.commit();
            con.close();
            return true;
        }catch(SQLException ex){
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
            con.close(); 
        }
        return false;
    }
    
    public boolean saveAccount(Account account) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "insert into account (description,document,openingbalance,balance,ownertype,type,ownername,id)"
                + "values (?,?,?,?,?,?,?,?)";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, account.getDescription());
            prepare.setString(2, account.getDocument());
            prepare.setDouble(3, account.getOpeningBalance());
            prepare.setDouble(4, account.getBalance());
            prepare.setInt(5, account.getOwnerType());
            prepare.setInt(6, account.getType());
            prepare.setString(7, account.getOwnerName());
            prepare.setInt(8, account.getId());
            LogMaker.log(prepare.toString());
            prepare.execute();
            con.commit();
            con.close();
            return true;
        }catch(SQLException ex){
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
            con.close(); 
        }
        return false; 
    }
}
