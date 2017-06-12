package com.empresa.sistema.ActionListener;

import com.empresa.sistema.cointracker.frames.LoginJFrame;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import cointracker.util.LogMaker;
import com.empresa.sistema.cointracker.entities.Session;
import com.empresa.sistema.cointracker.entities.User;
import com.empresa.sistema.cointracker.util.UserHistory;
import com.empresa.sistema.database.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class ActionListenerLoginJFrame implements ActionListener {
    
    public LoginJFrame frame;
    UserDAO dao;
    
    public ActionListenerLoginJFrame(){
        
    }
    
    public ActionListenerLoginJFrame(LoginJFrame frame){
        LogMaker.log("Tela de login aberta");
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dao = new UserDAO();
        if(e.getSource().equals(frame.buttonLogin)){
            try {
                User user = dao.getUser(frame.textUser.getText(), frame.textPassword.getText());
                if(user != null){
                    Session session = new Session();
                    session.setUser(user);
                    new MainJFrame(session).setVisible(true);
                    frame.dispose();
                    LogMaker.log( "O usuário " + frame.user.getName() + " realizou o login");
                    UserHistory.saveUser(frame.user.getName());
                }
                else{
                    frame.labelLoginMenssage.setText("Usuário ou senha incorreto");
                    LogMaker.log("Usuário ou senha digitados incorretamente, login falhou");
                }
            } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    LogMaker.log(ex.getMessage());
            }
        }
    }
    
}
