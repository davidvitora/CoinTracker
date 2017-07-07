package com.empresa.sistema.database;

import cointracker.util.LogMaker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Provider;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProviderDAO {
    
    Connection con;
    
    public int getProviderCount() throws SQLException, Exception{
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
    
    public List<Integer> getProvidersId() throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "SELECT id from provider";
        PreparedStatement prepare;
        List<Integer> providersIds = new ArrayList<Integer>();
        try{
            prepare = con.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while(result.next()){
                providersIds.add(result.getInt("id"));
            }
            con.commit();
            return providersIds;
        }catch(SQLException ex){
            con.rollback();
        }finally{
            con.close();
        }    
        return null;
    }
    
    public boolean deleteProvider(int id) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "delete from provider where id = ?";
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
    
    
    public Provider getProvider(int id) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "select * from provider where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            Provider provider;
            while(result.next()){
                provider = new Provider();
                provider.setId(result.getInt("id"));
                provider.setName(result.getString("name"));
                provider.setDocument(result.getString("document"));
                provider.setType(result.getInt("type"));
                provider.setActivated(result.getBoolean("activated"));
                provider.setCountry(result.getInt("country"));
                provider.setState(result.getString("state"));
                provider.setCity(result.getString("city"));
                provider.setDistrict(result.getString("district"));
                provider.setNumber(result.getString("number"));
                return provider;
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
    
    public boolean updateProvider(Provider provider) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "update provider set name = ?, document = ?, "
                + "type = ? , id = ?, country = ?, state = ?,"
                + "city = ?, district = ?, street = ?, number = ?,"
                + "activated = ? "
                + "where id = ?";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare.setString(1, provider.getName());
            prepare.setString(2, provider.getDocument());
            prepare.setInt(3, provider.getType());
            prepare.setInt(4, provider.getId());
            prepare.setInt(5, provider.getCountry());
            prepare.setString(6, provider.getState());
            prepare.setString(7, provider.getCity());
            prepare.setString(8, provider.getDistrict());
            prepare.setString(9, provider.getStreet());
            prepare.setInt(10, 0);
            try{
                prepare.setInt(10, Integer.parseInt(provider.getNumber()));
            }catch(NumberFormatException ex){
                
            }
            prepare.setBoolean(11, provider.isActivated());
            prepare.setInt(12, provider.getId());
            LogMaker.log(prepare.toString());
            prepare.execute();
            con.commit();
            con.close();
            return true;
        }catch(SQLException ex){
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage() + "aaa");
            con.close(); 
        }
        return false;
    }
    
    public boolean saveProvider(Provider provider) throws SQLException, Exception{
        con = ConnectionFactory.getConnection();
        String sql = "insert into provider (name,document,type,id,country,state,city,district,street,number,activated)"
                + "values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepare;
        try{
            prepare = con.prepareStatement(sql);
            prepare = con.prepareStatement(sql);
            prepare.setString(1, provider.getName());
            prepare.setString(2, provider.getDocument());
            prepare.setInt(3, provider.getType());
            prepare.setInt(4, provider.getId());
            prepare.setInt(5, provider.getCountry());
            prepare.setString(6, provider.getState());
            prepare.setString(7, provider.getCity());
            prepare.setString(8, provider.getDistrict());
            prepare.setString(9, provider.getStreet());
            prepare.setInt(10, 0);
            try{
                prepare.setInt(10, Integer.parseInt(provider.getNumber()));
            }catch(NumberFormatException ex){
                
            }
            prepare.setBoolean(11, provider.isActivated());
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
