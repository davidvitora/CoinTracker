package com.empresa.sistema.ActionListener;

import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Provider;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterProviderJInternalFrame;
import cointracker.util.LogMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterProviderActionListener implements ActionListener {
    
    RegisterProviderJInternalFrame frame;

    public RegisterProviderActionListener(RegisterProviderJInternalFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("OkRegisterProvider".equals(e.getActionCommand())){
            frame.saveChanges();
            frame.setEditMode(false);
            if(frame.listProvider.contains(frame.getProvider())){
                return;
            }
            frame.listProvider.add(frame.getProvider());
            LogMaker.log("Fornecedor salvo \n"
                    + "Id : " + frame.getProvider().getId() + "\n"
                    + "Número do documento : " + frame.getProvider().getDocument() + "\n"
                    + "Nome : " + frame.getProvider().getName() + "\n"
                    + "Pais : " + frame.getProvider().getCountry() + "\n"
                    + "Estado : " + frame.getProvider().getState() + "\n"
                    + "Cidade : " + frame.getProvider().getCity() + "\n"
                    + "Bairro : " + frame.getProvider().getDistrict() + "\n"
                    + "Número : " + frame.getProvider().getNumber());
            frame.idProvider = frame.getProvider().getId();
            
        }else if("buttonEditCliked".equals(e.getActionCommand())){
            if(frame.buttonEdit.getText().equals("Editar")){
                frame.setEditMode(true);
            }else{
                frame.setEditMode(false);
                frame.readAccount(frame.listProvider.get(frame.idProvider));
            }
        }
        else if("buttonLeftCliked".equals(e.getActionCommand())){
            if(frame.idProvider != 0){
                frame.idProvider -= 1;
                frame.readAccount(frame.listProvider.get(frame.idProvider));
            }
        }
        else if("buttonRigthCliked".equals(e.getActionCommand())){
            if(frame.idProvider < frame.listProvider.size() - 1){
                frame.idProvider += 1;
                frame.readAccount(frame.listProvider.get(frame.idProvider));
            }
        }else if("buttonNewCliked".equals(e.getActionCommand())){
            if(frame.buttonNew.getText().equals("Novo")){
                frame.setProvider(new Provider());
                frame.getProvider().setId(frame.listProvider.size());
                frame.readAccount(frame.getProvider());
                frame.setEditMode(true);
            }else{
                frame.readAccount(frame.getProvider());
            }
        }else if("comboBoxDocumentAction".equals(e.getActionCommand())){
            if(frame.getProvider().getType() == 0){
                frame.labelDocumentOwner.setText("CPF : ");            
            }else{
                frame.labelDocumentOwner.setText("CNPJ : ");  
            }
        }
    }
    
}
