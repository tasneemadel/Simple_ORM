/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw_maintenance;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author dell
 */
public class DBSet<T> {

    private Class<T> classType;
    private DatabaseEntity databaseEntity;
    private Field[] fields;
    private Object result;
    int flag = 0;

    public DBSet(DatabaseEntity databaseEntity, Class<T> classType) {
        this.databaseEntity = databaseEntity;
        this.classType = classType;
        //fields = classType.getFields();
        fields = classType.getDeclaredFields();
        try {
            createTable();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public DBSet(DatabaseEntity databaseEntity) {
        this.databaseEntity = databaseEntity;

    }

    /*
    public void DBInitation(Class<T> classType){
        System.out.println("Test for entering DBInitiation function");
        this.classType=classType;
        fields=classType.getDeclaredFields();
        System.out.println("Test for class name"+this.classType.getName());
        try{
       //     createTable();
        }catch(Exception e){
        }
    }*/
    //Will be implemented by team 0
    public void add(T item) {
        String insert = "INSERT INTO " + classType.getSimpleName() + "(";
        for (int i = 0; i < fields.length; i++) {

            String temp;
            if (i != fields.length - 1) {
                temp = fields[i].getName() + ", ";
            } else {
                temp = fields[i].getName() + ")";
            }
            insert += temp;
        }
        insert += "VALUES (";
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String temp;
            try {

                if (i != fields.length - 1) {
                    if (fields[i].get(item).equals(true)) {
                        temp = "'" + 1 + "', ";
                    } else if (fields[i].get(item).equals(false)) {
                        temp = "'" + 0 + "', ";
                    } else {
                        temp = "'" + fields[i].get(item) + "', ";
                    }

                } else {
                    if (fields[i].get(item).equals(true)) {
                        temp = "'" + 1 + "');";
                    } else if (fields[i].get(item).equals(false)) {
                        temp = "'" + 0 + "');";
                    } else {
                        temp = "'" + fields[i].get(item) + "');";
                    }
//                    temp = "'" + fields[i].get(item) + "');";

                }
                insert += temp;
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            System.out.println("inserting data");

            databaseEntity.excuteSql(insert);
            System.out.println("finished inserting data");

        } catch (SQLException e) {
            System.out.println("error in inserting data");

            System.out.println(e.getMessage());
        }
    }
    //New- added by team 1

    public ArrayList<T> findAll() throws SQLException {
        //The returned array list 
        ArrayList<T> arrayObject = new ArrayList<>();
        //Query to select all the information of given class
        String query = "SELECT * FROM " + classType.getSimpleName() + " ;";
        try {
            //The rows of database are returned in ResultSet reference    
            ResultSet rs = databaseEntity.excuteQUERY(query);
            //Get the full name of ResultSet Class
            String resultname = rs.getClass().getName();
            //Get the Full name of given class
            String className = classType.getName();
            //get the given class 
            Class<?> cls = Class.forName(className);
            //Loop over the returned rows from database 
            while (rs.next()) {
                //make new instance from the given class to set its attributes and return it to the user.
                Object _instance = cls.newInstance();
                //Loop over the fields of the given class
                for (int i = 0; i < fields.length; i++) {
                    //get Java Types eg. Int, Float 
                    String type = getJavaType(fields[i].getType().toString());
                    //get field name
                    String fieldName = fields[i].getName();
                    //concatenate get string with type of given field to form get functions of ResultSet class
                    String methoName = "get" + type;
                    //get method of abstract class ResultSet by passing the method name and its paramaters' type
                    Method myMethod = ResultSet.class.getDeclaredMethod(methoName, String.class);
                    //Invoke the given method of ResultSet class by passing reference of this abstract class and 
                    //the field name 
                    result = myMethod.invoke(rs, fieldName);
                    //Make the private field be accessible y setting it true
                    fields[i].setAccessible(true);
                    //pass the instaniated instance of the given class eg.user and the result of the method invokation
                    //to set this field by the new value of this instance of the given class => setting an attribute of user class
//                    fields[i].set(_instance, result);
                    if (flag == 1) {
                        fields[i].set(_instance, result.toString().charAt(0));
                        flag = 0;
                    } else {
                        fields[i].set(_instance, result);
                    }
                }
                //insert the object of the given class in the arraylist after setting all its attributes
                arrayObject.add((T) _instance);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObject;
    }
    //New- added by team 1

    public ArrayList<T> findBy(String field, Object value) throws SQLException {

        ArrayList<T> arrayObject = new ArrayList<>();
        String query = "SELECT * FROM " + classType.getSimpleName() + " WHERE " + field + "='" + value.toString() + "';";
        try {
            ResultSet rs = databaseEntity.excuteQUERY(query);
            String resultname = rs.getClass().getName();
            String className = classType.getName();// Class name
            Class<?> cls = Class.forName(className);
            Class<?> rls = rs.getClass();
            while (rs.next()) {
                Object _instance = cls.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    //get Java Types eg. Int, Float 
                    String type = getJavaType(fields[i].getType().toString());
                    //get field name
                    String fieldName = fields[i].getName();
                    //concatenate get string with type of given field to form get functions of ResultSet class
                    String methoName = "get" + type;
                    //get method of abstract class ResultSet by passing the method name and its paramaters' type
                    Method myMethod = ResultSet.class.getDeclaredMethod(methoName, String.class);
                    //Invoke the given method of ResultSet class by passing reference of this abstract class and 
                    //the field name 
                    result = myMethod.invoke(rs, fieldName);
                    //Make the private field be accessible y setting it true
                    fields[i].setAccessible(true);
                    //pass the instaniated instance of the given class eg.user and the result of the method invokation
                    //to set this field by the new value of this instance of the given class => setting an attribute of user class
//                    fields[i].set(_instance, result);
                    if (flag == 1) {
                        fields[i].set(_instance, result.toString().charAt(0));
                        flag = 0;
                    } else {
                        fields[i].set(_instance, result);
                    }

                }
                //insert the object of the given class in the arraylist after setting all its attributes
                arrayObject.add((T) _instance);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return arrayObject;
    }

    //Will be implemented by team 1
    //Will be implemented by team 1
    public T findById(String field, Object value) {

        T arrayObject = null;
        String query = "SELECT * FROM " + classType.getSimpleName() + " WHERE " + field + "='" + value.toString() + "';";
        try {
            ResultSet rs = databaseEntity.excuteQUERY(query);
            String resultname = rs.getClass().getName();
            String className = classType.getName();// Class name
            Class<?> cls = Class.forName(className);
            Class<?> rls = rs.getClass();
            while (rs.next()) {
                Object _instance = cls.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    //get Java Types eg. Int, Float 
                    String type = getJavaType(fields[i].getType().toString());
                    //get field name
                    String fieldName = fields[i].getName();
                    //concatenate get string with type of given field to form get functions of ResultSet class
                    String methoName = "get" + type;
                    //get method of abstract class ResultSet by passing the method name and its paramaters' type
                    Method myMethod = ResultSet.class.getDeclaredMethod(methoName, String.class);
                    //Invoke the given method of ResultSet class by passing reference of this abstract class and 
                    //the field name 
                    result = myMethod.invoke(rs, fieldName);
                    //Make the private field be accessible y setting it true
                    fields[i].setAccessible(true);
                    //pass the instaniated instance of the given class eg.user and the result of the method invokation
                    //to set this field by the new value of this instance of the given class => setting an attribute of user class
//                    fields[i].set(_instance, result);

                    if (flag == 1) {
                        fields[i].set(_instance, result.toString().charAt(0));
                        flag = 0;
                    } else {
                        fields[i].set(_instance, result);
                    }
                }
                //insert the object of the given class in the arraylist after setting all its attributes

                arrayObject = ((T) _instance);
                return arrayObject;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return arrayObject;
    }

    private void createTable() throws SQLException {
        String create = "CREATE TABLE " + classType.getSimpleName() + " (";

        for (int i = 0; i < fields.length; i++) {
            String temp;
            String type = getSqlType(fields[i].getType().toString());
            System.out.println("Type: " + type);
            String name = fields[i].getName();
            System.out.println("Field name : " + name);
            if (i != fields.length - 1) {
                temp = name + " " + type + ", ";
            } else {
                temp = name + " " + type + ");";
            }
            create += temp;
            System.out.println("Create Statement " + create);
        }

        databaseEntity.excuteSql(create);
    }

    private String getSqlType(String type) {
        switch (type) {
            case "class java.lang.String":
                return "VARCHAR(255)";
            case "boolean":
                return "TINYINT(1)";
            case "int":
                return "INTEGER";
            case "float":
                return "REAL";
            case "double":
                return "REAL";
            case "char":
                return "varchar(1)";
            case "long":
                return "INTEGER";
            default:
                return null;
        }
    }

    //New added by team 1
    private String getJavaType(String type) {
        switch (type) {
            case "class java.lang.String":
                flag = 0;
                return "String";
            case "boolean":
                flag = 0;
                return "Boolean";
            case "int":
                flag = 0;
                return "Int";
            case "float":
                flag = 0;
                return "Float";
            case "double":
                flag = 0;
                return "Double";
            case "char":
                flag = 1;
                return "String";
            case "long":
                flag = 0;
                return "Long";
            default:
                return null;
        }
    }

}
