/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.empresa.sistema.cointracker.frames.internalFrames;

import cointracker.util.LogMaker;
import com.empresa.sistema.ActionListener.RegisterProviderActionListener;
import com.empresa.sistema.cointracker.entities.Account;
import com.empresa.sistema.cointracker.entities.Provider;
import com.empresa.sistema.cointracker.entities.Session;
import com.empresa.sistema.cointracker.entities.User;
import com.empresa.sistema.cointracker.util.CountryList;
import com.empresa.sistema.database.AccountDAO;
import com.empresa.sistema.database.ProviderDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListDataListener;

/**
 *
 * @author comp8
 */
public class RegisterProviderJInternalFrame extends javax.swing.JInternalFrame {

    RegisterProviderActionListener actionListener;
    private ProviderDAO dao;
    private Session session;
    public List<String> paises;
    
    //Guarda o registro dos fornecedores
    //index do fornecedor acessado na lista
    private int indexProvider;
    //objeto do fornecedor acessado
    private Provider provider;
    //lista de ids de fornecedores do banco
    private List<Integer> providersIdsList;
    //Iterator 
    private ListIterator<Integer> iterator;
    
    public RegisterProviderJInternalFrame(ArrayList<Provider> listProvider, Session session) {
        this.session = session;
        paises = CountryList.countries();
        initComponents();
        this.dao = new ProviderDAO();
        updateProvidersIds();
        actionListener = new RegisterProviderActionListener(this);
        addActionListeners();
        setEditMode(false);
        this.iterator = this.providersIdsList.listIterator();
        initFrameAccount();
    }
    
