package com.empresa.sistema.ActionListener;

import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import cointracker.util.LogMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterAccountActionListener implements ActionListener {
    
    RegisterAccountJInternalFrame frame;

    public RegisterAccountActionListener(RegisterAccountJInternalFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("OkRegisterAccount".equals(e.getActionCommand())){
            frame.saveChanges();
            frame.setEditMode(false);
            if(frame.listAccount.contains(frame.getAccount())){
                return;
            }
            frame.listAccount.add(frame.getAccount());
            frame.idAccount = frame.getAccount().getId();
            
            String tipoConta;
            if(frame.getAccount().getType() == 0){
                tipoConta = "0 - Pessoal";
            }else if(frame.getAccount().getType() == 1){
                tipoConta = "1 - Compartilhada";
            }else{
                tipoConta = "2 - Empresarial";
            }
             
            String tipoDono;
            if(frame.getAccount().getOwnerType() == 0){
                tipoDono = "0 - Fisica";
            }else{
                tipoDono = "1 - Juridica";
            }
            
            LogMaker.log("Registro de cadastro de conta salvo\n id: " + frame.getAccount().getId()
            + "\n Descrição: " + frame.getAccount().getDescription()
            + "\n Saldo Atual: " + frame.getAccount().getBalance()
            + "\n Saldo Inicial: " + frame.getAccount().getOpeningBalance()
            + "\n Tipo de conta: " + tipoConta
            + "\n Nome do responsavel: " + frame.getAccount().getOwnerName()
            + "\n Tipo de pessoa responsavel: " + tipoDono
            + "\n CNPJ do responsavel: " + frame.getAccount() + "\n pelo usuário : " + this.frame.getUser().getName());
            
        }else if("buttonEditCliked".equals(e.getActionCommand())){
            if(frame.buttonEdit.getText().equals("Editar")){
                frame.setEditMode(true);
                LogMaker.log("Editando conta de codigo " + frame.idAccount);
            }else{
                frame.setEditMode(false);
                frame.readAccount(frame.listAccount.get(frame.idAccount));
                LogMaker.log("Cancelada edição de conta " + frame.idAccount);
            }
        }
        else if("buttonLeftCliked".equals(e.getActionCommand())){
            if(frame.idAccount != 0){
                frame.idAccount -= 1;
                frame.readAccount(frame.listAccount.get(frame.idAccount));
                LogMaker.log("Acesso a conta de codigo " + frame.idAccount);
            }
        }
        else if("buttonRigthCliked".equals(e.getActionCommand())){
            if(frame.idAccount < frame.listAccount.size() - 1){
                frame.idAccount += 1;
                frame.readAccount(frame.listAccount.get(frame.idAccount));
                LogMaker.log("Acesso a conta de codigo " + frame.idAccount);
            }
        }else if("buttonNewCliked".equals(e.getActionCommand())){
            if(frame.buttonNew.getText().equals("Novo")){
                frame.setAccount(new Account());
                frame.getAccount().setId(frame.listAccount.size());
                frame.readAccount(frame.getAccount());
                frame.setEditMode(true);
                LogMaker.log("Criando nova conta");
            }else{
                LogMaker.log("Desfeita a edição de conta" + frame.idAccount);
                frame.readAccount(frame.getAccount());
            }
        }else if("comboBoxDocumentAction".equals(e.getActionCommand())){
            if(frame.getAccount().getOwnerType() == 0){
                frame.labelDocumentOwner.setText("CPF : ");            
            }else{
                frame.labelDocumentOwner.setText("CNPJ : ");  
            }
        }
    }
    
}
