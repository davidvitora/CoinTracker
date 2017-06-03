package cointracker.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.swing.JOptionPane;


public class LogMaker {
    
    public static void log(String log) {
        Date date;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            date = new Date(System.currentTimeMillis());
            log =  date.toString() + " | " + log + "\n";
            fileWriter = new FileWriter("log.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(log);
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
    
}
