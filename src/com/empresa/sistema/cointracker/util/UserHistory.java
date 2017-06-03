package com.empresa.sistema.cointracker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.swing.JOptionPane;


public class UserHistory {
    
    public static void saveUser(String user) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            fileWriter = new FileWriter("user.txt", false);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(user);
            bufferedWriter.flush();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }    
        finally{
            try{
                bufferedWriter.close();
            }catch(IOException e){
                
            }
        }
        
    }
    public static String readUser() throws FileNotFoundException, IOException{
        try{
        FileReader fileReader = new FileReader("user.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            return bufferedReader.readLine();
        }
        bufferedReader.close();
        return null;
        }
        catch(Exception e){
           return null; 
        }
    }
    
}
