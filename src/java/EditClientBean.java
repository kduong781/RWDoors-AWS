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
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.ietf.jgss.Oid;

/**
 *
 * @author CHATU
 */
@Named(value = "editClientBean")
@RequestScoped
public class EditClientBean {

     private String firstName, lastName, email, phone;
    private int clientID, orderID;

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

    public EditClientBean( int clientID, String firstName, String lastName, String email, String phone, int orderID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.clientID = clientID;
        this.orderID = orderID;
    }

    
    
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
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
     public ArrayList<EditClientBean> GetAllClients() {
        ArrayList<EditClientBean> arr = new ArrayList<>();
        str = "SELECT clientID,firstname,lastname,email,phone,orderID FROM clients;";
        getConnection();
        try {
            //System.out.println("This is a printout!!!!");
            pstmt = conn.prepareStatement(str);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                EditClientBean st = new EditClientBean();
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
            
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return arr;

    }
    
     public void addClient() {
        getConnection();
        str = "Insert into clients(clientID,firstname,lastname,email,phone,orderID) values(?,?,?,?,?,?);";
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setInt(1, this.getClientID());
            pstmt.setString(2, this.getFirstName());
            pstmt.setString(3, this.getLastName());
            pstmt.setString(4, this.getEmail());
            pstmt.setString(5, this.getPhone());
            pstmt.setInt(6, this.getOrderID());
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Update SuccessFully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }
    }
    
      public void editClient() {
        ArrayList<EditClientBean> arrList = GetAllClients();
        FacesContext fc = FacesContext.getCurrentInstance();
       
        int idClient;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idClient = Integer.parseInt(request.getParameter("clientID"));
        //
        for (EditClientBean e : arrList) {
            if (e.getClientID() == idClient) {
                this.setClientID(e.getClientID());
                this.setFirstName(e.getFirstName());
                this.setLastName(e.getLastName());
                this.setEmail(e.getEmail());
                this.setPhone(e.getPhone());
                this.setOrderID(e.getOrderID());
                
            }

        }

    }
     
     
     public void update() {
        getConnection();
        str = "Update clients set firstname=?,lastname=?,email=?,phone=?,orderID=? where clientID=?;";
        //        Map<String,String> mapParam=fc.getExternalContext().getInitParameterMap();
        //        idStudent=mapParam.get("id");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int  idClient = Integer.parseInt(request.getParameter("clientID"));
        try {
             pstmt = conn.prepareStatement(str);
          
            pstmt.setString(1, this.getFirstName());
            pstmt.setString(2, this.getLastName());
            pstmt.setString(3, this.getEmail());
            pstmt.setString(4, this.getPhone());
            pstmt.setInt(5, this.getOrderID());
              pstmt.setInt(6, idClient);
//          System.out.println("Id Student Update :" + idStudent);//Error
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Update SuccessFully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }

    }
    
     public void delete() 
     {
        getConnection();
        str = "Delete from clients where clientID=?;";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int idClient = Integer.parseInt(request.getParameter("clientID"));
        //System.out.println("Delete Student: "+idStudent);
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setInt(1, idClient);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Delete SuccessFully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClientBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }

    }
     
   
    public EditClientBean() {
        
    }
    
}


