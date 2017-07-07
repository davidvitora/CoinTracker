package com.empresa.sistema.ActionListener;

import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Provider;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterProviderJInternalFrame;
import cointracker.util.LogMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RegisterProviderActionListener implements ActionListener {
    
    RegisterProviderJInternalFrame frame;

    public RegisterProviderActionListener(RegisterProviderJInternalFrame frame) {
        this.frame = frame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("OkRegisterProvider".equals(e.getActionCommand())){
            if(frame.buttonOk.getText().equals("Deletar")){
                int indexProviderToDelete = frame.getProvider().getId();
                try {
                    if(frame.getProvidersIdsList().size() == 1){
                        frame.getDao().deleteProvider(indexProviderToDelete);
                        frame.dispose();
                    }else{
                        frame.iteratorDeleteRoutine(indexProviderToDelete);
                        frame.setProvider(frame.getDao().getProvider(frame.getIndexProvider()));
                        frame.readProvider(frame.getProvider());
                        frame.setEditMode(false);
                        try{
                            frame.getDao().deleteProvider(indexProviderToDelete);
                            frame.updateProvidersIds();
                            frame.updateIterator();
                        }catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro ao setar conta");
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
            frame.saveChanges();
            frame.setEditMode(false);
            frame.updateProvidersIds();
            frame.updateIterator();
            frame.setIndexProvider(frame.getProvidersIdsList().indexOf(frame.getProvider().getId()));
            logSaveProvider();
        }else if("buttonEditCliked".equals(e.getActionCommand())){
            if(frame.buttonEdit.getText().equals("Editar")){
                frame.setEditMode(true);
                LogMaker.log("Editando fornecedor de codigo " + frame.getProvider().getId());
            }else{
                frame.setEditMode(false);
                try {
                    frame.readProvider(frame.getProvider());
                } catch (Exception ex) {
                    LogMaker.log(ex.getMessage());
                }
                LogMaker.log("Cancelada edição de fornecedor ");
            }
        }
        else if("buttonLeftCliked".equals(e.getActionCommand())){
            int indexOldProvider = frame.getProvider().getId();
            if(frame.getIterator().hasPrevious()){
                frame.setIndexProvider(frame.getIterator().previous());
                if(indexOldProvider == frame.getIndexProvider()){
                    if(frame.getIterator().hasPrevious()){
                        frame.setIndexProvider(frame.getIterator().previous());
                    }
                }
                try{
                    frame.setProvider(frame.getDao().getProvider(frame.getIndexProvider()));
                    frame.readProvider(frame.getProvider());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        }
        else if("buttonRigthCliked".equals(e.getActionCommand())){
            int indexOldProvider = frame.getProvider().getId();
            if(frame.getIterator().hasNext()){
                frame.setIndexProvider(frame.getIterator().next());
                if(indexOldProvider == frame.getIndexProvider()){
                    if(frame.getIterator().hasNext()){
                        frame.setIndexProvider(frame.getIterator().next());
                    }
                }
                try{
                    frame.setProvider(frame.getDao().getProvider(frame.getIndexProvider()));
                    frame.readProvider(frame.getProvider());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        }else if("buttonNewCliked".equals(e.getActionCommand())){
            if(frame.buttonNew.getText().equals("Novo")){
                frame.setProvider(new Provider());
                frame.getProvider().setId(frame.verifyOpenId());
                frame.readProvider(frame.getProvider());
                frame.setEditMode(true);
                frame.buttonEdit.setEnabled(false);
                LogMaker.log("Criando nova conta");
            }else{
                
                LogMaker.log("Desfeita a edição de fornecedor");
                frame.readProvider(frame.getProvider());
            }
        }else if("comboBoxDocumentAction".equals(e.getActionCommand())){
            if(frame.getProvider().getType() == 0){
                frame.labelDocumentOwner.setText("CPF : ");            
            }else{
                frame.labelDocumentOwner.setText("CNPJ : ");  
            }
        }
    }
    
    public void logSaveProvider(){
        LogMaker.log("Fornecedor salvo \n"
                    + "Id : " + frame.getProvider().getId() + "\n"
                    + "Número do documento : " + frame.getProvider().getDocument() + "\n"
                    + "Nome : " + frame.getProvider().getName() + "\n"
                    + "Pais : " + frame.getProvider().getCountry() + "\n"
                    + "Estado : " + frame.getProvider().getState() + "\n"
                    + "Cidade : " + frame.getProvider().getCity() + "\n"
                    + "Bairro : " + frame.getProvider().getDistrict() + "\n"
                    + "Número : " + frame.getProvider().getNumber());
    }
    
}
