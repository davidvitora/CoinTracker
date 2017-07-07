package com.empresa.sistema.ActionListener;

import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.frames.MainJFrame;
import com.empresa.sistema.cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import cointracker.util.LogMaker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RegisterAccountActionListener implements ActionListener {
    
    RegisterAccountJInternalFrame frame;

    public RegisterAccountActionListener(RegisterAccountJInternalFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("OkRegisterAccount".equals(e.getActionCommand())){
            if(frame.buttonOk.getText().equals("Deletar")){
                int indexAccountToDelete = frame.getAccount().getId();
                try {
                    if(frame.getAccountIdsList().size() == 1){
                        frame.getDao().deleteAccount(indexAccountToDelete);
                        frame.dispose();
                    }else{
                        frame.iteratorDeleteRoutine(indexAccountToDelete);
                        frame.setAccount(frame.getDao().getAccount(frame.indexAccount));
                        frame.readAccount(frame.getAccount());
                        frame.setEditMode(false);
                        try{
                            frame.setAccount(frame.getDao().getAccount(frame.indexAccount));
                            frame.readAccount(frame.getAccount());
                            frame.getDao().deleteAccount(indexAccountToDelete);
                            frame.updateAccountsIds();
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
            frame.updateAccountsIds();
            frame.updateIterator();
            frame.indexAccount = frame.getAccountIdsList().indexOf(frame.getAccount().getId());
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
            + "\n CNPJ do responsavel: " + frame.getAccount() + "\n pelo usuário : " + this.frame.getSession().getUser().getName());
        }else if("buttonEditCliked".equals(e.getActionCommand())){
            if(frame.buttonEdit.getText().equals("Editar")){
                frame.setEditMode(true);
                LogMaker.log("Editando conta de codigo ");
            }else{
                frame.setEditMode(false);
                try {
                    frame.readAccount(frame.getAccount());
                } catch (Exception ex) {
                    LogMaker.log(ex.getMessage());
                }
                LogMaker.log("Cancelada edição de conta ");
            }
        }
        else if("buttonLeftCliked".equals(e.getActionCommand())){
            int indexOldAccount = frame.getAccount().getId();
            if(frame.getIterator().hasPrevious()){
                frame.indexAccount = frame.getIterator().previous();
                if(indexOldAccount == frame.indexAccount){
                    if(frame.getIterator().hasPrevious()){
                        frame.indexAccount = frame.getIterator().previous();
                    }
                }
                try{
                    frame.setAccount(frame.getDao().getAccount(frame.indexAccount));
                    frame.readAccount(frame.getAccount());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        }
        else if("buttonRigthCliked".equals(e.getActionCommand())){
            int indexOldAccount = frame.getAccount().getId();
            if(frame.getIterator().hasNext()){
                frame.indexAccount = frame.getIterator().next();
                if(indexOldAccount == frame.indexAccount){
                    if(frame.getIterator().hasNext()){
                        frame.indexAccount = frame.getIterator().next();
                    }
                }
                try{
                    frame.setAccount(frame.getDao().getAccount(frame.indexAccount));
                    frame.readAccount(frame.getAccount());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
        }else if("buttonNewCliked".equals(e.getActionCommand())){
            if(frame.buttonNew.getText().equals("Novo")){
                frame.setAccount(new Account());
                frame.getAccount().setId(frame.verifyOpenId());
                frame.readAccount(frame.getAccount());
                frame.setEditMode(true);
                frame.buttonEdit.setEnabled(false);
                LogMaker.log("Criando nova conta");
            }else{
                
                LogMaker.log("Desfeita a edição de conta");
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
