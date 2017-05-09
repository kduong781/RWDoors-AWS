/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hecto
 */
@Named(value = "listClients")
@RequestScoped
public class ListClients {
    
    private int clientID, orderID;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

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
    
    public static Connection conn = null;
    public static PreparedStatement pstmt = null;
    public static ResultSet rs = null;
    private String str = "";
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Alt + enter
            conn = DriverManager.getConnection("jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb", "admin","mypassword");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    
    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Creates a new instance of ListClients
     */
    public ListClients() {
    }
    
    public ListClients(int clientID, int orderID, String firstName, String lastName, String email, String phone){
        this.clientID = clientID;
        this.orderID = orderID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    
    public ArrayList<ListClients> getAllClients() {
        ArrayList<ListClients> arr = new ArrayList<>();
        str = "SELECT clientID,firstname,lastname,email,phone,orderID FROM clients;";
        getConnection();
        try {
            //System.out.println("This is a printout!!!!");
            pstmt = conn.prepareStatement(str);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ListClients st = new ListClients();
                st.setClientID(rs.getInt("clientID"));
                st.setFirstName(rs.getString("firstname"));
                st.setLastName(rs.getString("lastname"));
                st.setEmail(rs.getString("email"));
                st.setPhone(rs.getString("phone"));
                st.setOrderID(rs.getInt("orderID"));
                //Add to List
                arr.add(st);
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return arr;

    }
    
   public void delete() 
     {
        getConnection();
        str = "Delete from clients where clientID=?;";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int idOrder = Integer.parseInt(request.getParameter("clientID"));
        //System.out.println("Delete Student: "+idStudent);
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setInt(1, idOrder);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Delete SuccessFully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListClients.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }

    }
   
 
}
