package com.empresa.sistema.contracker.listener;

import com.empresa.sistema.cointracker.frames.LoginJFrame;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import cointracker.util.LogMaker;
import com.empresa.sistema.cointracker.util.UserHistory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ActionListenerLoginJFrame implements ActionListener {
    
    public LoginJFrame frame;
    
    public ActionListenerLoginJFrame(LoginJFrame frame){
        LogMaker.log("Tela de login aberta");
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(frame.buttonLogin)){
            if(frame.textPassword.getText().equals("admin") && frame.textUser.getText().equals("admin")){
                frame.user.setName("admin");
                new MainJFrame(frame.user).setVisible(true);
                frame.dispose();
                LogMaker.log( "O usuário " + frame.user.getName() + " realizou o login");
                UserHistory.saveUser(frame.user.getName());
                
            }
            else if(frame.textPassword.getText().equals("gabriel") && frame.textUser.getText().equals("gabriel")){
                frame.user.setName("gabriel");
                new MainJFrame(frame.user).setVisible(true);
                frame.dispose();
                LogMaker.log( "O usuário " + frame.user.getName() + " realizou o login");
                UserHistory.saveUser(frame.user.getName());
            }
            else if(frame.textPassword.getText().equals("david") && frame.textUser.getText().equals("david")){
                frame.user.setName("david");
                new MainJFrame(frame.user).setVisible(true);
                frame.dispose();
                LogMaker.log( "O usuário " + frame.user.getName() + " realizou o login");
                UserHistory.saveUser(frame.user.getName());
            } 
            else{
                frame.labelLoginMenssage.setText("Usuário ou senha incorreto");
                LogMaker.log("Usuário ou senha digitados incorretamente, login falhou");
            }
        }
    }
    
}
