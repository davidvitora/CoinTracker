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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
            if(frame.buttonOK.getText().equals("Deletar")){
                int indexCategoryToDelete = frame.getCategory().getId();
                try {
                    if(frame.getCategorysIdsList().size() == 1){
                        frame.getDao().deleteCategory(indexCategoryToDelete);
                        frame.dispose();
                    }else{
                        frame.iteratorDeleteRoutine(indexCategoryToDelete);
                        frame.setCategory(frame.getDao().getCategory(frame.getCurrentID()));
                        frame.readCategory(frame.getCategory());
                        frame.modoEdicao(false);
                        try{
                            frame.getDao().deleteCategory(indexCategoryToDelete);
                            frame.updateCategoryIds();
                            frame.updateIterator();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro ao setar categoria");
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(RegisterAccountActionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
            if(!frame.validar()){
                return;
            }
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
            frame.modoEdicao(true);
        }
        else if("Delete".equals(e.getActionCommand())){
            LogMaker.log("Categoria " + this.frame.getCategory().getDescription() + "Excluida pelo usuário "
            + this.frame.getsession().getUser());
            frame.Delete();
        }else if("Novo".equals(e.getActionCommand())){
            if(frame.buttonNovo.getText().equals("Novo")){
                frame.novo();
                LogMaker.log("Nova categoria está sendo criada pelo usuário "
                + this.frame.getsession().getUser());
            }else{
                if(frame.getCategorysIdsList().size() == 0){
                    frame.dispose();
                }else{
                    frame.modoEdicao(false);
                    try {
                        frame.readCategory(frame.getDao().getCategory(frame.getCurrentID()));
                    } catch (Exception ex) {
                        Logger.getLogger(RegisterCategoryActionListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
    }
    
    

}
