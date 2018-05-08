/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw_maintenance;

/**
 *
 * @author dell
 */
public class user extends DatabaseEntity{
     private String nn;
     private int id;
public user(){}
    public user(String databaseName, String userName, String password) {
       
        super(databaseName, userName, password);
        // System.out.println(databaseName+" "+userName+password);
    }
    public void setid(int id){
    this.id=id;
    }
   public  void setnn(String nn){
       
        this.nn = nn;
    }
    
    public String getnn(){
    return this.nn;
    }
    public int getid(){
    return this.id;
    }
}
