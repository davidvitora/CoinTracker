/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cointracker.entities;

/*
 *
 * @author comp8
 */
public class Category {
    //private int id;
    private String description;
    private int type; // 1 for "income", 2 for "expense"
    
   /*
    * public int getId(){
    *     return id;
    * }
    * public void setId(int id){
    *     this.id = id;
    * }
    */
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    

}
