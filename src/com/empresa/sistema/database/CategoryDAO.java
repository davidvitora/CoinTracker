package com.empresa.sistema.database;

import cointracker.util.LogMaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Category;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoryDAO {
    
    Connection con;
    
    public int getCategorysCount() throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(id) from category";
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
    
    public void novo(){
        
    }
    
    public List<Integer> getCategorysId() throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "SELECT id from category";
        PreparedStatement prepare;
        List<Integer> categorysIds = new ArrayList<Integer>();
        try{
            prepare = con.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                categorysIds.add(result.getInt("id"));
            }
            con.commit();
            return categorysIds;
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return null;
    }
    
    
    public Category getCategory(int id) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "select * from category where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            Category category;
            while(result.next()){
                category = new Category();
                category.setId(result.getInt("id"));
                category.setDescription(result.getString("description"));
                category.setType(result.getString("type"));
                return category;
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
    
    public boolean updateCategory(Category category) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "update category set description = ?, id = ?, type = ? "
                + "where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, category.getDescription());
            prepare.setInt(2, category.getId());
            prepare.setString(3, category.getType());
            prepare.setInt(4, category.getId());
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
    
    public boolean deleteCategory(int id) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "delete from category where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setInt(1, id);
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
    
    public boolean saveCategory(Category category) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "insert into category(description,id,type)"
                + "values (?,?,?)";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, category.getDescription());
            prepare.setInt(2, category.getId());
            prepare.setString(3, category.getType());
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
