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
public class RegisterCategoryActionListener implements ActionListener {
    RegisterCategoryInternalJFrame frame;
    
    public RegisterCategoryActionListener(RegisterCategoryInternalJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("OK".equals(e.getActionCommand())){
            frame.SaveCategory();
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
            frame.Delete();
        }
        
    }
    
    

}
