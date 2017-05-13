/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kaibeOh24
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class UserBean implements Serializable {
    private String name;
    private String password;
    private String dbUsername;
    private String dbPassword;
    
    //private static final long serialVersionUID = 6081417964063918994L;
    Connection connect = null;
    String url = "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb";
    PreparedStatement ps;
    ResultSet rs;
    
    public UserBean() throws ClassNotFoundException {
        
    }
    
    /*
    public void printDB() throws ClassNotFoundException, SQLException {
        
        //Connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url,"admin","mypassword");
        } catch (SQLException ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        
        
        //ps = connect.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
        ps = connect.prepareStatement("SELECT * FROM users;");
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(3));
        }
        
        // close resources
	rs.close();
	ps.close();
	connect.close();
    }
    */
    private boolean getDBData(String name,String password) throws ClassNotFoundException, SQLException {
        
        //Connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url,"admin","mypassword");
            ps = connect.prepareStatement("SELECT name,password from users where name=? and password=?;");
            ps.setString(1, name);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                return true;
            }
        
        } catch (SQLException ex) {
            System.out.println("Login Error");
            System.out.println(ex.getMessage());
            return false;
        } finally {
            // close resources
            rs.close();
            ps.close();
            connect.close();
        }
        
        return false;
        
        /*
        ps = connect.prepareStatement("SELECT * FROM users;");
        rs = ps.executeQuery();
        rs.next();
        dbUsername = rs.getString("name");
        dbPassword = rs.getString("password");
        */
        
        
    }
    
    public String getName() { 
        return name;
    }
    
    public void setName (String newValue) {
        name = newValue;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String newValue) {
        password = newValue;
    }
    
    public String getdbName() {
        return dbUsername;
    }
    
    public String getdbPass() {
        return dbPassword;
    }
    
    public void createAccount() {
        
    }
    
    public String login() throws ClassNotFoundException, SQLException {
        
        boolean valid = getDBData(name,password);
        
        if(valid) {
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", name);
            if (session == null) {
                FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(),null, "/login.xhtml");
            }
            
            //System.out.println("This is a printout!!!!");
            return "welcome";
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "Please enter the correct login information." ));
            //FacesContext.getCurrentInstance().validationFailed();
            
            return null;
            
        }
    }
    
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/login.xhtml");
        return "index";
    }
    
    /*
    public String navLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session.getAttribute("user") == null){
            
    	} else {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/login.xhtml");
        }
        
        return "faces/login.xhtml";
    }
    */
    
    public String checkSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session.getAttribute("user") == null){
            return "Sign In";
    	} else {
            return "Logout";
        }
    }
    
    /*
    public String checkSession() {
        
        if () {
        // Session has been invalidated during the previous request.
            return "Sign In";
        } else {
            return "Log Out";
        }
    }
    */
}
