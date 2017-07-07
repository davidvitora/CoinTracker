/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.cointracker.frames.internalFrames;

import cointracker.util.LogMaker;
import com.empresa.sistema.ActionListener.RegisterAccountActionListener;
import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Session;
import com.empresa.sistema.cointracker.entities.User;
import com.empresa.sistema.database.AccountDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author comp8
 */
public class RegisterAccountJInternalFrame extends javax.swing.JInternalFrame {

    private AccountDAO dao;
    RegisterAccountActionListener actionListener;
    //Lista de ids de contas retiradas do banco de dados
    private List<Integer> accountIdsList;
    
    //Guarda o registro da conta acessada
    public int indexAccount;
    private Account account;
    
    //Registro de sessão
    private Session session;
    //Iterator
    private ListIterator<Integer> iterator;
    
    public RegisterAccountJInternalFrame(Session session) throws Exception {
        this.session = session;
        initComponents();
        this.dao = new AccountDAO();
        this.accountIdsList = dao.getAccountsId();
        actionListener = new RegisterAccountActionListener(this);
        addActionListeners();
        setEditMode(false);
        this.iterator = this.accountIdsList.listIterator();
        initFrameAccount();
    }
    
    public void initFrameAccount(){
        try{
            if(getAccountIdsList().size() == 0){
                this.setAccount(new Account());
                indexAccount = 0;
                this.getAccount().setId(this.verifyOpenId());
                this.readAccount(this.getAccount());
                setEditMode(true);
                buttonEdit.setEnabled(false);
            }else{
                this.indexAccount = this.getIterator().next();
                setAccount(getDao().getAccount(indexAccount));
                readAccount(getAccount());
            }
        }catch(Exception ex){
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage() + "Erro + 1");
        }
    }
    
    public void readAccount(Account account){
        this.setAccount(account);
        labelId.setText(Integer.toString(this.getAccount().getId()));
        textFieldDesciption.setText(this.getAccount().getDescription());
        textFieldOpeningBalance.setText(Double.toString(this.getAccount().getOpeningBalance()));
        comboBoxAccountType.setSelectedIndex(this.getAccount().getType());
        textFieldOwnerName.setText(this.getAccount().getOwnerName());
        comboBoxOwnerType.setSelectedIndex(this.getAccount().getOwnerType());
        textFieldDocument.setText(this.getAccount().getDocument());
        if(account.getOwnerType() == 0){
            labelDocumentOwner.setText("CPF : ");            
        }else{
            labelDocumentOwner.setText("CNPJ : ");  
        }
    }
    
    public void updateIterator(){
       this.setIterator(this.getAccountIdsList()
               .listIterator(this.getAccountIdsList().indexOf(this.getAccount().getId())));
    }
    
    public void iteratorDeleteRoutine(int indexAccToDelete){
       if(this.iterator.hasNext()){
           this.indexAccount = this.iterator.next();
           if(indexAccToDelete == this.indexAccount){
               this.indexAccount = this.iterator.next();
           }
       }else{
           this.indexAccount = this.iterator.previous();
           if(indexAccToDelete == this.indexAccount){
               this.indexAccount = this.iterator.previous();
           }
       }
    }
     
    public boolean validar(){
        if(this.textFieldDesciption.getText().length() < 2){
            JOptionPane.showMessageDialog(null, "Digite uma descrição de pelo menos 3 caracteres");
            return false;
        }
        if(this.textFieldDocument.getText().matches(".*[^0-9]+.*")){
            JOptionPane.showMessageDialog(null, "O documento poderá conter apenas numeros");
            return false;
        }
        if(this.textFieldDocument.getText().length() > 0){
            if(this.labelDocumentOwner.getText().equals("CNPJ : ") && this.textFieldDocument.getText().length() != 14){
                JOptionPane.showMessageDialog(null, "O CNPJ deverá conter 14 caracteres");
                return false;
            }
            if(this.labelDocumentOwner.getText().equals("CPF : ") && this.textFieldDocument.getText().length() != 11){
                JOptionPane.showMessageDialog(null, "O CPF deverá conter 11 caracteres");
                return false;
            }
        }
        try{
            if(Double.parseDouble(this.textFieldOpeningBalance.getText()) < 0){
                JOptionPane.showMessageDialog(null, "Digite um saldo inicial valido");
                return false;
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Digite um saldo inicial valido");
            return false;
        }
        return true;
    }
    
    public void saveChanges(){
        try {
            getAccount().setId(Integer.parseInt(labelId.getText()));
            getAccount().setDescription(textFieldDesciption.getText());
            getAccount().setOpeningBalance(Double.parseDouble(textFieldOpeningBalance.getText()));
            getAccount().setBalance(Double.parseDouble(labelActualBalance.getText()));
            getAccount().setType(comboBoxAccountType.getSelectedIndex());
            getAccount().setOwnerName(textFieldOwnerName.getText());
            getAccount().setDocument(textFieldDocument.getText());
            getAccount().setOwnerType(comboBoxOwnerType.getSelectedIndex());
            if(this.getAccountIdsList().contains(this.getAccount().getId())){
                getDao().updateAccount(this.getAccount());
            }else{
                getDao().saveAccount(this.getAccount());
            }
        } catch (Exception ex) {
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage() + "Erro");
        }
        
        
    }
    
    public int verifyOpenId(){
        int id = 0;
        for(Integer listId : this.getAccountIdsList()){
            if(id != listId){
                return id;
            }else{
                id++;
            }
        }
        return id;
    }
    
    public void updateAccountsIds(){
        try {
            this.accountIdsList = dao.getAccountsId();
        } catch (Exception ex) {
            Logger.getLogger(RegisterAccountJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            buttonOk.setText("Ok");
            
            //Item de formulario
            textFieldDesciption.setEnabled(true);
            textFieldDocument.setEnabled(true);
            textFieldOpeningBalance.setEnabled(true);
            textFieldOwnerName.setEnabled(true);
            comboBoxAccountType.setEnabled(true);
            comboBoxOwnerType.setEnabled(true);
        }else{
            //Botões de controle
            buttonOk.setEnabled(true);
            buttonLeft.setEnabled(true);
            buttonRigth.setEnabled(true);
            buttonEdit.setEnabled(true);
            buttonEdit.setText("Editar");
            buttonNew.setText("Novo");
            buttonOk.setText("Deletar");

            //Item de formulario
            textFieldDesciption.setEnabled(false);
            textFieldDocument.setEnabled(false);
            textFieldOpeningBalance.setEnabled(false);
            textFieldOwnerName.setEnabled(false);
            comboBoxAccountType.setEnabled(false);
            comboBoxOwnerType.setEnabled(false);
        }
    }
    
    public void addActionListeners(){
        buttonEdit.addActionListener(actionListener);
        buttonLeft.addActionListener(actionListener);
        buttonOk.addActionListener(actionListener);
        buttonRigth.addActionListener(actionListener);
        buttonNew.addActionListener(actionListener);
        comboBoxOwnerType.addActionListener(actionListener);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldDesciption = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textFieldOpeningBalance = new javax.swing.JTextField();
        labelId = new javax.swing.JLabel();
        labelActualBalance = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboBoxAccountType = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        textFieldOwnerName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboBoxOwnerType = new javax.swing.JComboBox<>();
        labelDocumentOwner = new javax.swing.JLabel();
        textFieldDocument = new javax.swing.JFormattedTextField();
        buttonOk = new javax.swing.JButton();

        jLabel7.setText("jLabel7");

        jLabel11.setText("jLabel11");

        setClosable(true);
        setTitle("Contas");
        setPreferredSize(new java.awt.Dimension(300, 350));

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

        jLabel1.setText("Descrição : ");

        jLabel2.setText("Id : ");

        jLabel3.setText("Saldo Inicial : ");

        textFieldDesciption.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setText("Saldo Atual:");

        textFieldOpeningBalance.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        labelId.setText("0");

        labelActualBalance.setText("0");

        jLabel13.setText("Tipo : ");

        comboBoxAccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pessoal", "Compartilhada", "Empresarial" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldOpeningBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelId))
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelActualBalance)
                                .addGap(70, 70, 70))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldDesciption)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(labelId)
                    .addComponent(labelActualBalance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldDesciption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldOpeningBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(comboBoxAccountType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados", jPanel1);

        jLabel8.setText("Nome : ");

        textFieldOwnerName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel9.setText("Tipo : ");

        comboBoxOwnerType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fisica", "Juridica" }));
        comboBoxOwnerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxOwnerTypeItemStateChanged(evt);
            }
        });
        comboBoxOwnerType.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                comboBoxOwnerTypeCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        labelDocumentOwner.setText("CPF : ");

        textFieldDocument.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldOwnerName))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxOwnerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelDocumentOwner)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textFieldDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textFieldOwnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxOwnerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDocumentOwner)
                    .addComponent(textFieldDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Responsavel", jPanel2);

        buttonOk.setText("Ok");
        buttonOk.setActionCommand("OkRegisterAccount");
        buttonOk.addActionListener(this.actionListener);
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(buttonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNew)
                        .addGap(18, 18, 18)
                        .addComponent(buttonLeft)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRigth)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buttonOk))
                            .addComponent(jTabbedPane1))
                        .addGap(19, 19, 19))))
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonEdit, buttonLeft, buttonNew, buttonOk, buttonRigth});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        
    }//GEN-LAST:event_buttonOkActionPerformed

    private void comboBoxOwnerTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxOwnerTypeItemStateChanged
        if(comboBoxOwnerType.getSelectedIndex() == 0){
            labelDocumentOwner.setText("CPF : ");            
        }else{
            labelDocumentOwner.setText("CNPJ : ");  
        }
    }//GEN-LAST:event_comboBoxOwnerTypeItemStateChanged

    private void comboBoxOwnerTypeCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_comboBoxOwnerTypeCaretPositionChanged

    }//GEN-LAST:event_comboBoxOwnerTypeCaretPositionChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonEdit;
    public javax.swing.JButton buttonLeft;
    public javax.swing.JButton buttonNew;
    public javax.swing.JButton buttonOk;
    public javax.swing.JButton buttonRigth;
    private javax.swing.JComboBox<String> comboBoxAccountType;
    public javax.swing.JComboBox<String> comboBoxOwnerType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelActualBalance;
    public javax.swing.JLabel labelDocumentOwner;
    private javax.swing.JLabel labelId;
    private javax.swing.JTextField textFieldDesciption;
    private javax.swing.JFormattedTextField textFieldDocument;
    private javax.swing.JTextField textFieldOpeningBalance;
    private javax.swing.JTextField textFieldOwnerName;
    // End of variables declaration//GEN-END:variables

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Integer> getAccountIdsList() {
        return accountIdsList;
    }

    public void setAccountIdsList(List<Integer> accountIdsList) {
        this.accountIdsList = accountIdsList;
    }

    public AccountDAO getDao() {
        return dao;
    }

    public void setDao(AccountDAO dao) {
        this.dao = dao;
    }

    public ListIterator<Integer> getIterator() {
        return iterator;
    }

    public void setIterator(ListIterator<Integer> iterator) {
        this.iterator = iterator;
    }
}
