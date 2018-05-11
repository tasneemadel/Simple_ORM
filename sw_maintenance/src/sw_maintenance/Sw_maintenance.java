/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw_maintenance;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class Sw_maintenance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        testingClass u = new testingClass("DBTest", "root", "");
        u.setid(2);
        u.setnn("testing");
        u.setFlNumber(10.95f);
        u.setFlag(false);
        u.setC('o');
        u.setLl(125647855);
        u.setD(4.46);
//        
        
        
        
        u.openConnection();
        
        
        DBSet<testingClass> u1 = u.getDBInstance(testingClass.class);

        u1.add(u);

        
        ArrayList<testingClass> arr = u.getDBInstance(testingClass.class).findBy("id", 1);
        
        testingClass object = (testingClass)u.getDBInstance(testingClass.class).findById("id", 1);
        
        ArrayList<testingClass> arr2 = u.getDBInstance(testingClass.class).findAll();
        System.out.println("Array size " + arr.size());
        
        System.out.println("");
        System.out.println("");
        System.out.println("data with id 1");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println("member " + i);
            printData(arr.get(i));
            
        }
        System.out.println("");
        System.out.println("");
        
//        
//        if (arr.size() > 0) {
//            System.out.println("value of id for first row is  " + arr.get(0).getid());
//            
//        }else{
//            System.out.println("array lis is empty");
//        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("all data in database");
        for (int i = 0; i < arr2.size(); i++) {
            System.out.println("member " + i);
            printData(arr2.get(i));
            
        }
        System.out.println("");
        System.out.println("");
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("findby function which returns object");
        if(object != null){
            printData(object);
        }else{
            System.out.println("no data returned");
        }
//        System.out.println("value of field nn for firs row is  " + "String is" + object.getnn() + "\n"  + "\n" + "integer is" + object.getid());
        

        System.out.println("");
        System.out.println("");
//            System.out.println("value of second field nn for second row is : " + arr2.get(1).getnn());
            
            
        u.closeConnection();
    }
    
    static void printData(testingClass u){
        String s = u.getnn();
        int jj = u.getid();
        char cc = u.getC();
        boolean bb = u.isFlag();
        long ll = u.getLl();
        float f = u.getFlNumber();
        double d = u.getD();
        System.out.println("text is " + s + " number is " + jj + " charcater is " + cc + " boolean is " + bb + " double number is " + d + " float number is " + f);
    }

}
