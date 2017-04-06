package ActionListener;

import cointracker.entities.Account;
import cointracker.frames.MainJFrame;
import cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
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
            if(frame.listAccount.contains(frame.account)){
                return;
            }
            frame.listAccount.add(frame.account);
            frame.idAccount = frame.account.getId();
            
            String tipoConta;
            if(frame.account.getType() == 0){
                tipoConta = "0 - Pessoal";
            }else if(frame.account.getType() == 1){
                tipoConta = "1 - Compartilhada";
            }else{
                tipoConta = "2 - Empresarial";
            }
             
            String tipoDono;
            if(frame.account.getOwnerType() == 0){
                tipoDono = "0 - Fisica";
            }else{
                tipoDono = "1 - Juridica";
            }
            
            System.out.println("Registro de cadastro de conta criado\n id: " + frame.account.getId()
            + "\n Descrição: " + frame.account.getDescription()
            + "\n Saldo Atual: " + frame.account.getBalance()
            + "\n Saldo Inicial: " + frame.account.getOpeningBalance()
            + "\n Tipo de conta: " + tipoConta
            + "\n Nome do responsavel: " + frame.account.getOwnerName()
            + "\n Tipo de pessoa responsavel: " + tipoDono
            + "\n CNPJ do responsavel: " + frame.account);
            
        }else if("buttonEditCliked".equals(e.getActionCommand())){
            if(frame.buttonEdit.getText().equals("Editar")){
                frame.setEditMode(true);
            }else{
                frame.setEditMode(false);
                frame.readAccount(frame.listAccount.get(frame.idAccount));
            }
        }
        else if("buttonLeftCliked".equals(e.getActionCommand())){
            if(frame.idAccount != 0){
                frame.idAccount -= 1;
                frame.readAccount(frame.listAccount.get(frame.idAccount));
            }
        }
        else if("buttonRigthCliked".equals(e.getActionCommand())){
            if(frame.idAccount < frame.listAccount.size() - 1){
                frame.idAccount += 1;
                frame.readAccount(frame.listAccount.get(frame.idAccount));
            }
        }else if("buttonNewCliked".equals(e.getActionCommand())){
            if(frame.buttonNew.getText().equals("Novo")){
                frame.account = new Account();
                frame.account.setId(frame.listAccount.size());
                frame.readAccount(frame.account);
                frame.setEditMode(true);
            }else{
                frame.readAccount(frame.account);
            }
        }else if("comboBoxDocumentAction".equals(e.getActionCommand())){
            if(frame.account.getOwnerType() == 0){
                frame.labelDocumentOwner.setText("CPF : ");            
            }else{
                frame.labelDocumentOwner.setText("CNPJ : ");  
            }
        }
    }
    
}
