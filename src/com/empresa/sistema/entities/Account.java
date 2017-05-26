package com.empresa.sistema.entities;


public class Account {
    
    private int id; //Identificador
    private String description; //Descrição
    private double balance; //Saldo
    private double openingBalance; //Saldo inicial
    private String ownerName; //Nome do responsavel ou dono
    private int ownerType; //0 - Fisica 1 - Juridica
    private String document; //Numero do documento
    private int type; //0 - Pessoal 1 - Compartilhada 2 - Empresarial
    
    public Account(){
        id = 0;
        description = "";
        balance = 0;
        openingBalance = 0;
        ownerName = "";
        ownerType = 0;
        document = "";
        type = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String documento) {
        this.document = documento;
    }
}
