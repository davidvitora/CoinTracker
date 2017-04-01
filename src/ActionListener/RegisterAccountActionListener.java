package ActionListener;

import cointracker.frames.MainJFrame;
import cointracker.frames.RegisterAccountJInternalFrame;
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
            System.out.println("OkRegisteraccount");
        }
    }
    
}
