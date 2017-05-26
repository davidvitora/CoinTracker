/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.actionlistener;

import com.empresa.sistema.frames.MainJFrame;
import com.empresa.sistema.internalframes.RegisterAccountJInternalFrame;
import com.empresa.sistema.internalframes.RegisterCategoryInternalJFrame;
import com.empresa.sistema.internalframes.RegisterProviderJInternalFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author David .V
 */
public class MainActionListener implements ActionListener{
    MainJFrame frame;

    public MainActionListener(MainJFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("buttonRegisterAccountClicked".equals(e.getActionCommand())){
            if(frame.getRegisterAccountJInternalFrame() == null){
                frame.setRegisterAccountJInternalFrame(new RegisterAccountJInternalFrame(frame.accountList));
                frame.desktopPane.add(frame.getRegisterAccountJInternalFrame());
            }
            frame.getRegisterAccountJInternalFrame().setVisible(true);
        }else if("buttonRegisterProviderClicked".equals(e.getActionCommand())){
            if(frame.getRegisterProviderJInternalFrame() == null){
                frame.setRegisterProviderJInternalFrame(new RegisterProviderJInternalFrame(frame.providerList));
                frame.desktopPane.add(frame.getRegisterProviderJInternalFrame());
            }
            frame.getRegisterProviderJInternalFrame().setVisible(true);
        }
        else if("AddCategory".equals(e.getActionCommand())){
            if(frame.getRegisterCategoryInternalJFrame() == null){
                frame.setRegisterCategoryInternalJFrame(new RegisterCategoryInternalJFrame(frame.categoriesList));
                frame.desktopPane.add(frame.getRegisterCategoryInternalJFrame());
            }
            frame.getRegisterCategoryInternalJFrame().setVisible(true);
        }
    }
}
