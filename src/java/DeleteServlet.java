/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Kevin
 */
@WebServlet("/deleteFile")
public class DeleteServlet extends HttpServlet {

    private static final long serialVersionUID = -1623656324694499109L;
    private Connection conn;

    public DeleteServlet() throws ClassNotFoundException, SQLException {
	String url = "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb";
	String username = "admin";
	String password = "mypassword";
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        // gets values of text fields
        String taskId = request.getParameter("deleteImageID");
    
        try {
            // constructs SQL statement
            String sql = "DELETE FROM gallery WHERE imgId='" + taskId + "'";
           //String sql = "DELETE FROM gallery WHERE imgId='52'";
         //   sql = "DELETE FROM `rwdb`.`gallery` WHERE `imgId`='"+taskId+"'";
       //     String sql = "Insert into gallery(name, description, valu"
            Statement statement = conn.prepareStatement(sql);
           // statement.setString(1, taskName);
           // statement.setString(2, taskDesc);

            //if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
              //  statement.setBlob(3, inputStream);
          //  }
            statement.executeUpdate(sql);
            // sends the statement to the database server
            //int row = statement.executeUpdate();
          //  if (row > 0) {
          //      message = "Image is uploaded successfully into the Database";
          //  }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       // request.setAttribute("Message", message);
        // sets the message in request scope

        // forwards to the message page
      //  getServletContext().getRequestDispatcher("/submit.jsp").forward(
       //         request, response);
               response.sendRedirect("welcome.xhtml");
    }
}

