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
        user u=new user("DBTest","root","");
   
        u.openConnection();
        DBSet<user> u1= u.getDBInstance(user.class);
        
        ArrayList<user> arr=u.getDBInstance(user.class).findBy("id", 1);
        ArrayList<user> arr2=u.getDBInstance(user.class).findAll();
        System.out.println("Array size "+arr.size());
       System.out.println("value of id for first row is  "+arr.get(0).getid());
        System.out.println("value of second field nn for firs row is  "+arr2.get(0).getnn());
        System.out.println("value of second field nn for second row is : "+arr2.get(1).getnn());
        u.closeConnection();
    }
    
}
