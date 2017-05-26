package com.empresa.sistema.listener;

import com.empresa.sistema.frames.LoginJFrame;
import com.empresa.sistema.frames.MainJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ActionListenerLoginJFrame implements ActionListener {
    
    public LoginJFrame frame;
    
    public ActionListenerLoginJFrame(LoginJFrame frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(frame.buttonLogin)){
            if(frame.textPassword.getText().equals("admin") && frame.textUser.getText().equals("admin")){
                new MainJFrame().setVisible(true);
                frame.dispose();
            }else{
                frame.labelLoginMenssage.setText("Usuário ou senha incorreto");

            }
        }
    }
    
}
