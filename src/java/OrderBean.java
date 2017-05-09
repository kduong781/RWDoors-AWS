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
 * @author kaibeOh24
 */
@Named(value = "orderBean")
@RequestScoped
public class OrderBean {
    
    private int id;
    private String name,material,size,style,description,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public String getSize() {
        return size;
    }

    public String getStyle() {
        return style;
    }

    public String getDescription() {
        return description;
    }
   
    /**
     * Creates a new instance of OrderBean
     */
    public OrderBean() {
    }
    
    public OrderBean(int id, String name, String material, String size, String style, String description, String status ) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.size = size;
        this.style = style;
        this.description = description;
        this.status = status;
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
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<OrderBean> GetAllOrders() {
        ArrayList<OrderBean> arr = new ArrayList<>();
        str = "SELECT orderID,order_name,material,size,style,description,status FROM orders;";
        getConnection();
        try {
            //System.out.println("This is a printout!!!!");
            pstmt = conn.prepareStatement(str);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderBean st = new OrderBean();
                st.setId(rs.getInt("orderID"));
                st.setName(rs.getString("order_name"));
                st.setMaterial(rs.getString("material"));
                st.setSize(rs.getString("size"));
                st.setStyle(rs.getString("style"));
                st.setDescription(rs.getString("description"));
                st.setStatus(rs.getString("status"));
                //Add to List
                arr.add(st);
            }
        } catch (SQLException ex) {
            
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return arr;

    }
    
    public void addOrder() {
        getConnection();
        str = "Insert into orders(order_name,material,size,style,description,status) values(?,?,?,?,?,?);";
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setString(1, this.getName());
            pstmt.setString(2, this.getMaterial());
            pstmt.setString(3, this.getSize());
            pstmt.setString(4, this.getStyle());
            pstmt.setString(5, this.getDescription());
            pstmt.setString(6, this.getStatus());
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Update SuccessFully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }
    }
    
    public void editOrder() {
        ArrayList<OrderBean> arrList = GetAllOrders();
        FacesContext fc = FacesContext.getCurrentInstance();
        // Map<String,String> mapParam=fc.getExternalContext().getInitParameterMap();
        // idStudent=mapParam.get("id");
        int idOrder;
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idOrder = Integer.parseInt(request.getParameter("id"));
        //
        for (OrderBean orderBean : arrList) {
            if (orderBean.getId() == idOrder) {
                this.setId(orderBean.getId());
                this.setName(orderBean.getName());
                this.setMaterial(orderBean.getMaterial());
                this.setSize(orderBean.getSize());
                this.setStyle(orderBean.getStyle());
                this.setDescription(orderBean.getDescription());
                this.setStatus(orderBean.getStatus());
            }

        }

    }
    
    public void update() {
        getConnection();
        str = "Update orders set order_name=?,material=?,size=?,style=?,description=?,status=? where orderID=?;";
        //        Map<String,String> mapParam=fc.getExternalContext().getInitParameterMap();
        //        idStudent=mapParam.get("id");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int  idOrder = Integer.parseInt(request.getParameter("id"));
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setString(1, this.getName());
            pstmt.setString(2, this.getMaterial());
            pstmt.setString(3, this.getSize());
            pstmt.setString(4, this.getStyle());
            pstmt.setString(5, this.getDescription());
            pstmt.setString(6, this.getStatus());
            pstmt.setInt(7, idOrder);
//          System.out.println("Id Student Update :" + idStudent);//Error
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Update SuccessFully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }

    }
    
    public void delete() 
     {
        getConnection();
        str = "Delete from orders where orderID=?;";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int idOrder = Integer.parseInt(request.getParameter("id"));
        //System.out.println("Delete Student: "+idStudent);
        try {
            pstmt = conn.prepareStatement(str);
            pstmt.setInt(1, idOrder);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                System.out.println("Delete SuccessFully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, pstmt, rs);
        }

    }
    
}
