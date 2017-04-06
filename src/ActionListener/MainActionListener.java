/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionListener;

import cointracker.frames.MainJFrame;
import cointracker.frames.internalFrames.RegisterAccountJInternalFrame;
import cointracker.frames.internalFrames.RegisterProviderJInternalFrame;
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
            RegisterAccountJInternalFrame registerAccountJInternalFrame = new RegisterAccountJInternalFrame(frame.accountList);
            frame.desktopPane.add(registerAccountJInternalFrame);
            registerAccountJInternalFrame.setVisible(true);
        }else if("buttonRegisterProviderClicked".equals(e.getActionCommand())){
            RegisterProviderJInternalFrame registerProviderJInternalFrame = new RegisterProviderJInternalFrame(frame.providerList);
            frame.desktopPane.add(registerProviderJInternalFrame);
            registerProviderJInternalFrame.setVisible(true);
        }
    }
}
