/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.internalframes;

import com.empresa.sistema.actionlistener.RegisterProviderActionListener;
import com.empresa.sistema.entities.Provider;
import java.util.ArrayList;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author comp8
 */
public class RegisterProviderJInternalFrame extends javax.swing.JInternalFrame {

    RegisterProviderActionListener actionListener;
    public ArrayList<Provider> listProvider;
    
    //Guarda o registro da conta acessada
    public int idProvider;
    public Provider provider;
    
    public RegisterProviderJInternalFrame(ArrayList<Provider> listProvider) {
        initComponents();
        this.listProvider = listProvider;
        actionListener = new RegisterProviderActionListener(this);
        addActionListeners();
        setEditMode(false);
        initFrameAccount();
    }
    
    public void initFrameAccount(){
        idProvider = 0;
        if(listProvider.size() == 0){
            provider = new Provider();
            readAccount(provider);
            setEditMode(true);
            buttonEdit.setEnabled(false);
        }else{
            readAccount(listProvider.get(idProvider));
        }
    }
    
    public void readAccount(Provider provider){
        this.provider = provider;
        labelId.setText(Integer.toString( this.provider.getId()));
        textFieldName.setText(this.provider.getName());
        comboBoxType.setSelectedIndex(this.provider.getType());
        textFieldDocument.setText(this.provider.getDocument());
        if(provider.getType() == 0){
            labelDocumentOwner.setText("CPF : ");            
        }else{
            labelDocumentOwner.setText("CNPJ : ");  
        }
        checkBoxActive.setSelected(provider.isActivated());
        comboBoxCountry.setSelectedIndex(provider.getCountry());
        textFieldState.setText(provider.getState());
        textFieldCity.setText(provider.getCity());
        textFieldDistrict.setText(provider.getDistrict());
        textFieldStreet.setText(provider.getStreet());
        textFieldNumber.setText(provider.getNumber());
   
    }
    
    public void saveChanges(){
        provider.setId(Integer.parseInt(labelId.getText()));
        provider.setName(textFieldName.getText());
        provider.setType(comboBoxType.getSelectedIndex());
        provider.setDocument(textFieldDocument.getText());
        provider.setActivated(checkBoxActive.isSelected());
        provider.setCountry(comboBoxCountry.getSelectedIndex());
        provider.setState(textFieldState.getText());
        provider.setCity(textFieldCity.getText());
        provider.setDistrict(textFieldDistrict.getText());
        provider.setStreet(textFieldStreet.getText());
        provider.setNumber(textFieldNumber.getText());
        System.out.println("------------------------------");
        System.out.println("Saved provider:");
        System.out.println(Integer.parseInt(labelId.getText()));
        System.out.println(textFieldName.getText());
        System.out.println(comboBoxType.getSelectedIndex());
        System.out.println(textFieldDocument.getText());
        System.out.println(checkBoxActive.isSelected());
        System.out.println(comboBoxCountry.getSelectedIndex());
        System.out.println(textFieldState.getText());
        System.out.println(textFieldCity.getText());
        System.out.println(textFieldDistrict.getText());
        System.out.println(textFieldStreet.getText());
        System.out.println(textFieldNumber.getText());
        System.out.println("------------------------------");
        
        
    }
    
    public void setEditMode(boolean enable){
        
        if(enable == true){
            //Botões de controle
            buttonOk.setEnabled(true);
            buttonLeft.setEnabled(false);
            buttonRigth.setEnabled(false);
            buttonEdit.setText("Cancelar");
            buttonEdit.setEnabled(true);
            buttonNew.setText("Desfazer");
            
            //Item de formulario
            textFieldName.setEnabled(true);
            textFieldDocument.setEnabled(true);
            textFieldState.setEnabled(true);
            textFieldCity.setEnabled(true);
            textFieldDistrict.setEnabled(true);
            textFieldStreet.setEnabled(true);
            textFieldNumber.setEnabled(true);
            comboBoxType.setEnabled(true);
            checkBoxActive.setEnabled(true);
        }else{
            //Botões de controle
            buttonOk.setEnabled(false);
            buttonLeft.setEnabled(true);
            buttonRigth.setEnabled(true);
            buttonEdit.setEnabled(true);
            buttonEdit.setText("Editar");
            buttonNew.setText("Novo");

            //Item de formulario
            textFieldName.setEnabled(false);
            textFieldDocument.setEnabled(false);
            textFieldState.setEnabled(false);
            textFieldCity.setEnabled(false);
            textFieldDistrict.setEnabled(false);
            textFieldStreet.setEnabled(false);
            textFieldNumber.setEnabled(false);
            comboBoxType.setEnabled(false);
            checkBoxActive.setEnabled(false);
        }
    }
    
