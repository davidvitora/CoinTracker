/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionListener;

import cointracker.frames.internalFrames.RegisterCategoryInternalJFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gabriel
 */
public class RegisterCategoryActionListener implements ActionListener{
    
    RegisterCategoryInternalJFrame frame;
    
    public RegisterCategoryActionListener(RegisterCategoryInternalJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if("ClickOK".equals(ae.getActionCommand())){
            frame.saveCategory();
        }
    }
    
    
}
