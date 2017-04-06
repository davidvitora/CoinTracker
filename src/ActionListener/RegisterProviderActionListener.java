package ActionListener;

import cointracker.entities.Account;
import cointracker.entities.Provider;
import cointracker.frames.MainJFrame;
import cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import cointracker.frames.internalFrames.RegisterProviderJInternalFrame;
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
            if(frame.listProvider.contains(frame.provider)){
                return;
            }
            frame.listProvider.add(frame.provider);
            frame.idProvider = frame.provider.getId();
            
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
                frame.provider = new Provider();
                frame.provider.setId(frame.listProvider.size());
                frame.readAccount(frame.provider);
                frame.setEditMode(true);
            }else{
                frame.readAccount(frame.provider);
            }
        }else if("comboBoxDocumentAction".equals(e.getActionCommand())){
            if(frame.provider.getType() == 0){
                frame.labelDocumentOwner.setText("CPF : ");            
            }else{
                frame.labelDocumentOwner.setText("CNPJ : ");  
            }
        }
    }
    
}
