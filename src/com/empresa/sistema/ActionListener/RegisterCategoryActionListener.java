/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.ActionListener;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterCategoryInternalJFrame;
import cointracker.util.LogMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Gabriel
 */
public class RegisterCategoryActionListener implements ActionListener {
    RegisterCategoryInternalJFrame frame;
    
    public RegisterCategoryActionListener(RegisterCategoryInternalJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("OK".equals(e.getActionCommand())){
            frame.SaveCategory();
            LogMaker.log("Salva a categoria \n"
                    + "Id : " + frame.getCategory().getId() + "\n"
                    + "Descrição : " + frame.getCategory().getDescription() + "\n"
                    + "Tipo : " + frame.getCategory().getType() + "\n pelo usuário "+ this.frame.getsession().getUser().getName());
        }
        else if("Back".equals(e.getActionCommand())){
            frame.Back();
        }
        else if("Forward".equals(e.getActionCommand())){
            frame.Forward();
        }
        else if("Edit".equals(e.getActionCommand())){
            frame.Edit();
        }
        else if("Delete".equals(e.getActionCommand())){
            LogMaker.log("Categoria " + this.frame.getCategory().getDescription() + "Excluida pelo usuário "
            + this.frame.getsession().getUser());
            frame.Delete();
        }
        
    }
    
    

}
