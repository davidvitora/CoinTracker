/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.cointracker.frames.internalFrames;

import com.empresa.sistema.cointracker.entities.Category;
import java.util.ArrayList;
import com.empresa.sistema.ActionListener.RegisterCategoryActionListener;
import com.empresa.sistema.cointracker.entities.Session;
import com.empresa.sistema.cointracker.entities.User;
import com.empresa.sistema.database.CategoryDAO;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class RegisterCategoryInternalJFrame extends javax.swing.JInternalFrame {
    
    RegisterCategoryActionListener actionListener;
    private Category category;
    private int currentID;
    //
    private CategoryDAO dao;
    private List<Integer> categorysIdsList;
    private ListIterator<Integer> iterator;
    boolean editing = false;
    private Session session;
    
    public RegisterCategoryInternalJFrame(ArrayList<Category> categories, Session session) {
        this.session = session;
        this.dao = new CategoryDAO();
        initComponents();
        this.updateCategoryIds();
        this.iterator = this.getCategorysIdsList().listIterator();
        actionListener = new RegisterCategoryActionListener(this);
        AddActionListeners();
        initFrameData();
        //InputID.setText(String.valueOf(categories.size() + 1));
    }
    
    private void initFrameData(){
        if(this.getCategorysIdsList().size() == 0){
            this.modoEdicao(true);
            this.setCategory(new Category());
            this.modoEdicao(true);
            this.readCategory(this.getCategory());
        }else{
            this.modoEdicao(false);
            this.setCurrentID((int) this.getIterator().next());
            try {
                this.setCategory(this.getDao().getCategory(getCurrentID()));
                this.readCategory(this.getCategory());
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(RegisterCategoryInternalJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateCategoryIds(){
         try {
            this.setCategorysIdsList(getDao().getCategorysId());
        } catch (Exception ex) {
            Logger.getLogger(RegisterAccountJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int verifyOpenId(){
        int id = 0;
        for(Integer listId : this.getCategorysIdsList()){
            if(id != listId){
                return id;
            }else{
                id++;
            }
        }
        return id;
    }
    
    public void updateIterator(){
        this.setIterator(this.getCategorysIdsList()
                .listIterator(this.getCategorysIdsList().indexOf(this.getCategory().getId())));
    }
    
    public void iteratorDeleteRoutine(int indexCatToDelete){
       if(this.iterator.hasNext()){
           this.setCurrentID((int) this.iterator.next());
           if(indexCatToDelete == this.getCurrentID()){
               this.setCurrentID((int) this.iterator.next());
           }
       }else{
           this.setCurrentID((int) this.iterator.previous());
           if(indexCatToDelete == this.getCurrentID()){
               this.setCurrentID((int) this.iterator.previous());
           }
       }
    }
    
    public void readCategory(Category category){
        labelIdCategory.setText(Integer.toString(getCategory().getId()));
        textDescription.setText(getCategory().getDescription());
        comboType.setSelectedItem(getCategory().getType());
    }
    
    public void confirmCategory(){
        getCategory().setId(Integer.parseInt(this.labelIdCategory.getText()));
        getCategory().setDescription(textDescription.getText());
        getCategory().setType((String) comboType.getSelectedItem());
    }
    
    public void SaveCategory(){
        confirmCategory();
        if(!this.validar()){
            return;
        }
        try {
            if(this.getCategorysIdsList().contains(this.getCategory().getId())){
                getDao().updateCategory(this.getCategory());
            }else{
                getDao().saveCategory(this.getCategory());
            }
            this.updateCategoryIds();
            this.updateIterator();
            editing = false;
            this.modoEdicao(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(RegisterCategoryInternalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void novo(){
        this.setCategory(new Category());
        this.category.setId(verifyOpenId());
        this.readCategory(this.getCategory());
        this.modoEdicao(true);
    }
    
    public void Reset(){
        readCategory(this.getCategory());
    }
    
    public void modoEdicao(boolean escolha){
        if(escolha){
            buttonOK.setText("Ok");
            textDescription.setEditable(true);
            comboType.setEnabled(true);
            buttonEdit.setEnabled(false);
            buttonNovo.setText("Cancelar");
        }
        else{
            buttonOK.setText("Deletar");
            textDescription.setEditable(false);
            comboType.setEnabled(false);
            buttonNovo.setText("Novo");
            buttonEdit.setEnabled(true);
        }
    }
    
    public boolean validar(){
        if(this.textDescription.getText().length() < 2){
            JOptionPane.showMessageDialog(null, "Informe uma descrição com mais de 3 caracteres");
            return false;
        }
        if(this.comboType.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "Selecione um tipo");
            return false;
        }
        return true;
    }
    
    public void Delete(){

    }
    
    public void AddActionListeners(){
        buttonOK.addActionListener(actionListener);
        buttonBack.addActionListener(actionListener);
        buttonFoward.addActionListener(actionListener);
        buttonEdit.addActionListener(actionListener);
        buttonNovo.addActionListener(actionListener);
    }
    
    public void Back(){
        int indexOldCategory = this.currentID;
        if(iterator.hasPrevious()){
            this.setCurrentID(iterator.previous());
            if(indexOldCategory == this.currentID){
                    if(iterator.hasPrevious()){
                        this.setCurrentID(iterator.previous());
                    }
            }
            try {
                setCategory(getDao().getCategory(getCurrentID()));
            } catch (Exception ex) {
                Logger.getLogger(RegisterCategoryInternalJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            labelIdCategory.setText(Integer.toString(getCategory().getId()));
            textDescription.setText(getCategory().getDescription());
            comboType.setSelectedItem(getCategory().getType());
            this.modoEdicao(false);
        }
    }
    
    
    
    public void Forward(){
        int indexOldCategory = this.currentID;
        if(iterator.hasNext()){
            this.setCurrentID((int) iterator.next());
            if(indexOldCategory == this.currentID){
                    if(iterator.hasNext()){
                        this.setCurrentID(iterator.next());
                    }
            }
            try {
                setCategory(getDao().getCategory(getCurrentID()));
            } catch (Exception ex) {
                Logger.getLogger(RegisterCategoryInternalJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            labelIdCategory.setText(Integer.toString(getCategory().getId()));
            textDescription.setText(getCategory().getDescription());
            comboType.setSelectedItem(getCategory().getType());
            this.modoEdicao(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonEdit = new javax.swing.JButton();
        buttonBack = new javax.swing.JButton();
        buttonFoward = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textDescription = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        buttonOK = new javax.swing.JButton();
        buttonNovo = new javax.swing.JButton();
        labelIdCategory = new javax.swing.JLabel();

        setClosable(true);

        buttonEdit.setActionCommand("Edit");
        buttonEdit.setText("Editar");

        buttonBack.setActionCommand("Back");
        buttonBack.setText("<");

        buttonFoward.setActionCommand("Forward");
        buttonFoward.setText(">");

        jLabel1.setText("Descrição");

        jLabel2.setText("Tipo");

        comboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Despesa", "Receita" }));
        comboType.setToolTipText("");

        jLabel3.setText("Codigo da categoria : ");

        buttonOK.setActionCommand("OK");
        buttonOK.addActionListener(this.actionListener);
        buttonOK.setText("Ok");

        buttonNovo.setText("Novo");
        buttonNovo.setMaximumSize(new java.awt.Dimension(57, 31));
        buttonNovo.setMinimumSize(new java.awt.Dimension(57, 31));
        buttonNovo.setActionCommand("Novo");
        buttonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNovoActionPerformed(evt);
            }
        });

        labelIdCategory.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buttonOK))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(buttonEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addComponent(buttonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonFoward)
                                .addGap(16, 16, 16))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textDescription))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelIdCategory))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonFoward, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(buttonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelIdCategory))
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNovoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonNovoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBack;
    public javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonFoward;
    public javax.swing.JButton buttonNovo;
    public javax.swing.JButton buttonOK;
    private javax.swing.JComboBox<String> comboType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelIdCategory;
    private javax.swing.JTextField textDescription;
    // End of variables declaration//GEN-END:variables

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Session getsession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Integer> getCategorysIdsList() {
        return categorysIdsList;
    }

    public void setCategorysIdsList(List<Integer> categorysIdsList) {
        this.categorysIdsList = categorysIdsList;
    }

    public ListIterator<Integer> getIterator() {
        return iterator;
    }

    public void setIterator(ListIterator<Integer> iterator) {
        this.iterator = iterator;
    }

    public CategoryDAO getDao() {
        return dao;
    }

    public void setDao(CategoryDAO dao) {
        this.dao = dao;
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }
}
