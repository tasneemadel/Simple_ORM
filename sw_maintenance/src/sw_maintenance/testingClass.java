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
public class testingClass extends DatabaseEntity{
     private String text;
     private int id;
     private boolean flag;
     private float flNumber;
     private char c;
     private long ll;
     private double d;

    public void setD(double d) {
        this.d = d;
    }

    public double getD() {
        return d;
    }

    public void setLl(long ll) {
        this.ll = ll;
    }

    public long getLl() {
        return ll;
    }
     

  
     
public testingClass(){}
    public testingClass(String databaseName, String userName, String password) {
       
        super(databaseName, userName, password);
        // System.out.println(databaseName+" "+userName+password);
    }
    public void setid(int id){
    this.id=id;
    }
   public  void setnn(String nn){
       
        this.text = nn;
    }
    
    public String getnn(){
    return this.text;
    }
    public int getid(){
    return this.id;
    }
      public boolean isFlag() {
        return flag;
    }

    public float getFlNumber() {
        return flNumber;
    }

    public char getC() {
        return c;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setFlNumber(float fl) {
        this.flNumber = fl;
    }
    
public void setC(char gg) {
        this.c = gg;
    }

    
}
