/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author hecto
 */
@Named(value = "addClientBean")
@RequestScoped
public class AddClientBean implements Serializable {

    private int orderID;


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    private String firstName, lastName, email, phone;

    public void print() {
        System.out.println(orderID);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb?zeroDateTimeBehavior=convertToNull",
                    "admin", "mypassword");
//here sonoo is database name, root is username and password  
                       
            Statement stmt = con.createStatement();
            stmt.execute("insert into clients (firstname, "
                    + "lastname, email, phone, orderID) values "
                    + "( '"+firstName+"\', \'"+lastName+"\', \'"+email+"\', \'"+phone+"\', "+orderID+");");
            
            /**while (rs.next()) {
                System.out.println(rs.getInt(1) + rs.getString(2));
            }**/
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
/**
 * Creates a new instance of AddClientBean
 */
public AddClientBean() {
    }
    
}
