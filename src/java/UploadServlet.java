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
@WebServlet("/fileUpload")
@MultipartConfig(maxFileSize = 16177215) // upload file up to 16MB
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = -1623656324694499109L;
    private Connection conn;

    public UploadServlet() throws ClassNotFoundException, SQLException {
	String url = "jdbc:mysql://rwdb.cmxsmmcmpe9w.us-east-1.rds.amazonaws.com:3306/rwdb";
	String username = "admin";
	String password = "mypassword";
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        // gets values of text fields
        String taskName = request.getParameter("taskName");
        String taskDesc = request.getParameter("taskDesc");

        InputStream inputStream = null;

        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            // debug messages
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        String message = null; // message will be sent back to client

        try {
            // constructs SQL statement
            String sql = "INSERT INTO gallery(name,description,file) VALUES ('"+taskName + "','"+ 
                    taskDesc + "','" + inputStream +"')";
       //     String sql = "Insert into gallery(name, description, valu"
            PreparedStatement statement = conn.prepareStatement(sql);
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
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        }
        // sets the message in request scope
       // request.setAttribute("Message", message);

        // forwards to the message page
     //   getServletContext().getRequestDispatcher("/gallery.xhtml").forward(
       //         request, response);
        response.sendRedirect("welcome.xhtml");
    }
}