    public void initFrameAccount(){
        try{
            if(getProvidersIdsList().size() == 0){
                this.setIndexProvider(0);
                this.setProvider(new Provider());
                this.getProvider().setId(this.verifyOpenId());
                this.readProvider(this.getProvider());
                setEditMode(true);
                buttonEdit.setEnabled(false);
            }else{
                this.indexProvider = this.getIterator().next();
                setProvider(getDao().getProvider(indexProvider));
                readProvider(getProvider());
            }
        }catch(Exception ex){
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage() + "Erro ao inicializar | Verifique a conexao com o banco de dados");
        }
    }
    
    public void readProvider(Provider provider){
        this.setProvider(provider);
        labelId.setText(Integer.toString(this.getProvider().getId()));
        textFieldName.setText(this.getProvider().getName());
        comboBoxType.setSelectedIndex(this.getProvider().getType());
        textFieldDocument.setText(this.getProvider().getDocument());
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
    
    public void updateIterator(){
        this.setIterator(this.getProvidersIdsList()
                .listIterator(this.getProvidersIdsList().indexOf(this.getProvider().getId())));
    }
    
    public void saveChanges(){ 
        try {
            getProvider().setId(Integer.parseInt(labelId.getText()));
            getProvider().setName(textFieldName.getText());
            getProvider().setType(comboBoxType.getSelectedIndex());
            getProvider().setDocument(textFieldDocument.getText());
            getProvider().setActivated(checkBoxActive.isSelected());
            getProvider().setCountry(comboBoxCountry.getSelectedIndex());
            getProvider().setState(textFieldState.getText());
            getProvider().setCity(textFieldCity.getText());
            getProvider().setDistrict(textFieldDistrict.getText());
            getProvider().setStreet(textFieldStreet.getText());
            getProvider().setNumber(textFieldNumber.getText());
            if(this.getProvidersIdsList().contains(this.getProvider().getId())){
                getDao().updateProvider(this.getProvider());
            }else{
                getDao().saveProvider(this.getProvider());
            }
        } catch (Exception ex) {
            LogMaker.log(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage() + "Erro");
        }
    }
    
    public void setEditMode(boolean enable){
        
        if(enable == true){
            //Botões de controle
            buttonOk.setText("Ok");
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
            comboBoxCountry.setEnabled(true);
            checkBoxActive.setEnabled(true);
        }else{
            buttonOk.setText("Deletar");
            //Botões de controle
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
            comboBoxCountry.setEnabled(false);
            checkBoxActive.setEnabled(false);
        }
    }
    
    public void updateProvidersIds(){
        try {
            this.providersIdsList = dao.getProvidersId();
        } catch (Exception ex) {
            Logger.getLogger(RegisterAccountJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iteratorDeleteRoutine(int indexProcToDelete){
       if(this.iterator.hasNext()){
           this.indexProvider = this.iterator.next();
           if(indexProcToDelete == this.indexProvider){
               this.indexProvider = this.iterator.next();
           }
       }else{
           this.indexProvider = this.iterator.previous();
           if(indexProcToDelete == this.indexProvider){
               this.indexProvider = this.iterator.previous();
           }
       }
    }
    
    public boolean validar(){
        if(this.textFieldName.getText().length() < 2){
            JOptionPane.showMessageDialog(null, "Digite um nome de pelo menos 3 caracteres");
            return false;
        }
        if(this.textFieldDocument.getText().matches(".*[^0-9]+.*")){
            JOptionPane.showMessageDialog(null, "O documento poderá conter apenas numeros");
            return false;
        }
        if(this.textFieldNumber.getText().matches(".*[^0-9]+.*")){
            JOptionPane.showMessageDialog(null, "O número poderá conter apenas numeros");
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
        return true;
    }
    
    //Verifica um id livre para ser inserido em um novo fornecedor
    public int verifyOpenId(){
        int id = 0;
        for(Integer listId : this.getProvidersIdsList()){
            if(id != listId){
                return id;
            }else{
                id++;
            }
        }
        return id;
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
        setTitle("Fornecedores");
        setPreferredSize(new java.awt.Dimension(300, 350));

        this.comboBoxCountry.setSelectedItem("Brasil");

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

        checkBoxActive.setSelected(true);

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
                        .addComponent(textFieldDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        comboBoxCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Brasil", "Afeganistão", "África do Sul", "Albânia", "Alemanha", "Andorra", "Angola", "Antiga e Barbuda", "Arábia Saudita", "Argélia", "Argentina", "Arménia", "Austrália", "Áustria", "Azerbaijão", "Bahamas", "Bangladexe", "Barbados", "Barém", "Bélgica", "Belize", "BenimPorto ", "Bielorrússia", "Bolívia", "Bósnia e Herzegovina", "Botsuana", "Brunei", "Bulgária", "Burquina Faso", "Burúndi", "Butão", "Cabo Verde", "Camarões", "Camboja", "Canadá", "Catar", "Cazaquistão", "Chade", "Chile", "China", "Chipre", "Colômbia", "Comores", "Congo-Brazzaville", "Coreia do Norte", "Coreia do Sul", "Cosovo", "Costa do Marfim", "Costa Rica", "Croácia", "Cuaite", "Cuba", "Dinamarca", "Dominica", "Egito", "Emirados Árabes Unidos", "Equador", "Eritreia", "Eslováquia", "Eslovénia", "Espanha", "Estado da Palestina", "Estados Unidos", "Estónia", "EtiópiaAdis ", "Fiji", "Filipinas", "Finlândia", "França", "Gabão", "Gâmbia", "Gana", "Geórgia", "GranadaSão ", "Grécia", "Guatemala", "Guiana", "Guiné", "Guiné Equatorial", "Guiné-Bissau", "Haiti", "Honduras", "Hungria", "Iémen", "Ilhas Marechal", "Índia", "Indonésia", "Irão", "Iraque", "Irlanda", "Islândia", "Israel", "Itália", "Jamaica", "Japão", "Jibuti", "Jordânia", "Laus", "Lesoto", "Letónia", "Líbia", "Listenstaine", "Lituânia", "Luxemburgo", "Macedónia", "Madagáscar", "MalásiaCuala ", "Maláui", "Maldivas", "Mali", "Malta", "Marrocos", "Maurícia", "Mauritânia", "México", "Mianmar", "Micronésia", "Moçambique", "Moldávia", "Mónaco", "Mongólia", "Montenegro", "Namíbia", "Nauru", "Nepal", "Nicarágua", "Níger", "Nigéria", "Noruega", "Nova Zelândia", "Omã", "Países Baixos", "Palau", "Panamá", "Papua Nova Guiné", "Paquistão", "Paraguai", "Peru", "Polónia", "Portugal", "Quénia", "Quirguistão", "Quiribáti", "Reino Unido", "República Centro-Africana", "República Checa", "República Democrática do Congo", "República Dominicana", "Roménia", "Ruanda", "Rússia", "Salomão", "Salvador ", "Samoa", "Santa Lúcia", "São Cristóvão e Neves", "São Marinho", "São Tomé e Príncipe", "São Vicente e Granadinas", "Seicheles", "Senegal", "Serra Leoa", "Sérvia", "Singapura", "Síria", "Somália", "Sri Lanca", "Suazilândia", "Sudão", "Sudão do Sul", "Suécia", "Suíça", "Suriname", "Tailândia", "Taiuã", "Tajiquistão", "Tanzânia", "Timor-Leste", "Togo", "Tonga", "Trindade e Tobago", "Tunísia", "Turcomenistão", "Turquia", "Tuvalu", "Ucrânia", "Uganda", "Uruguai", "Usbequistão", "VanuatuPorto ", "Vaticano", "Venezuela", "Vietname", "Zâmbia", "Zimbábue" }));
        comboBoxCountry.setEnabled(false);

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
                        .addComponent(comboBoxCountry, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(textFieldDistrict))
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
                                .addGap(0, 24, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonEdit, buttonLeft, buttonNew, buttonOk, buttonRigth});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Integer> getProvidersIdsList() {
        return providersIdsList;
    }

    public void setProvidersIdsList(List<Integer> providersIdsList) {
        this.providersIdsList = providersIdsList;
    }

    public int getIndexProvider() {
        return indexProvider;
    }

    public void setIndexProvider(int indexProvider) {
        this.indexProvider = indexProvider;
    }

    public ProviderDAO getDao() {
        return dao;
    }

    public void setDao(ProviderDAO dao) {
        this.dao = dao;
    }

    public ListIterator<Integer> getIterator() {
        return iterator;
    }

    public void setIterator(ListIterator<Integer> iterator) {
        this.iterator = iterator;
    }
    
}
