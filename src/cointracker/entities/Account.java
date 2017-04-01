package cointracker.entities;


public class Account {
    
    private int id; //Identificador
    private String description; //Descrição
    private double balance; //Saldo
    private double openingBalance; //Saldo inicial
    private String ownerName; //Nome do responsavel ou dono
    private int type; //1 - Pessoal 2 - Compartilhada 3 - Empresarial
    
    public Account(){
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
}