    public void addActionListeners(){
        buttonEdit.addActionListener(actionListener);
        buttonLeft.addActionListener(actionListener);
        buttonOk.addActionListener(actionListener);
        buttonRigth.addActionListener(actionListener);
        buttonNew.addActionListener(actionListener);
        comboBoxType.addActionListener(actionListener);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        buttonRigth = new javax.swing.JButton();
        buttonLeft = new javax.swing.JButton();
        buttonNew = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonEdit.setActionCommand("buttonEditCliked");
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textFieldName = new javax.swing.JTextField();
        comboBoxType = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        labelDocumentOwner = new javax.swing.JLabel();
        textFieldDocument = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        checkBoxActive = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        comboBoxCountry = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        textFieldState = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textFieldCity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFieldDistrict = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textFieldStreet = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        textFieldNumber = new javax.swing.JTextField();
        buttonOk = new javax.swing.JButton();
        labelId = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel7.setText("jLabel7");

        jLabel11.setText("jLabel11");

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Fornecedores");
        setAutoscrolls(true);
        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 400));

        buttonRigth.setActionCommand("buttonRigthCliked");
        buttonRigth.addActionListener(this.actionListener);
        buttonRigth.setText(">");

        buttonLeft.setActionCommand("buttonLeftCliked");
        buttonLeft.addActionListener(this.actionListener);
        buttonLeft.setText("<");

        buttonNew.setActionCommand("buttonNewCliked");
        buttonNew.addActionListener(this.actionListener);
        buttonNew.setText("Novo");

        buttonEdit.setText("Editar");

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel1.setText("Nome : ");

        textFieldName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        comboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fisica", "Juridica" }));
        comboBoxType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxTypeItemStateChanged(evt);
            }
        });
        comboBoxType.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                comboBoxTypeCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        jLabel9.setText("Tipo da pessoa: ");

        labelDocumentOwner.setText("CPF : ");

        textFieldDocument.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel12.setText("Ativo : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(158, 158, 158))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelDocumentOwner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkBoxActive))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDocumentOwner)
                    .addComponent(textFieldDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(checkBoxActive))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados", jPanel1);

        jLabel8.setText("Pais : ");

        comboBoxCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Estado:");

        jLabel4.setText("Cidade : ");

        jLabel5.setText("Bairro : ");

        jLabel6.setText("Rua : ");

        jLabel10.setText("Numero : ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldState)
                            .addComponent(textFieldCity, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldDistrict, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldStreet)))
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboBoxCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textFieldDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textFieldStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(textFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Endereço", jPanel2);

        buttonOk.setText("Ok");
        buttonOk.setActionCommand("OkRegisterProvider");
        buttonOk.addActionListener(this.actionListener);
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        labelId.setText("0");

        jLabel2.setText("Codigo do fornecedor : ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelId)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(buttonEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonNew)
                                .addGap(18, 18, 18)
                                .addComponent(buttonLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonRigth)
                                .addGap(0, 134, Short.MAX_VALUE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonOk)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonEdit)
                        .addComponent(buttonLeft))
                    .addComponent(buttonRigth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelId))
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(buttonOk)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonEdit, buttonLeft, buttonNew, buttonOk, buttonRigth});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        
    }//GEN-LAST:event_buttonOkActionPerformed

    private void comboBoxTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxTypeItemStateChanged
        if(comboBoxType.getSelectedIndex() == 0){
            labelDocumentOwner.setText("CPF : ");            
        }else{
            labelDocumentOwner.setText("CNPJ : ");  
        }
    }//GEN-LAST:event_comboBoxTypeItemStateChanged

    private void comboBoxTypeCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_comboBoxTypeCaretPositionChanged

    }//GEN-LAST:event_comboBoxTypeCaretPositionChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonEdit;
    public javax.swing.JButton buttonLeft;
    public javax.swing.JButton buttonNew;
    public javax.swing.JButton buttonOk;
    public javax.swing.JButton buttonRigth;
    private javax.swing.JCheckBox checkBoxActive;
    private javax.swing.JComboBox<String> comboBoxCountry;
    public javax.swing.JComboBox<String> comboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JLabel labelDocumentOwner;
    private javax.swing.JLabel labelId;
    private javax.swing.JTextField textFieldCity;
    private javax.swing.JTextField textFieldDistrict;
    private javax.swing.JFormattedTextField textFieldDocument;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldNumber;
    private javax.swing.JTextField textFieldState;
    private javax.swing.JTextField textFieldStreet;
    // End of variables declaration//GEN-END:variables
}
